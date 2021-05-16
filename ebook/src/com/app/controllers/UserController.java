package com.app.controllers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.app.daos.IUserDao;
import com.app.pojos.Book;
import com.app.pojos.IssueRecord;
import com.app.pojos.Payments;
import com.app.pojos.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
@CrossOrigin
@MultipartConfig
public class UserController 
{
	int otpNo;
	@Autowired
	private IUserDao dao;
	
	@Autowired
	private JavaMailSender sender;

	@PutMapping("/login")
	public ResponseEntity<?> validate(@RequestBody User u)
	{
		System.out.println(u.getEmail() + " "+ u.getPassword());
		
		try {
			User user = dao.validatUser(u.getEmail(), u.getPassword());
			if (user != null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);	
		}
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
	}
	@PostMapping("/chpa/{otp}")
	//public ResponseEntity<?> changePassword(@RequestParam String email,@RequestParam String password,@RequestParam String newPassword)
	public ResponseEntity<?> changePassword(@RequestBody User u,@PathVariable Integer otp)
	{
		if(otp==otpNo)
		{
		User user = dao.changePassword(u.getEmail(),u.getPassword());
		if(user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);			
		
	}
	@PostMapping("/edit")
	public ResponseEntity<?> editProfile(@RequestBody User u )
	{
		User user; 
		try {
			user = dao.changeProfile(u.getEmail(),u.getPassword(),u.getName(),u.getPhone());
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);			
		}
	}
	@GetMapping("/find/{bookname}")
	public ResponseEntity<?> findBook(@PathVariable String bookname)
	{
		System.out.println("bookname"+bookname);
		Book book = dao.findBook(bookname);
		//Book book=findbook2(book)
		if(book != null)
		{
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/deleteBook/{bookname}")
	public ResponseEntity<?> deleteUser(@PathVariable String bookname)
	{
		System.out.println("bookname"+bookname);
		Book book = dao.findBook(bookname);
		dao.daleteBook(book);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
//	public ResponseEntity<?> findbook2(Book book)
//	{
//		
//	}
	
	
	@PostMapping("/pay/{id}")
	public ResponseEntity<?> makePayment(@RequestBody Payments p1,@PathVariable Integer id)
	{
		
		try {
			dao.makePayment(id,p1.getType(),p1.getAmount());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestParam(value="file") MultipartFile file,@RequestParam(value="user") String u1)
	{
		try {
			User u2=new ObjectMapper().readValue(u1,User.class);
			u2.setImageblob(file.getBytes());
			dao.register(u2);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		//dao.register(u1);
		
	}
	@PostMapping("/uploaddata")
	public ResponseEntity<?> uploadData(@RequestParam("datafile") MultipartFile datafile)
	{
		try {
			dao.savaData(datafile);
			return new ResponseEntity<String>("all right", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/pass/{email}")
	public ResponseEntity<?> checkOtp(@PathVariable String email)
	{
		System.out.println("in ch pass cont");
		Random rand=new Random();
		otpNo=rand.nextInt(1000);
		SimpleMailMessage mesg=new SimpleMailMessage();
		String eemail=email+".com";
		mesg.setTo(eemail);
		mesg.setSubject("Ebook Management System");
		String name="your otp is"+otpNo;
		mesg.setText(name);
		sender.send(mesg);
		return new ResponseEntity<Integer>(otpNo, HttpStatus.OK);
	}
	
	@PutMapping("/getdetails/{bookId}")
	public ResponseEntity<?> getDetails(@RequestBody Integer userId,@PathVariable Integer bookId)
	{
		System.out.println("in con issue");
		IssueRecord rf=new IssueRecord();
//		Book b2=new Book();
//		b2.setId(bookId);
//		rf.setBkid(b2);
//		User u2= new User();
//		u2.setId(userId);
//		rf.setUsrId(u2);
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
		Date date=new Date();
		//System.out.println(dateFormat.format(date));
		rf.setIssueDate(date);
		dao.insertDetail(rf,userId,bookId);
		return new ResponseEntity<String>("all right", HttpStatus.OK);
	}
	
	@GetMapping("/getIssueDetail/{userId}")
	public ResponseEntity<?> getIssueDetail(@PathVariable Integer userId)
	{
		List<IssueRecord> detail=dao.getIssueDetail(userId);
		return new ResponseEntity<List<IssueRecord>>(detail, HttpStatus.OK);
	}
}	

