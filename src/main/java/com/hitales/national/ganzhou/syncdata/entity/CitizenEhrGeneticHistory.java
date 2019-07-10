package com.hitales.national.ganzhou.syncdata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 档案医疗遗传病史
 *
 * @author harryhe
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen_ehr_genetic_history", comment = "居民健康档案-遗传病史")
@Table(name = "citizen_ehr_genetic_history", indexes = {
        @Index(name = "citizen_ehr_genetic_history_idx_ehr_id", columnList = "ehr_id")
})
@Data
public class CitizenEhrGeneticHistory extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "ehr_id", columnDefinition = "bigint(20) unsigned default 0 comment '关联档案ID'")
    private Long ehrId;

    @Column(name = "name", columnDefinition = "varchar(30) default '' comment '遗传病名称'")
    private String name;

}
