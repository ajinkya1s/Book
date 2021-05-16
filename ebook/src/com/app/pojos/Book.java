package com.app.pojos;

import java.util.List;
import java.sql.Blob;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "books")
public class Book
{
	private Integer id;
	private String name,author,subject;
	private Double price;
	private Integer isbn;
	private FileType type;
	private byte[] fileblob;
	//private Blob fileblob;
	
	@JsonIgnore
	private IssueRecord recId;
	public Book() {
		// TODO Auto-generated constructor stub
	}
	public Book(String name, String author, String subject, Double price, Integer isbn,byte[] fileblob,FileType type) {
		super();
		this.type=type;
		this.name = name;
		this.author = author;
		this.subject = subject;
		this.price = price;
		this.isbn = isbn;
		this.fileblob=fileblob;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Enumerated(EnumType.STRING)
	public FileType getType() {
		return type;
	}
	public void setType(FileType type) {
		this.type = type;
	}
	@Column(length = 30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 30)
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(length = 30)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getIsbn() {
		return isbn;
	}
	public void setIsbn(Integer isbn) {
		this.isbn = isbn;
	}
//	@OneToMany(mappedBy = "bkid" ,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	public List<Copy> getCopiesId() {
//		return copiesId;
//	}
//	public void setCopiesId(List<Copy> copiesId) {
//		this.copiesId = copiesId;
//	}
	
	@OneToOne(mappedBy = "bkid",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	public IssueRecord getRecId() {
		return recId;
	}
	public void setRecId(IssueRecord recId) {
		this.recId = recId;
	}
	@Lob
	//@JsonIgnore
	//@Column(columnDefinition="longblob")
	public byte[] getFileblob() {
		return fileblob;
	}
	public void setFileblob(byte[] fileblob) {
		this.fileblob = fileblob;
	}
	
	public void addBookid(IssueRecord iobj)
	{
		this.setRecId(iobj);
		iobj.setBkid(this);;
	}
	
//	public void removeUserId(IssueRecord iobj)
//	{
//		this.listRec.remove(iobj);
//	}
}
