package com.angiy.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {

	@Autowired
	private BookShopDao bookShopDao;

	//1.propagation:指定事务传播行为，默认REQUIRE，调用方法的事务；REQUIRES_NEW事务自己的事务
	//2.isolation:隔离级别，READ_COMMITTED
	//3.noRollbackFor:对某种异常不回滚，所以值会改变，相当于没有隔离
	//4.readonly:帮助数据库引擎优化用
	//5.timeout:指定强制回滚之前事务可以占用的时间
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			isolation = Isolation.READ_COMMITTED,
			noRollbackFor= {BookStockException.class},
			readOnly=false,
			timeout=3)
	@Override
	public void purchase(String username, String isbn) {
		try {
			Thread.sleep(3100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int price = bookShopDao.findBookPriceByIsbn(isbn);
		bookShopDao.updateBookStock(isbn);
		bookShopDao.updateUserAccount(username, price);
	}

}
