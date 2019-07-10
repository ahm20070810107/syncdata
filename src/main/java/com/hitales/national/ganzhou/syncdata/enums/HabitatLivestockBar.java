package com.hitales.national.ganzhou.syncdata.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 生活环境-禽畜栏
 *
 * @author jingang
 */
@EnumTag(key = "habitat_livestock_bar", name = "生活环境-禽畜栏")
public enum HabitatLivestockBar implements IntEnum, Describable {
	NONE(1, "无"),
	SEPARATELY(2, "单设"),
	INDOOR(3, "室内"),
	OUTDOOR(4, "室外"),;


	private Integer key;
	private String desc;

	private HabitatLivestockBar(Integer key, String desc) {
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