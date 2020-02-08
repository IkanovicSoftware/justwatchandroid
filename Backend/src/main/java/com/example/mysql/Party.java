package com.example.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Parties")
public class Party {
	
	public Party(String name){
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GID")
	private int GID;

	@Column(name = "name")
	private String name;

	@Column(name = "picture")
	private String picture;

	
	/* Getter Methods */
	public int getGID() {
		return GID;
	}
	public String getName() {
		return name;
	}
	public String getPicture() {
		return picture;
	}
	
	/* Setter Methods */
	public void setGID(int GID) {
		this.GID = GID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
