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
import com.bmob.im.demo.util.*;

/**
 * 发起请客
 * 
 * @ClassName: LaunchFragment
 * @author xiaoyang
 * @date 2014-6-7 下午1:02:05
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
		initTopBarForOnlyTitle("发起请客");
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
				showSaveSuccessDialog("请选择时间 ...");
				control = true;
				return;
			}else{
				try {
					long userTime = TimeUtil.stringToLong(time, "yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, +30);
					long systime = TimeUtil.dateToLong(cal.getTime());
					if(userTime >= systime){
						showSaveSuccessDialog("时间不要超过30天 ...");
						control = true;
						return;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(address == null || address.trim().equals("")){
				showSaveSuccessDialog("请输入地点 ...");
				control = true;
				return;
			}
			if(content == null || content.trim().equals("")){
				showSaveSuccessDialog("请填写说明 ...");
				control = true;
				return;
			}
			User user = userManager.getCurrentUser(User.class);
			final ProgressDialog progress = new ProgressDialog(this.getActivity());
			progress.setMessage("正在发布...");
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
					ShowToast("发布成功！");
					progress.dismiss();
					tv_time.setText("");
					tv_address.setText("");
					tv_content.setText("");
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					progress.dismiss();
					ShowToast("发布失败,请检查网络！\n" + arg1);
					ShowLog(arg1);
				}
			});
		}
		control = true;
	}

	protected void showSaveSuccessDialog(String message) {
		AlertDialog.Builder builder = new Builder(this.getActivity());
		builder.setMessage(message);
		builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
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
				monthOfYear = monthOfYear + 1;
				if(monthOfYear >= 1 && monthOfYear <= 9){
					tv_time.setText(year + "-0" + monthOfYear + "-" + dayOfMonth);
				}else{
					tv_time.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
				}
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
	}
}
