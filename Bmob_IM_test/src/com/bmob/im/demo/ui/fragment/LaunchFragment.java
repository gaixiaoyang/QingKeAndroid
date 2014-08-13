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
 * 发起请客
 * 
 * @ClassName: LaunchFragment
 * @author xiaoyang
 * @date 2014-6-7 下午1:02:05
 */
@SuppressLint("DefaultLocale")
public class LaunchFragment extends FragmentBase implements OnClickListener {

	private TextView tv_name;
	private TextView tv_time;
	private EditText tv_address;
	private EditText tv_content;
	private RadioButton is_agree;
	private RadioButton is_disagree;
	private Button btn_launch;
	private boolean[] selected;
	private String[] arrays;
	private boolean isOpen = true;
	private BmobUserManager userManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_launch, container, false);
	}

	@Override
	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTopBarForOnlyTitle("发起请客");
		userManager = BmobUserManager.getInstance(this.getActivity());
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_time = (TextView) findViewById(R.id.tv_time);
		is_agree = (RadioButton) findViewById(R.id.is_agree);
		is_disagree = (RadioButton) findViewById(R.id.is_disagree);
		btn_launch = (Button) findViewById(R.id.btn_launch);
		tv_address = (EditText) findViewById(R.id.tv_address);
		tv_content = (EditText) findViewById(R.id.tv_content);
		tv_name.setOnClickListener(this);
		tv_time.setOnClickListener(this);
		is_agree.setOnClickListener(this);
		is_disagree.setOnClickListener(this);
		btn_launch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_name:
			showFriendDialog();
			break;
		case R.id.tv_time:
			showTimeDialog();
			break;
		case R.id.btn_launch:
			saveData();
			break;
		case R.id.is_agree:
			isOpen = true;
			break;
		case R.id.is_disagree:
			isOpen = false;
			break;
		}
		;
	}

	private void saveData() {
		String friend_list = tv_name.getText().toString();
		String time = tv_time.getText().toString();
		String address = tv_address.getText().toString();
		String content = tv_content.getText().toString();
		User user = userManager.getCurrentUser(User.class);
		final ProgressDialog progress = new ProgressDialog(this.getActivity());
		progress.setMessage("正在发布...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final Activitys activity = new Activitys();
		activity.setAddress(address);
		activity.setContent(content);
		activity.setFriend_list(friend_list);
		activity.setOpen(isOpen);
		activity.setTime(time);
		activity.setTimestamp(System.currentTimeMillis());
		activity.setUser_id(user.getUsername());
		activity.save(this.getActivity(), new SaveListener() {
			@Override
			public void onSuccess() {
				progress.dismiss();
				ShowToast("发布成功！");
				tv_name.setText("");
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

	private void showFriendDialog() {
		selected = new boolean[] { false, false, false };
		arrays = new String[] { "Item1", "Item2", "Item3" };
		Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setTitle("添加邀请人");
		// builder.setIcon(R.drawable.);
		DialogInterface.OnMultiChoiceClickListener mutiListener = new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
				selected[which] = isChecked;
			}
		};
		builder.setMultiChoiceItems(arrays, selected, mutiListener);
		DialogInterface.OnClickListener btnListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				String selectedStr = "";
				for (int i = 0; i < selected.length; i++) {
					if (selected[i] == true) {
						selectedStr = selectedStr + "," + arrays[i];
					}
				}
				if (selectedStr.startsWith(",")) {
					selectedStr = selectedStr.substring(1);
				}
				TextView editText = (TextView) findViewById(R.id.tv_name);
				editText.setText(selectedStr);
			}
		};
		builder.setPositiveButton("确定", btnListener);
		Dialog dialog = builder.create();
		dialog.show();
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
