package com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity;


public class DlqMessage {
	public String id;
	public String client_id;
	public String time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
