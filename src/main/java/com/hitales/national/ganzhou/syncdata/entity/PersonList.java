package com.hitales.national.ganzhou.syncdata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-03
 * @time:13:09
 */



@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ganzhou_person_list" ,indexes = {@Index(name = "idx_ganzhou_person_list_state", columnList = "sync_state")
})
@org.hibernate.annotations.Table(appliesTo = "ganzhou_person_list", comment = "赣州居民列表数据")
public class PersonList {
    @Id
    @Column(name = "personid", columnDefinition = "varchar(100)  DEFAULT ''")
    private String personid;

    @Column(name = "fz_doctor", columnDefinition = "varchar(50)  DEFAULT ''")
    private String fzDoctor;

    @Column(name = "record_org", columnDefinition = "varchar(30)  DEFAULT ''")
    private String record_org;

    @Column(name = "record_o_name", columnDefinition = "varchar(50)  DEFAULT ''")
    private String recordOName;

    @Column(name = "record_op", columnDefinition = "varchar(50)  DEFAULT ''")
    private String recordOp;

    @Column(name = "rn", columnDefinition = "varchar(50)  DEFAULT ''")
    private String rn;

    @Column(name = "idkey", columnDefinition = "varchar(50)  DEFAULT ''")
    private String idkey;

    @Column(name = "district_code", columnDefinition = "varchar(50)  DEFAULT ''")
    private String districtCode;

    @Column(name = "short_name", columnDefinition = "varchar(50)  DEFAULT ''")
    private String shortName;

    @Column(name = "name", columnDefinition = "varchar(50)  DEFAULT ''")
    private String name;

    @Column(name = "sync_state", columnDefinition = "tinyint(2)  DEFAULT '0' comment '0 未同步 1 已同步' ")
    private Integer syncState = 0;
}
