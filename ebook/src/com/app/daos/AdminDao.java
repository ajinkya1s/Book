package com.app.daos;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Book;
import com.app.pojos.User;
@Repository
@Transactional
public class AdminDao implements IAdminDao{
	@Autowired
	private SessionFactory sf;

	@Override
	public List<User> listAllUser() {
		String jpql="select u from User u";
		return sf.getCurrentSession().createQuery(jpql, User.class).getResultList();
	}

	@Override
	public List<Book> listAllBooks() {
		String jpql="select b from Book b";
		return sf.getCurrentSession().createQuery(jpql, Book.class).getResultList();
	}

	@Override
	public String uploadfile(Book book) {
		Book b1=new Book();
		b1.setName(book.getName());
		b1.setSubject(book.getSubject());
		b1.setAuthor(book.getAuthor());
		b1.setPrice(book.getPrice());
		b1.setFileblob(book.getFileblob());
		b1.setIsbn(book.getIsbn());
		b1.setType(book.getType());
		sf.getCurrentSession().persist(b1);
		if(b1.getId()==null)
		{
			return "file is not uploaded";
		}
		return "file is uploaded";
	}

	@Override
	public User findUser(String username) {
		String jpql="select u from User u where u.name=:nm";
		return sf.getCurrentSession().createQuery(jpql, User.class).setParameter("nm",username).getSingleResult();
	}

	@Override
	public void savaData(MultipartFile datafile) throws Exception {
		String folder="/home/sunbeam/ServerProject/";
		byte[] bytes=datafile.getBytes();
		Path path=Paths.get(folder+datafile.getOriginalFilename());
		Files.write(path,bytes);
	}

	@Override
	public void daleteUser(User u) {
		sf.getCurrentSession().delete(u);
	}
	
	
	

}
