package com.example.himinefriend;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SendMessage extends Activity{

    private DBAdapter db;
	private Cursor cursor;

    private TextView showname;
    private Button sendMess;
    private EditText messageContent;



    private String telephone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmessage);

        db = new DBAdapter(this);

		showname = (TextView)findViewById(R.id.name);
        messageContent = (EditText)findViewById(R.id.et_content);
		sendMess = (Button)findViewById(R.id.bt_send);


        fetch();
        sendMess.setOnClickListener(listener);

	}
	
	
	public void fetch(){

        db.open();

		Intent intent = getIntent();
		int myid = intent.getIntExtra("id", 0);

		cursor=db.queryOneData(myid);
		cursor.moveToFirst();
		
		String name = cursor.getString(1);
        long phone = cursor.getLong(2);
        telephone = ""+phone;

        String content = messageContent.getText().toString();

        Toast.makeText(SendMessage.this,""+phone,Toast.LENGTH_SHORT).show();
		showname.setText(name);
		cursor.close();
		
	}

    private View.OnClickListener listener = new View.OnClickListener() {

        public void onClick(View view) {

            //获得SmsManager的默认实例
            SmsManager smsManager = SmsManager.getDefault();
            //从系统取得一个用于向BroadcastReceiver的Intent广播的PendingIntent对象
            PendingIntent sentIntent = PendingIntent.getBroadcast(SendMessage.this,0,new Intent(),0);

            String content = messageContent.getText().toString();

            if(content.length() >= 70){
                //当短信超过SMS消息的最大长度时(70字)，将短信分割为几块。
                List<String> ms = smsManager.divideMessage(content);
                for(String str:ms){
                    //短信发送
                    smsManager.sendTextMessage(telephone,null,str,sentIntent,null);
                }
            } else {
                smsManager.sendTextMessage(telephone,null,content,sentIntent,null);
            }

            //将短信写入系统库
            //将发送的短信插入数据库
            ContentValues values = new ContentValues();
            //发送时间
            values.put("date",System.currentTimeMillis());
            //阅读状态
            values.put("read",0);
            //1为收，2为发
            values.put("type",2);
            //送达号码
            values.put("address",telephone);
            //送达内容
            values.put("body",content);
            //插入短信库
            getContentResolver().insert(Uri.parse("content://sms"),values);

            Toast.makeText(SendMessage.this,"发送成功!",Toast.LENGTH_SHORT).show();
        }
    };
}
