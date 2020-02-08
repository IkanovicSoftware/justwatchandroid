package com.example.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Friend")
public class Friend {
	
	@Id
	@Column(name = "uuid")
	@GeneratedValue
	private int uuid;
	
	@Column(name = "uid")
	private int uid;
	
	@Column(name = "fid")
	private int fid;
	
	
	public Friend() {
		// TODO Auto-generated constructor stub
	}
	
	
	/* Getter Methods */
	public int getUID() {
		return uid;
	}
	public int getFID() {
		return fid;
	}
	
	/* Setter Methods */
	public void setUID(int UID) {
		this.uid = UID;
	}
	public void setFID(int FID) {
		this.fid = FID;
	}
}
