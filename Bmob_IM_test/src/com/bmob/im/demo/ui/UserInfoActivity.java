package com.bmob.im.demo.ui;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.bmob.im.demo.*;

/** �ҵ����ҳ��
  * @ClassName: UserInfoActivity
  * @Description: TODO
  * @author smile
  * @date 2014-6-10 ����2:55:19
  */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("SimpleDateFormat")
public class UserInfoActivity extends ActivityBase implements OnClickListener{

	String username = "";
	RelativeLayout layout_my_launch, layout_my_join;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//��Ϊ�����ֻ���������������ĵ�����ť����Ҫ�������ص�����Ȼ���ڵ����պ����������ť������setContentView֮ǰ���ò�����Ч
		int currentapiVersion=android.os.Build.VERSION.SDK_INT;
		if(currentapiVersion>=14){
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
		setContentView(R.layout.activity_set_user);
		initView();
		username = getIntent().getStringExtra("username");
	}

	private void initView() {
		initTopBarForLeft("�ҵ����");
		layout_my_launch = (RelativeLayout) findViewById(R.id.layout_my_launch);
		layout_my_join = (RelativeLayout) findViewById(R.id.layout_my_join);
		layout_my_launch.setOnClickListener(this);
		layout_my_join.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_my_launch:
				Intent intent1 =new Intent(this,MyLaunchActivity.class);
				startActivity(intent1);
				break;
			case R.id.layout_my_join:
				Intent intent2 =new Intent(this,MyJoinActivity.class);
				startActivity(intent2);
				break;
		}
	}
	
}
