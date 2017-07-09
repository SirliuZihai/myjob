package com.zihai.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		try {
			System.out.println("user converters");
			return new SimpleDateFormat("yyyy/mm/dd").parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

}
