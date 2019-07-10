package com.hitales.national.ganzhou.syncdata.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 生活环境-饮水
 *
 * @author jingang
 */
@EnumTag(key = "habitat_drinking_water", name = "生活环境-饮水")
public enum HabitatDrinkingWater implements IntEnum, Describable {
	TAP_WATER(1, "自来水"),
	FILTERED_WATER(2, "经净化过滤的水"),
	WELL_WATER(3, "井水"),
	RIVER_WATER(4, "河湖水"),
	POND_WATER(5, "塘水"),
	OTHER(6, "其他"),;


	private Integer key;
	private String desc;

	private HabitatDrinkingWater(Integer key, String desc) {
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