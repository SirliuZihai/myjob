package com.zihai.service.print;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zihai.test.dao.UserDao;

@Service("printEvents")
public class PrintEvents implements PrintService {
	private Logger log = Logger.getLogger("printEvents");
	@Autowired
	private UserDao dao;
	public void print(String name) {
		log.info("print name is "+dao.getPassword(name));
		log.error("printEvent.changed");

	}

}
