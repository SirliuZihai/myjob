package com.zihai.service.iml;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zihai.dao.AccountDao;
import com.zihai.dao.TransLogDao;
import com.zihai.entity.Account;
import com.zihai.entity.TransLog;
import com.zihai.service.TransService;
import com.zihai.util.BusinessException;

@Service
public class TransServiceIml implements TransService {
	@Autowired
	private AccountDao accountdao;
	@Autowired
	private TransLogDao transdao;

	@Transactional
	public boolean trans(TransLog trans) throws Exception{
		System.out.println("the trans is from "+trans.getUserFrom());
		transdao.insertSelective(trans);
		Account accountFrom = accountdao.selectByPrimaryKey(trans.getUserFrom());
		accountFrom.setMoney(accountFrom.getMoney().add(trans.getAmt().negate()));
		if(accountFrom.getMoney().compareTo(new BigDecimal(0)) ==-1)
			throw new BusinessException("余额不足");
		Account accountTo = accountdao.selectByPrimaryKey(trans.getUserTo());
		accountTo.setMoney(accountTo.getMoney().add(trans.getAmt()));
		trans.setState("0");
		transdao.updateByPrimaryKey(trans);
		// TODO Auto-generated method stub
		return true;
	}

}
