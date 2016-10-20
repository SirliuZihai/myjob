package com.zihai.service.iml;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.dao.UserDao;
import com.zihai.entity.Account;
import com.zihai.entity.User;
import com.zihai.service.PrintService;

@Service("printEvents")
public class PrintEvents implements PrintService {
	private Logger log = Logger.getLogger("printEvents");
	@Autowired
	private UserDao dao;
	public void print(String name) {
		User user = new User();
		Account account = new Account();
		account.setCredit(name);
		user.setAccount(account);
		log.info("print money is "+dao.getAccount(user).getAccount().getMoney());
		log.info("print time is "+dao.getAccount(user).getModifydatetime());
		log.info("print qq email is "+dao.selectByPrimaryKey("qq").getEmail());
		log.error("printEvent.changed");

	}

}
