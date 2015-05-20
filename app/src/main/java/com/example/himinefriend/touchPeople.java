package com.example.himinefriend;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class touchPeople extends Activity {
	
	
	private Cursor cursor;
	DBAdapter db;
	TextView showname,shownumber;
	Button delete,sendmessage,makephonecall;
	public int deleteid;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch_people);
		
		db = new DBAdapter(this);
		
		showname = (TextView)findViewById(R.id.showname);
		shownumber = (TextView)findViewById(R.id.shownumber);
		delete = (Button)findViewById(R.id.delete);
		sendmessage = (Button)findViewById(R.id.sendmessage);
		makephonecall = (Button)findViewById(R.id.makephonecall);
		
		showname.setCursorVisible(false);
		shownumber.setCursorVisible(false);
		
		fetch();		
    	delete.setOnClickListener(listener);
    	sendmessage.setOnClickListener(messlistener);
        makephonecall.setOnClickListener(calllistener);
	}
	
	public void fetch(){

		Intent intent = getIntent();
		int myid = intent.getIntExtra("id", 0);
		db = new DBAdapter(this);
		
		cursor=db.queryOneData(myid);
		cursor.moveToFirst();
		
		deleteid = myid;
		
		String name = cursor.getString(1);
		String number = cursor.getString(2);
		
		showname.setText(name);
		shownumber.setText(number);
		
		cursor.close();
		
	}
	
	private OnClickListener listener = new OnClickListener() {
	
			public void onClick(View view) {
				Button btn = (Button) view;
				switch(btn.getId()) {
					case R.id.delete:
					delete_people();
					break;	
				}
			}
		
	};
	
	public void delete_people() {
			db.open();
			long raw=-1;
			raw=db.deleteOneData(deleteid);
			//Toast.makeText(this, ""+deleteid, Toast.LENGTH_SHORT).show();
			if(raw > 0)
				Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
	}
	
	private OnClickListener messlistener = new OnClickListener() {
			
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(touchPeople.this, SendMessage.class);
				intent.putExtra("id",deleteid);
				startActivity(intent);
			}
		};

    private OnClickListener calllistener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(touchPeople.this, makePhonecall.class);
            intent.putExtra("id",deleteid);
            startActivity(intent);
        }
    };
	
}
