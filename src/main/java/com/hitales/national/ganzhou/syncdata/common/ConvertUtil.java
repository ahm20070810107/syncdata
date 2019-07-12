package com.hitales.national.ganzhou.syncdata.common;

import com.hitales.national.ganzhou.syncdata.enums.CitizenGender;

/**
 * @Author: jingang
 * @Date: 2019/7/12 14:42
 * @Description:
 */
public class ConvertUtil {

	public static CitizenGender getGender(Integer gender){
		if(gender.equals(1)){
			return CitizenGender.MALE;
		}
		if(gender.equals(2)){
			return CitizenGender.FEMALE;
		}
		return CitizenGender.NOT_SPECIFIED;
	}
}
