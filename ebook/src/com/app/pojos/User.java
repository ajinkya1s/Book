package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	private Integer id;
	private String name;
	private String email;
	private String phone;
	private String password;
	private Role role;
	private byte[] imageblob;
	
	@JsonIgnore
	private List<IssueRecord> listRec=new ArrayList<>();
	@JsonIgnore
	private List<Payments> listPayments = new ArrayList<>();
	
	public void addPayments(Payments p)
	{
		listPayments.add(p);
		p.setPayId(this);
	}
	public void removePayments(Payments p)
	{
		listPayments.remove(p);
		p.setPayId(this);
	}
	
	public User() {
		System.out.println("in user ctor");
	}
	
	
	public User(Integer id, String name, String email, String phone, String password, Role role,byte[] imageblob) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.role = role;
		this.imageblob=imageblob;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	@Lob
	@Column(columnDefinition = "longblob")
	public byte[] getImageblob() {
		return imageblob;
	}
	public void setImageblob(byte[] imageblob) {
		this.imageblob = imageblob;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(length = 20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 30,unique = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 20)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(length = 20)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}


	@OneToMany(mappedBy = "usrId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	public List<IssueRecord> getListRec() {
		return listRec;
	}


	public void setListRec(List<IssueRecord> listRec) {
		this.listRec = listRec;
	}


	@OneToMany(mappedBy = "payId")
	@Fetch(value = FetchMode.SUBSELECT)
	public List<Payments> getListPayments() {
		return listPayments;
	}

	public void setListPayments(List<Payments> listPayments) {
		this.listPayments = listPayments;
	}
	
	public void addUserid(IssueRecord iobj)
	{
		this.listRec.add(iobj);
		iobj.setUsrId(this);
	}
	
	public void removeUserId(IssueRecord iobj)
	{
		this.listRec.remove(iobj);
	}


	
	
}
