package com.bmob.im.demo.ui.fragment;

import java.util.*;

import android.annotation.*;
import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import cn.bmob.im.*;
import cn.bmob.v3.listener.*;

import com.bmob.im.demo.*;
import com.bmob.im.demo.bean.*;
import com.bmob.im.demo.ui.*;

/**
 * �������
 * 
 * @ClassName: LaunchFragment
 * @author xiaoyang
 * @date 2014-6-7 ����1:02:05
 */
@SuppressLint("DefaultLocale")
public class LaunchFragment extends FragmentBase implements OnClickListener {

	private TextView tv_time;
	private EditText tv_address;
	private EditText tv_content;
	private RadioButton male;
	private RadioButton female;
	private Button btn_launch;
	private String sex = "female";
	private BmobUserManager userManager;
	private boolean control = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_launch, container, false);
	}

	@Override
	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTopBarForOnlyTitle("�������");
		userManager = BmobUserManager.getInstance(this.getActivity());
		tv_time = (TextView) findViewById(R.id.tv_time);
		male = (RadioButton) findViewById(R.id.male);
		female = (RadioButton) findViewById(R.id.female);
		btn_launch = (Button) findViewById(R.id.btn_launch);
		tv_address = (EditText) findViewById(R.id.tv_address);
		tv_content = (EditText) findViewById(R.id.tv_content);
		tv_time.setOnClickListener(this);
		male.setOnClickListener(this);
		female.setOnClickListener(this);
		btn_launch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_time:
			showTimeDialog();
			break;
		case R.id.btn_launch:
			saveData();
			break;
		case R.id.male:
			sex = "male";
			break;
		case R.id.female:
			sex = "female";
			break;
		};
	}

	private void saveData() {
		if(control){
			control = false;
			String time = tv_time.getText().toString();
			String address = tv_address.getText().toString();
			String content = tv_content.getText().toString();
			if(time == null || time.trim().equals("")){
				showSaveSuccessDialog("��ѡ��ʱ�� ...");
				return;
			}
			if(address == null || address.trim().equals("")){
				showSaveSuccessDialog("������ص� ...");
				return;
			}
			if(content == null || content.trim().equals("")){
				showSaveSuccessDialog("����д˵�� ...");
				return;
			}
			User user = userManager.getCurrentUser(User.class);
			final ProgressDialog progress = new ProgressDialog(this.getActivity());
			progress.setMessage("���ڷ���...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
			final Activitys activity = new Activitys();
			activity.setSex(sex);
			activity.setAddress(address);
			activity.setContent(content);
			activity.setTime(time);
			activity.setTimestamp(System.currentTimeMillis());
			activity.setUser_id(user.getObjectId());
			activity.setUser_name(user.getUsername());
			String avatar = user.getAvatar();
			if(avatar == null || avatar.equals("")){
				activity.setAvatar("");
			}else{
				activity.setAvatar(avatar);
			}
			try {
				String currentLat = CustomApplcation.getInstance().getLatitude();
				activity.setCurrentLat(currentLat);
			} catch (Exception e) {
				activity.setCurrentLat("");
			}
			try {
				String currentLong = CustomApplcation.getInstance().getLongtitude();
				activity.setCurrentLong(currentLong);
			} catch (Exception e) {
				activity.setCurrentLong("");
			}
			activity.save(LaunchFragment.this.getActivity(), new SaveListener() {
				@Override
				public void onSuccess() {
					ShowToast("�����ɹ���");
					progress.dismiss();
					tv_time.setText("");
					tv_address.setText("");
					tv_content.setText("");
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					progress.dismiss();
					ShowToast("����ʧ��,�������磡\n" + arg1);
					ShowLog(arg1);
				}
			});
		}
		control = true;
	}

	protected void showSaveSuccessDialog(String message) {
		AlertDialog.Builder builder = new Builder(this.getActivity());
		builder.setMessage(message);
		builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void showTimeDialog() {
		Calendar c = Calendar.getInstance();
		new DatePickerDialog(LaunchFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				tv_time.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
	}
}
