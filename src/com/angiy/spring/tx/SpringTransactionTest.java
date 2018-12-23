package com.angiy.spring.tx;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTransactionTest {
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private BookShopDao bookShopDao = ctx.getBean(BookShopDao.class);
	private BookShopService BookShopService = ctx.getBean(BookShopService.class);
	private Cashier cashier = ctx.getBean(Cashier.class);

	@Test
	public void testBookShopDaoFindPriceByIsbn() {
		System.out.println(bookShopDao.findBookPriceByIsbn("1001"));
	}
	
	@Test
	public void testBookShopDaoUpdateBookStock() {
		bookShopDao.updateBookStock("1001");
	}
	
	@Test
	public void testBookShopDaoUpdateUserAccount() {
		bookShopDao.updateUserAccount("AA", 100);
	}
	
	@Test
	public void testBookShopService() {
		BookShopService.purchase("AA", "1001");
	}
	
	@Test
	public void testTransactionPropagation() {
		cashier.checkout("AA", Arrays.asList("1001", "1002"));
	}

}
