package com.zihai.service.iml;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zihai.dao.AccountDao;
import com.zihai.dao.UserDao;
import com.zihai.dao.UserInfoDao;
import com.zihai.entity.Account;
import com.zihai.entity.User;
import com.zihai.entity.UserInfo;
import com.zihai.service.PrintService;
import com.zihai.util.RanddomDateUtil;

@Service("printEvents")
public class PrintEvents implements PrintService {
	private Logger log = Logger.getLogger("printEvents");
	@Autowired
	private UserDao dao;
	@Autowired
	private AccountDao accountDao;
	
	public void print(String name) {
		User user = new User();
		/*Account account = new Account();
		account.setCredit(name);*/
		//user.setAccount(account);
		user.setUsername(name);
		log.info("print money is "+dao.getAccount(user).getAccount().getMoney());
		log.info("print time is "+dao.getAccount(user).getModifydatetime());
		//log.info("print qq email is "+dao.selectByPrimaryKey("qq").getEmail());
		log.error("printEvent.changed");

	}
	
	@Cacheable(value="money",key="#name+'_money'")
	public String getMoney(String name){
		return String.valueOf(accountDao.selectByPrimaryKey(name).getMoney());
	}
	
	@CacheEvict(key="#name+'_money'",cacheNames="money")
	public void cleanCache(String name){
		
	}
}
