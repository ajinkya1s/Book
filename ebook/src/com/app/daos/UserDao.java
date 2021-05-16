package com.app.daos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Book;
import com.app.pojos.IssueRecord;
import com.app.pojos.Payments;
import com.app.pojos.Ptype;
import com.app.pojos.User;

@Repository
@Transactional
public class UserDao implements IUserDao 
{
	@Autowired
	private SessionFactory sf;
	
	@Autowired
	private JavaMailSender sender;
	
	@Override
	public User validatUser(String email, String pass) {
		
		String jpql = "select u from User u where u.email=:em and u.password=:pass";
		return sf.getCurrentSession().createQuery(jpql, User.class).setParameter("em",email)
				.setParameter("pass",pass).getSingleResult();
	}

	@Override
	public User changePassword(String email, String password) {
		String jpql="select u from User u where u.email=:em";
		User user = sf.getCurrentSession().createQuery(jpql, User.class).setParameter("em",email).getSingleResult();
		user.setPassword(password);
		sf.getCurrentSession().update(user);
		return user;
	}
	@Override
	public User changeProfile(String email, String password, String name,String phone) {
		String jpql="select u from User u where u.email=:em and u.password=:pass";
		User user = sf.getCurrentSession().createQuery(jpql, User.class).setParameter("em",email).setParameter("pass", password).getSingleResult();
		user.setName(name);
		user.setPhone(phone);
		sf.getCurrentSession().update(user);
		return user;
	}

	@Override
	public Book findBook(String bookname) 
	{
		String jpql="select b from Book b where b.name=:nm";
		return sf.getCurrentSession().createQuery(jpql, Book.class).setParameter("nm",bookname).getSingleResult();
	}
	
	@Override
	public void makePayment(Integer id,Ptype type ,Double amount) 
	{
//		String jpql="insert into Payments p (p.amount,p.type,p.transTime,p.nextpayment_duedate,p.payId) values(:amt,:type,:ti,:date,:id)";
//		sf.getCurrentSession().createQuery(jpql, Payments.class).setParameter("amt",amount).
//				setParameter("type",type).setParameter("ti",LocalDateTime.now()).
//				setParameter("date", LocalDateTime.now()).setParameter("id", u.getId()).executeUpdate();
		//User u1=sf.getCurrentSession().get(User.class, id);
		Payments p1 = new Payments();
		p1.setType(type);
		p1.setAmount(amount);
		p1.setTransTime(null);
		p1.setNextpayment_duedate(null);
		User u1=new User();
		u1.setId(id);
		p1.setPayId(u1);
		//u1.addPayments(p1);
		sf.getCurrentSession().persist(p1);
	}
	public void register(User u)
	{
		
		User u1=new User();
		u1.setEmail(u.getEmail());
		u1.setName(u.getName());
		u1.setPassword(u.getPassword());
		u1.setPhone(u.getPhone());
		u1.setRole(u.getRole());
		u1.setImageblob(u.getImageblob());
		sf.getCurrentSession().persist(u1);
		SimpleMailMessage mesg=new SimpleMailMessage();
		mesg.setTo(u.getEmail());
		mesg.setSubject("Ebook Management System");
		mesg.setText("your account is activated");
		sender.send(mesg);
	}
	@Override
	public void savaData(MultipartFile datafile) throws Exception {
		String folder="/home/sunbeam/ServerProject/";
		byte[] bytes=datafile.getBytes();
		Path path=Paths.get(folder+datafile.getOriginalFilename());
		Files.write(path,bytes);
	}

	@Override
	public void daleteBook(Book book) {
		sf.getCurrentSession().delete(book);		
	}

	@Override
	public void insertDetail(IssueRecord rf,Integer userId,Integer bookId) {
		System.out.println("in dao issue");
		//IssueRecord rd=new IssueRecord();
		//rd.setBkid(rf.getBkid());
		//rd.setUsrId(rf.getUsrId());
		//rd.setIssueDate(rd.getIssueDate());
		User u=sf.getCurrentSession().get(User.class, bookId);
		Book b=sf.getCurrentSession().get(Book.class, userId);
		System.out.println(userId+" "+bookId);
		sf.getCurrentSession().persist(rf);
		u.addUserid(rf);
		b.addBookid(rf);
		
	}

	@Override
	public List<IssueRecord> getIssueDetail(Integer userId) {
		System.out.println(userId);
		//String jpql="select i from IssueRecord i where i.usrId=:ui";
		User i=sf.getCurrentSession().get(User.class,userId);
		return i.getListRec();
	}



}
