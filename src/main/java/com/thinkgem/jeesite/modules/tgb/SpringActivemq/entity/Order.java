package com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity;

import java.io.Serializable;



public class Order implements Serializable {
	/**
	 * 
	 */
		//ID
		private String id;
		public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String state;
		public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
		private String dingdan_id;
		private String userid;
		private String name;
		private String address;
		private int num;
		private int shangpin_money;
		private String shangpin_id;
		private String shangpin_name;
		private String date;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getDingdan_id() {
			return dingdan_id;
		}
		public void setDingdan_id(String dingdan_id) {
			this.dingdan_id = dingdan_id;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		
		public int getShangpin_money() {
			return shangpin_money;
		}
		public void setShangpin_money(int shangpin_money) {
			this.shangpin_money = shangpin_money;
		}
		public String getShangpin_id() {
			return shangpin_id;
		}
		public void setShangpin_id(String shangpin_id) {
			this.shangpin_id = shangpin_id;
		}
		public String getShangpin_name() {
			return shangpin_name;
		}
		public void setShangpin_name(String shangpin_name) {
			this.shangpin_name = shangpin_name;
		}
			

		
				
}
