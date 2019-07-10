package com.hitales.national.ganzhou.syncdata.converter;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.hitales.commons.enums.EnumCollector;
import com.hitales.national.ganzhou.syncdata.enums.DrugAllergyType;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.stream.Collectors;

public class ListDrugAllergyHistoryConverter implements AttributeConverter<List<DrugAllergyType>, String> {

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = ",";

	@Override
	public String convertToDatabaseColumn(List<DrugAllergyType> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "";
		} else {
			return attribute.stream()
					   .filter(item -> item != null)
					   .map(item -> item.getKey().toString())
					   .collect(Collectors.joining(SEPARATOR));
		}
	}

	@Override
	public List<DrugAllergyType> convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return Lists.newArrayList();
		} else {
			return Splitter.on(SEPARATOR).omitEmptyStrings().trimResults()
					   .splitToList(dbData)
					   .stream().map(str -> Ints.tryParse(str))
					   .filter(i -> i != null)
					   .map(i -> (DrugAllergyType) EnumCollector.forClass(DrugAllergyType.class).keyOf(i))
					   .filter(e -> e != null)
					   .collect(Collectors.toList());
		}
	}

}
