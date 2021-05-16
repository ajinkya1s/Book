package com.app.daos;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Book;
import com.app.pojos.User;

public interface IAdminDao {
	List<User> listAllUser();
	List<Book> listAllBooks();
	String uploadfile(Book book);
	User findUser(String bookname);
	void savaData(MultipartFile datafile) throws Exception;
	void daleteUser(User user);

}
