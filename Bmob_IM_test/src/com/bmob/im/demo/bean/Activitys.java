package com.bmob.im.demo.bean;

import cn.bmob.v3.*;

public class Activitys extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sex;
	private String time;
	private String address;
	private String content;
	private String user_id;
	private String avatar;
	private long timestamp;

	public Activitys() {

	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTime() {
		return time;
	}

	public String getAddress() {
		return address;
	}

	public String getContent() {
		return content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
