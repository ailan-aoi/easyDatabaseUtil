package com.mec.dataBase.test.model;

public class CourseModel {
	private String id;
	private String name;
	private short credit;
	private String pcon;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public short getCredit() {
		return credit;
	}
	
	public void setCredit(short credit) {
		this.credit = credit;
	}
	
	public String getPcon() {
		return pcon;
	}
	
	public void setPcon(String pcon) {
		this.pcon = pcon;
	}

	@Override
	public String toString() {
		return "id:" + id + ", name:" + name;
	}
	
}
