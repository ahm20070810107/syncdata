package com.hitales.national.ganzhou.syncdata.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 生活环境-厕所
 *
 * @author jingang
 */
@EnumTag(key = "habitat_toilet", name = "生活环境-厕所")
public enum HabitatToilet implements IntEnum, Describable {
	SANITARY_TOILET(1, "卫生厕所"),
	ONE_OR_TWO_SEPTIC_TANKS(2, "一格或二格粪池式"),
	CLOSESTOOL(3, "马桶"),
	THE_OUTDOOR_CESSPIT(4, "露天粪坑"),
	SIMPLE_LATRINE(5, "简易棚厕"),;


	private Integer key;
	private String desc;

	private HabitatToilet(Integer key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

}