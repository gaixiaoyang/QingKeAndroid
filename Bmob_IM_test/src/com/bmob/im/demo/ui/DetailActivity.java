package com.bmob.im.demo.ui;

import android.os.*;
import android.widget.*;

import com.bmob.im.demo.*;

/**
 * DetailActivity
 * 
 * @Title: DetailActivity.java
 * @Package com.bmob.im.demo.ui
 * @author xiaoyang
 * @date 2014-8-9 ����5:52:14
 * @version V1.0
 */
public class DetailActivity extends BaseActivity {
	
	private TextView detail_time = null;
	private TextView detail_place = null;
	private TextView detail_content = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initTopBarForLeft("����ǿ�����");
		initDetailView();
	}

	private void initDetailView(){
		detail_time = (TextView)findViewById(R.id.detail_time);
		detail_place = (TextView)findViewById(R.id.detail_place);
		detail_content = (TextView)findViewById(R.id.detail_content);
		
		detail_time.setText("2014-08-08 12:10");
		detail_place.setText("�����г��������ҵ�����С��");
		detail_content.setText("��ӭǰ��\n��ӭ��Ů\n��ӭ����");
	}
	
}
