package com.example.himinefriend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by zmc on 15/5/12.
 */
public class ReceiveMessage extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

            StringBuilder sb = new StringBuilder();
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                Object[] pdus = (Object[])bundle.get("pdus");
                SmsMessage[] msg = new SmsMessage[pdus.length];
                for(int i=0;i<pdus.length;i++){
                    msg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }

                for(SmsMessage curMsg:msg){
                    sb.append("您收到一条来自[");
                    sb.append(curMsg.getDisplayOriginatingAddress());
                    sb.append("]的短信：");
                    sb.append(curMsg.getDisplayMessageBody());
                }
                Toast.makeText(context,sb.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }
