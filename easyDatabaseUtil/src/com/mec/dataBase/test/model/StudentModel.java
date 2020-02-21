package com.mec.dataBase.test.model;

import com.mec.dataBase.annotation.Column;
import com.mec.dataBase.annotation.Id;
import com.mec.dataBase.annotation.Table;

@Table("Student")
public class StudentModel {
	@Id
	@Column("Sno")
	private String id;
	@Column("Sname")
	private String name;
	private String sex;
	private int age;
	private String phoneNumber;
	private String sdept;
	private int status;
	
	public StudentModel() {
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSdept() {
		return sdept;
	}

	public void setSdept(String sdept) {
		this.sdept = sdept;
	}

	@Override
	public String toString() {
		return "id:" + id + ", name:" + name + ", status:" + status;
	}

}
