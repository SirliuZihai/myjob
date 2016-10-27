package com.zihai.service.iml;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	private UserInfoDao infodao;
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
	

	@Override
	public void insertTest(int num) {
		User user = new User();
		UserInfo info = new UserInfo();
		Account account = new Account();
		for(int i=0;i<num;i++){
			try {
				user.setUsername(RanddomDateUtil.randEnglish());
				user.setEmail(RanddomDateUtil.getEmail(3, 9));
				user.setPassword(UUID.randomUUID().toString());
				user.setPhone(RanddomDateUtil.getTel());
				user.setMakedatetime(new Date());
				dao.insertSelective(user);
			} catch (Exception e) {
				System.out.println(user.getUsername()+"重复了");
				continue;
			}
			info.setAddress(RanddomDateUtil.getRoad());
			info.setUsername(user.getUsername());
			info.setCountry("Chinese");
			info.setName(RanddomDateUtil.getChineseName());
			info.setSex(RanddomDateUtil.name_sex);
			infodao.insertSelective(info);
			account.setCredit("1");
			account.setUsername(user.getUsername());
			account.setMoney(new BigDecimal(RanddomDateUtil.getNum(0,9999999)).movePointLeft(2));
			account.setModifydate(new Date());
			accountDao.insertSelective(account);
			System.out.println("成功插入一条数据");

		}
		
	}
	
}
