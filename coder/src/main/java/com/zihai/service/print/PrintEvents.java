package com.zihai.service.print;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.dao.UserDao;
import com.zihai.entity.User;

@Service("printEvents")
public class PrintEvents implements PrintService {
	private Logger log = Logger.getLogger("printEvents");
	@Autowired
	private UserDao dao;
	public void print(String name) {
		User user = new User();
		user.setUsername(name);
		log.info("print money is "+dao.getAccount(user).getAccount().getMoney());
		log.error("printEvent.changed");

	}

}
