package com.example.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	public User() {
	}
	
	public User(String username, String password, String dob) {
		this.username = username;
		this.password = password;
		this.DOB = dob;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UID")
	private int UID;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String  password;
	
	@Column(name = "DOB")
	private String DOB;

	@Column(name = "profilePicture")
	private String profilePicture;
	
	
	/* Getter Methods **/
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getDOB() {
		return DOB;
	}
	public int getUID() {
		return UID;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	
	/* Setter Methods */
	public void setUsername(String username) {
		this.username = username; 
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDOB(String DOB) {
		this.DOB = DOB;
	}
	public void setUID(int UID) {
		this.UID = UID;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
}
