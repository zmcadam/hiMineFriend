package com.example.himinefriend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListPeople extends Activity {
	

	DBAdapter db ;
	Cursor cursor;
	ListView mylist;
    private Button newpeople;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_people);
		
		db = new DBAdapter(this);
		
		mylist=(ListView)findViewById(R.id.listview);
		newpeople=(Button)findViewById(R.id.newpeople);
		
		newpeople.setOnClickListener(listener);
		
		fetchall();
		
		mylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
				HashMap<String,Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
				
				int myid=(Integer) map.get("id");
				Intent intent=new Intent();
				intent.setClass(ListPeople.this,touchPeople.class);
				intent.putExtra("id", myid);
				startActivity(intent);
				
			}
		});
		
	}
	public void fetchall(){
		String[] mFrom = new String[]{"name"};
		int[] mTo = new int[]{R.id.name};
		
		ArrayList<Map<String,Object>> mlist= new ArrayList<Map<String,Object>>(); 
		Map<String,Object> mMap = null;  
		
		cursor=db.queryAllData();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			mMap = new HashMap<String,Object>();
			int id=cursor.getInt(0);
			String name=cursor.getString(1);
			mMap.put("id",id);
			mMap.put("name",name);
			mlist.add(mMap); 
			cursor.moveToNext();
		}
		cursor.close();
		
		SimpleAdapter mAdapter = new SimpleAdapter(this,mlist,R.layout.content,mFrom,mTo);
		mylist.setAdapter(mAdapter);
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(ListPeople.this, AddPeople.class);
			startActivity(intent);
		}
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_people, menu);
		return true;
	}

}
