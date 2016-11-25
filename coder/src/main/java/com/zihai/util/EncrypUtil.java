package com.zihai.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.zihai.entity.User;

public class EncrypUtil {
	//private RandomNumberGenerator randomNumberGenerator =new SecureRandomNumberGenerator();
	private static String algorithmName = "md5";
	private static final int hashIterations = 2;
	public static void encryptPassword(User user) {
		String newPassword = new SimpleHash(algorithmName,user.getPassword(),ByteSource.Util.bytes("zihai"),
							hashIterations).toHex();
		user.setPassword(newPassword);
	}
	
	public static void main(String args[]){
		String newPassword = new SimpleHash(algorithmName,"111111",ByteSource.Util.bytes("zihai"),
				hashIterations).toHex();
		System.out.println(newPassword);
	}
}
