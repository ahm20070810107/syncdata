package com.hitales.national.ganzhou.syncdata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ganzhou_clinic")
@org.hibernate.annotations.Table(appliesTo = "ganzhou_clinic", comment = "赣州医疗机构数据")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned  comment '系统ID'")
    private Long id;

    @Column(name = "org_code", columnDefinition = "varchar(20)  DEFAULT '' COMMENT '机构代码'")
    private String orgCode;

    @Column(name = "org_name", columnDefinition = "varchar(100)  default '' comment '机构名称'")
    private String orgName;
}
