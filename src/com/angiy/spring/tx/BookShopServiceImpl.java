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

	//1.propagation:ָ�����񴫲���Ϊ��Ĭ��REQUIRE�����÷���������REQUIRES_NEW�����Լ�������
	//2.isolation:���뼶��READ_COMMITTED
	//3.noRollbackFor:��ĳ���쳣���ع�������ֵ��ı䣬�൱��û�и���
	//4.readonly:�������ݿ������Ż���
	//5.timeout:ָ��ǿ�ƻع�֮ǰ�������ռ�õ�ʱ��
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
