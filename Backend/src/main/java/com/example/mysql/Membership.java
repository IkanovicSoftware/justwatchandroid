package com.example.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Membership")
public class Membership {
	
	@Id
	@Column(name = "uuid")
	@GeneratedValue
	private int uuid;
	
	@Column(name = "uid")
	private int uid;
	
	@Column(name = "gid")
	private int gid;
	
	@Column(name = "isHost")
	private boolean isHost;
	
	@Column(name = "isMod")
	private boolean isMod;
	
	
	/* Getter Methods */
	public int getUID() {
		return uid;
	}
	public int getGID() {
		return gid;
	}
	public boolean getIsHost() {
		return isHost;
	}
	public boolean getIsMod() {
		return isMod;
	}
	
	/* Setter Methods */
	public void setUID(int UID) {
		this.uid = UID;
	}
	public void setGID(int GID) {
		this.gid = GID;
	}
	public void setIsHost(boolean isHost) {
		this.isHost = isHost;
	}
	public void setIsMod(boolean isMod) {
		this.isMod = isMod;
	}
}
