package com.hitales.national.ganzhou.syncdata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 居民服务标签
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen_serve_tag", comment = "居民服务标签")
@Table(name = "citizen_serve_tag", indexes = {
        @Index(name = "idx_serve_tag_county", columnList = "county_id")
})
@DynamicUpdate
@DynamicInsert
@Data
@EqualsAndHashCode(callSuper = false)
public class CitizenServeTag extends AuditableEntity implements CopyPartially {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "tag_name", columnDefinition = "varchar(150) DEFAULT '' COMMENT '服务标签名'")
    private String tagName;

    @Column(name = "county_id", columnDefinition = "bigint(20) unsigned default 0 comment '标签所属行政县ID'")
    private Long countyId;
}
