package com.angiy.spring.tx.xml.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angiy.spring.tx.xml.service.BookShopService;
import com.angiy.spring.tx.xml.service.Cashier;

public class CashierImpl implements Cashier {
	
	private BookShopService BookShopService;
	
	public void setBookShopService(BookShopService bookShopService) {
		BookShopService = bookShopService;
	}

	@Transactional()
	@Override
	public void checkout(String username, List<String> isbns) {
		for(String isbn : isbns) {
			BookShopService.purchase(username, isbn);
		}
	}
	
	
	
}
