package com.app.pojos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "issuerecord")
public class IssueRecord 
{
	private Integer id;
	private Book bkid;
	private User usrId;
	@JsonFormat(pattern="YYYY-MM-DD")
	private Date issueDate;
	private Date returnDueDate;
	private Date returnDate;
	private Double fineAmount;
	public IssueRecord() 
	{
	System.out.println("in Issuerecord ctor");	// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
//	@OneToOne
//	@JoinColumn(name = "copyid")
//	public Copy getCpId() {
//		return cpId;
//	}
//
//	public void setCpId(Copy cpId) {
//		this.cpId = cpId;
//	}
	
	@ManyToOne
	@JoinColumn(name = "bk_id")
	public Book getBkid() {
		return bkid;
	}
	public void setBkid(Book bkid) {
		this.bkid = bkid;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "memberid")
	public User getUsrId() {
		return usrId;
	}

	public void setUsrId(User usrId) {
		this.usrId = usrId;
	}

	@Temporal(TemporalType.DATE)
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getReturnDueDate() {
		return returnDueDate;
	}

	public void setReturnDueDate(Date returnDueDate) {
		this.returnDueDate = returnDueDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public IssueRecord(Book bkid, User usrId, Date issueDate, Date returnDueDate) {
		super();
		this.bkid = bkid;
		this.usrId = usrId;
		this.issueDate = issueDate;
		this.returnDueDate = returnDueDate;
	}	
}
