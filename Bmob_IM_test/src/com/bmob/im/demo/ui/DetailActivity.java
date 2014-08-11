package com.bmob.im.demo.ui;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.bmob.im.demo.*;

/**
 * 活动详情页
 * 
 * @Title: DetailActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 下午5:52:14
 * @version V1.0
 */
public class DetailActivity extends ActivityBase implements OnClickListener{
	
	private TextView detail_time = null;
	private TextView detail_place = null;
	private TextView detail_content = null;
	private Button btn_save = null;
	private Button btn_check = null;
	private String from = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		from = getIntent().getStringExtra("from");
		initTopBarForLeft("赵自强的请客");
		initDetailView();
	}

	private void initDetailView(){
		detail_time = (TextView)findViewById(R.id.detail_time);
		detail_place = (TextView)findViewById(R.id.detail_place);
		detail_content = (TextView)findViewById(R.id.detail_content);
		
		btn_save = (Button)findViewById(R.id.btn_save);
		btn_check = (Button)findViewById(R.id.btn_check);
		
		btn_save.setOnClickListener(this);
		btn_check.setOnClickListener(this);
		
		detail_time.setText("2014-08-08 12:10");
		detail_place.setText("北京市朝阳区花家地望京小腰");
		detail_content.setText("欢迎前来\n欢迎美女\n欢迎汉子");
		
		if(from.equals("mylaunch")){
			btn_save.setVisibility(View.INVISIBLE);
		}else if(from.equals("myjoin")){
			btn_save.setVisibility(View.INVISIBLE);
			btn_check.setVisibility(View.INVISIBLE);
		}else if(from.equals("activity")){
			btn_check.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_save:
				//TODO
				
				break;
			case R.id.btn_check:
				Intent intent2 = new Intent(this,ApplyListActivity.class);
				startActivity(intent2);
				break;
		}
	}
	
}
