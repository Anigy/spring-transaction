package com.angiy.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
	
	@Autowired
	private BookShopDao bookShopDao;

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void purchase(String username, String isbn) {
		int price = bookShopDao.findBookPriceByIsbn(isbn);
		bookShopDao.updateBookStock(isbn);
		bookShopDao.updateUserAccount(username, price);
	}

}
