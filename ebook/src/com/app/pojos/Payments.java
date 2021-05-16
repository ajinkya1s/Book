package com.app.pojos;

import java.util.Date;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payments 
{
	private Integer id;
	private Double amount;
	private Ptype type;
	private Date transTime;; 
	private Date nextpayment_duedate;
	@JsonIgnore
	private User payId;
	
	public Payments() {
		System.out.println("In payment ctor");
	}

	public Payments(Integer id, Double amount, Ptype type, Date transaction_time,
			Date nextpayment_duedate) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.transTime = transaction_time;
		this.nextpayment_duedate = nextpayment_duedate;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Enumerated(EnumType.STRING)
	public Ptype getType() {
		return type;
	}

	public void setType(Ptype type) {
		this.type = type;
	}


	@Temporal(TemporalType.DATE)
	public Date getNextpayment_duedate() {
		return nextpayment_duedate;
	}

	public void setNextpayment_duedate(Date nextpayment_duedate) {
		this.nextpayment_duedate = nextpayment_duedate;
	}
	
	@ManyToOne
	@JoinColumn(name="userid")
	public User getPayId() {
		return payId;
	}

	public void setPayId(User payId) {
		this.payId = payId;
	}

	@Temporal(TemporalType.TIME)
	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}


	
	
}
