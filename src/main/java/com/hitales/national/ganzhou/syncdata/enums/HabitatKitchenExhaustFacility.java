package com.hitales.national.ganzhou.syncdata.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 生活环境-厨房排风设施
 *
 * @author jingang
 */
@EnumTag(key = "habitat_kitchen_exhaust_facility", name = "生活环境-厨房排风设施")
public enum HabitatKitchenExhaustFacility implements IntEnum, Describable {
	NONE(1, "无"),
	HOOD(2, "油烟机"),
	VENTILATOR(3, "换气扇"),
	CHIMNEY(4, "烟囱"),;


	private Integer key;
	private String desc;

	private HabitatKitchenExhaustFacility(Integer key, String desc) {
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