package com.hitales.national.ganzhou.syncdata.entity;

import com.hitales.national.ganzhou.syncdata.converter.ListDrugAllergyHistoryConverter;
import com.hitales.national.ganzhou.syncdata.converter.ListExposureHistoryConverter;
import com.hitales.national.ganzhou.syncdata.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen_ehr", comment = "居民健康档案")
@Table(name = "citizen_ehr", indexes = {
        @Index(name = "citizen_ehr_idx_citizen_id", columnList = "citizen_id")
})
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = false)
public class CitizenEhr extends AuditableEntity implements CopyPartially {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "citizen_id", columnDefinition = "bigint(20) unsigned comment '居民id'")
    private Long citizenId;


    @Column(name = "drug_allergy_history", columnDefinition = "varchar(64) default '' comment '药物过敏史（多条以,分割):1-青霉素;2-磺胺;3-链霉素;4-其他'")
    @Convert(converter = ListDrugAllergyHistoryConverter.class)
    private List<DrugAllergyType> drugAllergyHistory;

    @Column(name = "exposure_history", columnDefinition = "varchar(64) default '' comment '暴露史（多条以,分割):1-化学品;2-毒物;3-射线'")
    @Convert(converter = ListExposureHistoryConverter.class)
    private List<ExposureType> exposureHistory;

//    @Column(name = "medical_disease_history", columnDefinition = "varchar(500) default '' comment '疾病史'")
//    private String medical_disease_history;
//
//    @Column(name = "medical_operation_history", columnDefinition = "varchar(500) default '' comment '手术史'")
//    private String medical_operation_history;
//
//    @Column(name = "medical_wound_history", columnDefinition = "varchar(500) default '' comment '外伤史'")
//    private String medical_wound_history;
//
//    @Column(name = "medical_blood_trans_history", columnDefinition = "varchar(500) default '' comment '输血史'")
//    private String medical_blood_trans_history;
//
//    @Column(name = "family_medical_history", columnDefinition = "varchar(500) default '' comment '家族病史'")
//    private String family_medical_history;
//
//    @Column(name = "genetic_history", columnDefinition = "varchar(500) default '' comment '遗传病史'")
//    private String genetic_history;


    @Column(name = "habitat_kitchen_exhaust_facility", columnDefinition = "tinyint(2) unsigned default 0  comment '生活环境-厨房排风设施:1-无;2-油烟机;3-换气扇;4-烟囱'")
    private HabitatKitchenExhaustFacility habitatKitchenExhaustFacility;

    @Column(name = "habitat_fuel_type", columnDefinition = "tinyint(2) unsigned default 0  comment '生活环境-燃料类型:1-液化气;2-煤;3-天然气;4-沼气;5-柴火;6-其他'")
    private HabitatFuelType habitatFuelType;

    @Column(name = "habitat_drinking_water", columnDefinition = "tinyint(2) unsigned default 0  comment '生活环境-饮水:1-自来水;2-经净化过滤的水;3-井水;4-河湖水;5-塘水;6-其他'")
    private HabitatDrinkingWater habitatDrinkingWater;

    @Column(name = "habitat_toilet", columnDefinition = "tinyint(2) unsigned default 0  comment '生活环境-厕所:1-卫生厕所;2-一格或二格粪池式;3-马桶;4-露天粪坑;5-简易棚厕'")
    private HabitatToilet habitatToilet;

    @Column(name = "habitat_livestock_bar", columnDefinition = "tinyint(2) unsigned default 0  comment '生活环境-禽畜栏:1-无;2-单设;3-室内;4-室外'")
    private HabitatLivestockBar habitatLivestockBar;
}
