package com.hitales.national.ganzhou.syncdata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 服务标签与居民的对应关系
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen_serve_tag_citizen", comment = "服务标签与居民对应表")
@Table(name = "citizen_serve_tag_citizen", indexes = {
        @Index(name = "idx_serve_tag_mapping_citizen", columnList = "citizen_id"),
        @Index(name = "idx_serve_tag_mapping_tag", columnList = "tag_id")
})
@DynamicUpdate
@DynamicInsert
@Data
@EqualsAndHashCode(callSuper = false)
public class CitizenServeTagMapping extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "citizen_id", columnDefinition = "bigint(20) unsigned default 0 comment '居民ID'")
    private Long citizenId;

    @Column(name = "tag_id", columnDefinition = "bigint(20) unsigned default 0 comment '标签ID'")
    private Long tagId;

    @Column(name = "package_id", columnDefinition = "bigint(20) unsigned default 0 comment '服务包ID'")
    private Long packageId;



}
