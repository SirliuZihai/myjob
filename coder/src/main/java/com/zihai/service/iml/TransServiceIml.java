package com.zihai.service.iml;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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

	@Transactional(rollbackFor={BusinessException.class})
	public boolean trans(TransLog trans,String...args) throws BusinessException{
		System.out.println("the trans is from "+trans.getUserFrom());
		Account accountFrom = accountdao.selectByPrimaryKeyForUpdate(trans.getUserFrom());
		accountFrom.setMoney(accountFrom.getMoney().add(trans.getAmt().negate()));
		if(accountFrom.getMoney().compareTo(new BigDecimal(0)) ==-1)
			throw new BusinessException("余额不足");
		accountdao.updateByPrimaryKey(accountFrom);
		Account accountTo = accountdao.selectByPrimaryKeyForUpdate(trans.getUserTo());
		accountTo.setMoney(accountTo.getMoney().add(trans.getAmt()));
		accountdao.updateByPrimaryKey(accountTo);
		trans.setState("0");
		if(args.length==1){
			try {
				Thread.currentThread().sleep(Integer.parseInt(args[0]));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public String getSerialno() {
		return "TEST"+transdao.getSeq();
	}
}
