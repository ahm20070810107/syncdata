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
@Table(name = "ganzhou_person_tag" ,indexes = {@Index(name = "idx_ganzhou_person_tag_personid", columnList = "personid")})
@org.hibernate.annotations.Table(appliesTo = "ganzhou_person_tag", comment = "赣州居民标签数据")
public class PersonTag {

    @Id
    @Column(name = "personid", columnDefinition = "varchar(100)  DEFAULT ''")
    private String personid;

    @Column(name = "birthday", columnDefinition = "varchar(40)  DEFAULT ''")
    private String birthday;

    @Column(name = "mxb_12", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb12;

    @Column(name = "mxb_11", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb11;

    @Column(name = "mxb_14", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb14;

    @Column(name = "mxb_13", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb13;

    @Column(name = "p_card_no", columnDefinition = "varchar(40)  DEFAULT ''")
    private String pCardNo;

    @Column(name = "blood_xb", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodXb;

    @Column(name = "blood_xa", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodXa;

    @Column(name = "mxb_qtvalue", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxbQtvalue;

    @Column(name = "mxb_10", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb10;

    @Column(name = "blood_ab", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodAb;

    @Column(name = "home_address", columnDefinition = "varchar(40)  DEFAULT ''")
    private String homeAddress;

    @Column(name = "blood_xo", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodXo;

    @Column(name = "qt_memo", columnDefinition = "varchar(40)  DEFAULT ''")
    private String qtMemo;

    @Column(name = "fz_doc_phone", columnDefinition = "varchar(40)  DEFAULT ''")
    private String fzDocPhone;

    @Column(name = "fz_doctor", columnDefinition = "varchar(100)  DEFAULT ''")
    private String fzDoctor;

    @Column(name = "gms_4", columnDefinition = "varchar(40)  DEFAULT ''")
    private String gms4;

    @Column(name = "gms_5", columnDefinition = "varchar(40)  DEFAULT ''")
    private String gms5;

    @Column(name = "gms_2", columnDefinition = "varchar(40)  DEFAULT ''")
    private String gms2;

    @Column(name = "gms_3", columnDefinition = "varchar(40)  DEFAULT ''")
    private String gms3;

    @Column(name = "home_phone", columnDefinition = "varchar(40)  DEFAULT ''")
    private String homePhone;

    @Column(name = "blood_rhy", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodRhy;

    @Column(name = "spec_linkphone", columnDefinition = "varchar(40)  DEFAULT ''")
    private String specLinkphone;

    @Column(name = "record_o_name", columnDefinition = "varchar(40)  DEFAULT ''")
    private String recordOName;

    @Column(name = "mxb_8", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb8;

    @Column(name = "mxb_9", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb9;

    @Column(name = "record_o_phone", columnDefinition = "varchar(40)  DEFAULT ''")
    private String recordOPhone;

    @Column(name = "gms_qt", columnDefinition = "varchar(40)  DEFAULT ''")
    private String gmsQt;

    @Column(name = "mxb_1", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb1;

    @Column(name = "blood_bx", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodBx;

    @Column(name = "mxb_2", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb2;

    @Column(name = "mxb_3", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb3;

    @Column(name = "sex_name", columnDefinition = "varchar(40)  DEFAULT ''")
    private String sexName;

    @Column(name = "mxb_4", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb4;

    @Column(name = "mxb_5", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb5;

    @Column(name = "spec_linkman", columnDefinition = "varchar(40)  DEFAULT ''")
    private String specLinkman;

    @Column(name = "mxb_6", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb6;

    @Column(name = "mxb_7", columnDefinition = "varchar(40)  DEFAULT ''")
    private String mxb7;

    @Column(name = "reCode", columnDefinition = "varchar(40)  DEFAULT ''")
    private String recode;

    @Column(name = "name", columnDefinition = "varchar(40)  DEFAULT ''")
    private String name;

    @Column(name = "blood_rhb", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodRhb;

    @Column(name = "blood_rha", columnDefinition = "varchar(40)  DEFAULT ''")
    private String bloodRha;

}
