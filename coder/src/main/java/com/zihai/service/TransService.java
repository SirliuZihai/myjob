package com.zihai.service;

import com.zihai.entity.TransLog;

public interface TransService {
	public boolean trans(TransLog trans) throws Exception;
}
