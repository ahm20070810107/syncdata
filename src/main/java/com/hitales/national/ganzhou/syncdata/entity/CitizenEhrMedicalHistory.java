package com.hitales.national.ganzhou.syncdata.entity;

import com.hitales.national.ganzhou.syncdata.enums.CitizenMedicalDisease;
import com.hitales.national.ganzhou.syncdata.enums.MedicalHistoryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 档案医疗既往史
 *
 * @author harryhe
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen_ehr_medical_history", comment = "居民健康档案-既往史")
@Table(name = "citizen_ehr_medical_history", indexes = {
        @Index(name = "citizen_ehr_medical_history_idx_ehr_id", columnList = "ehr_id")
})
@Data
public class CitizenEhrMedicalHistory extends AuditableEntity {

    public CitizenEhrMedicalHistory() {
    }

    private CitizenEhrMedicalHistory(Long ehrId, MedicalHistoryType type, String name, CitizenMedicalDisease disease, Date confirmDate) {
        this.ehrId = ehrId;
        this.type = type;
        this.name = name;
        this.disease = disease;
        this.confirmDate = confirmDate;
    }

    public static CitizenEhrMedicalHistory ofDisease(Long ehrId, CitizenMedicalDisease disease, Date confirmDate) {
        return new CitizenEhrMedicalHistory(ehrId, MedicalHistoryType.DISEASE, null, disease, confirmDate);
    }

    public static CitizenEhrMedicalHistory ofOperation(Long ehrId, String name, Date confirmDate) {
        return new CitizenEhrMedicalHistory(ehrId, MedicalHistoryType.OPERATION, name, null, confirmDate);
    }

    public static CitizenEhrMedicalHistory ofWound(Long ehrId, String name, Date confirmDate) {
        return new CitizenEhrMedicalHistory(ehrId, MedicalHistoryType.WOUND, name, null, confirmDate);
    }

    public static CitizenEhrMedicalHistory ofBloodTrans(Long ehrId, String name, Date confirmDate) {
        return new CitizenEhrMedicalHistory(ehrId, MedicalHistoryType.BLOOD_TRANS, name, null, confirmDate);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "ehr_id", columnDefinition = "bigint(20) unsigned default 0 comment '关联档案ID'")
    private Long ehrId;

    @Column(name = "type", columnDefinition = "tinyint(2) unsigned default 0 comment '既往史类型:1-疾病史;2-手术史;3-外伤史;4-输血史'")
    private MedicalHistoryType type;

    @Column(name = "name", columnDefinition = "varchar(30) default '' comment '既往史名称'")
    private String name;

    @Column(name = "disease", columnDefinition = "tinyint(2) unsigned default 0 comment '疾病（仅既往史类型疾病时有）:2-高血压;3-糖尿病;4-冠心病;5-慢性阻塞性肺疾病;6-恶性肿瘤;7-脑卒中;8-严重精神障碍;9-结核病;10-肝炎;11-其他法定传染病;12-职业病;13-其他'")
    private CitizenMedicalDisease disease;

    @Column(name = "confirm_date", columnDefinition = "datetime  comment '确认时间'")
    private Date confirmDate;


}
