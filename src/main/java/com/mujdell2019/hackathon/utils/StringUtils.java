package com.mujdell2019.hackathon.utils;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class StringUtils {

	public boolean isEmpty(String value) {
		return null == value || "".equals(value);
	}
	
	public boolean containsEmpty(List<String> values) {
		for (String item : values)
			if (isEmpty(item)) return true;
		return false;
	}
}
