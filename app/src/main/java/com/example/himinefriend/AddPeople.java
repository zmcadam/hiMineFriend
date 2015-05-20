package com.example.himinefriend;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.provider.MediaStore;

public class AddPeople extends Activity{
	

	private DBAdapter db;
	
	private EditText name,number;
	private Button add,back;
    private ImageView photo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_people);
		
		db= new DBAdapter(this);
		
		name=(EditText)findViewById(R.id.addname);
		number=(EditText)findViewById(R.id.addnumber);
		add=(Button)findViewById(R.id.add);
		back=(Button)findViewById(R.id.cancel);
        photo = (ImageView)findViewById(R.id.photo);
		
		add.setOnClickListener(listener);
		back.setOnClickListener(backListener);
        photo.setOnClickListener(photolistener);
	}
	
	private OnClickListener listener = new OnClickListener() {
			
		public void onClick(View view) {
			Button btn = (Button) view;
			switch(btn.getId()) {
				case R.id.add:
				add_people();
				break;	
			}
		}
	};

	//添加联系人
	public void add_people()
	{
		db.open();
		String NAME=name.getText().toString();
		String NUMBER=number.getText().toString();
		if(NAME.equals("")||NUMBER.equals("")) {
			Toast.makeText(this, "请完善信息", Toast.LENGTH_SHORT).show();
			return;
		}
		long raw = 0;
		raw = db.insert(NAME, NUMBER);
		if(raw != -1)
			Toast.makeText(this, "添加联系人成功", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "添加联系人失败", Toast.LENGTH_SHORT).show();

	}	
	
	
	
	private OnClickListener backListener = new OnClickListener() {
		
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(AddPeople.this, ListPeople.class);
			startActivity(intent);
		}
	};

    private OnClickListener photolistener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, 1);
        }
    };
	
	
}
