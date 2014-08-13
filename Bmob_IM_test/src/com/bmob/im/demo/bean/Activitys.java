package com.bmob.im.demo.bean;

import cn.bmob.v3.*;

public class Activitys extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String friend_list;
	private String time;
	private String address;
	private String content;
	private String user_id;
	private boolean isOpen;
	private long timestamp;

	public Activitys() {
		
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getFriend_list() {
		return friend_list;
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

	public void setFriend_list(String friend_list) {
		this.friend_list = friend_list;
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
