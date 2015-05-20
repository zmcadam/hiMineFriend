package com.example.himinefriend;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by zmc on 15/5/12.
 */
public class makePhonecall extends Activity {

    private DBAdapter db;
    private Cursor cursor;
    private String telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBAdapter(this);

        fetch();

        //传入服务， parse（）解析号码
        Intent phoneIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + telephone));
        //通知activtity处理传入的call服务
        startActivity(phoneIntent);
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

        Toast.makeText(makePhonecall.this, "" + phone, Toast.LENGTH_SHORT).show();
        cursor.close();

    }


}
