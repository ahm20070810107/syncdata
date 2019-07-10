package com.hitales.national.ganzhou.syncdata.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 生活环境-燃料类型
 *
 * @author jingang
 */
@EnumTag(key = "habitat_fuel_type", name = "生活环境-燃料类型")
public enum HabitatFuelType implements IntEnum, Describable {
	LIQUEFIED_GAS(1, "液化气"),
	COAL(2, "煤"),
	NATURAL_GAS(3, "天然气"),
	marsh_gas(4, "沼气"),
	FIREWOOD(5, "柴火"),
	OTHER(6, "其他"),;


	private Integer key;
	private String desc;

	private HabitatFuelType(Integer key, String desc) {
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