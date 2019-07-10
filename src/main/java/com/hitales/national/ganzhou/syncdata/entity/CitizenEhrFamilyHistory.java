package com.hitales.national.ganzhou.syncdata.entity;

import com.hitales.national.ganzhou.syncdata.converter.ListFamilyMedicalDiseaseConverter;
import com.hitales.national.ganzhou.syncdata.enums.FamilyMedicalDisease;
import com.hitales.national.ganzhou.syncdata.enums.MedicalFamilyRelationship;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * 档案医疗家族史
 *
 * @author harryhe
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen_ehr_family_history", comment = "居民健康档案-家族病史")
@Table(name = "citizen_ehr_family_history", indexes = {
        @Index(name = "citizen_ehr_family_history_idx_ehr_id", columnList = "ehr_id")
})
@Data
public class CitizenEhrFamilyHistory extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "ehr_id", columnDefinition = "bigint(20) unsigned default 0 comment '关联档案ID'")
    private Long ehrId;

    @Column(name = "type", columnDefinition = "tinyint(2) unsigned default 0 comment '家庭关系类型:1-父亲;2-母亲;3-兄弟姐妹;4-子女'")
    private MedicalFamilyRelationship Type;

    @Column(name = "disease", columnDefinition = "varchar(64) default '' comment '家族史疾病类型（多条以,分割):2-高血压;3-糖尿病;4-冠心病;5-慢性阻塞性肺疾病;6-恶性肿瘤;7-脑卒中;8-严重精神障碍;9-结核病;10-肝炎;11-先天畸形;12-其他'")
    @Convert(converter = ListFamilyMedicalDiseaseConverter.class)
    private List<FamilyMedicalDisease> disease;

}
