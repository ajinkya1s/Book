package com.app.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.app.daos.IAdminDao;
import com.app.pojos.Book;
import com.app.pojos.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@MultipartConfig
public class AdminController {
	@Autowired
	private IAdminDao dao;
	String file;
	@GetMapping("/users")
	public ResponseEntity<?> listAllUsers()
	{
		List<User> list = dao.listAllUser();
		if(list != null)
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/books")
	public ResponseEntity<?> listAllBooks()
	{
		List<Book> list = dao.listAllBooks();
		if(list != null)
			return new ResponseEntity<List<Book>>(list, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/uploads")
	public ResponseEntity<?> uploadfile(@RequestParam(value="file") MultipartFile file,@RequestParam(value="book") String book ) throws JsonParseException, JsonMappingException, IOException
	{
		try {
			Book bupload=new ObjectMapper().readValue(book, Book.class);
			//name=file.getOriginalFilename();
			bupload.setFileblob(file.getBytes());
			String msg=dao.uploadfile(bupload);
			System.out.println(msg);
			dao.savaData(file);
			return new ResponseEntity<String>("all right", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		//return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	@GetMapping("/findUser/{username}")
	public ResponseEntity<?> findUser(@PathVariable String username)
	{
		System.out.println("bookname"+username);
		User user = dao.findUser(username);
		if(user != null)
		{
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/deleteUser/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username)
	{
		System.out.println("bookname"+username);
		User user = dao.findUser(username);
		dao.daleteUser(user);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	//@PostMapping("/uploaddata")
	//public ResponseEntity<?> uploadData(@RequestParam("datafile") MultipartFile datafile)
	//{
//		try {
//			dao.savaData(datafile);
//			return new ResponseEntity<String>("all right", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
	//}
	@GetMapping("/download/{name}")
	public ResponseEntity<?> downloadBook(@PathVariable String name) throws Exception
	{
		System.out.println("in download");
		//String name=file.getOriginalFilename();
		String name2=(name+".pdf");
		String filename="/home/sunbeam/ServerProject/"+name2;
		System.out.println(filename);
		File file2=new File(filename);
		InputStreamResource resource=new InputStreamResource(new FileInputStream(file2));
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Decomposition",String.format("attachment;filename=\"%s\"",file2.getName()));
		headers.add("Cache-Control","no-cache,no-store,must-revalidate");
		headers.add("Expires","0");
		
		ResponseEntity<Object> responseEntity=ResponseEntity.ok().headers(headers)
												.contentLength(file2.length())
												.contentType(MediaType.parseMediaType("application/pdf")).body(resource);
		return responseEntity;	
	}
	@PostMapping("/download2")
	public ResponseEntity<?> downloadBook2(@RequestBody Book b) throws Exception
	{
		System.out.println("in download");
		//String name=file.getOriginalFilename();
		//String name2=(name+".pdf");
		String filename="/home/sunbeam/ServerProject/"+b.getName();
		System.out.println(filename);
		File file2=new File(filename);
		InputStreamResource resource=new InputStreamResource(new FileInputStream(file2));
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Decomposition",String.format("attachment;filename=\"%s\"",file2.getName()));
		headers.add("Cache-Control","no-cache,no-store,must-revalidate");
		headers.add("Expires","0");
		
		ResponseEntity<Object> responseEntity=ResponseEntity.ok().headers(headers)
												.contentLength(file2.length())
												.contentType(MediaType.parseMediaType("application/pdf")).body(resource);
		return responseEntity;	
	}
}
