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
@Table(name = "ganzhou_person" ,indexes = {@Index(name = "idx_ganzhou_person_personid", columnList = "personid"),
@Index(name = "idx_ganzhou_person_idno", columnList = "idno")
})
@org.hibernate.annotations.Table(appliesTo = "ganzhou_person", comment = "赣州居民数据")
public class Person {
    @Id
    @Column(name = "personid", columnDefinition = "varchar(100)  DEFAULT ''")
    private String personid;

    @Column(name = "education", columnDefinition = "varchar(100)  DEFAULT ''")
    private String education;

    @Column(name = "sf_update_time", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sfUpdateTime;

    @Column(name = "p_card_no", columnDefinition = "varchar(100)  DEFAULT ''")
    private String pCardNo;

    @Column(name = "yb2type", columnDefinition = "varchar(100)  DEFAULT ''")
    private String yb2type;

    @Column(name = "jws_qz_yy4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzYy4;

    @Column(name = "jws_qz_yy3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzYy3;

    @Column(name = "fmid", columnDefinition = "varchar(100)  DEFAULT ''")
    private String fmid;

    @Column(name = "jws_qz_yy2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzYy2;

    @Column(name = "jws_s_zg1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsSZg1;

    @Column(name = "jws_qz_yy1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzYy1;

    @Column(name = "sh_qxl", columnDefinition = "varchar(100)  DEFAULT ''")
    private String shQxl;

    @Column(name = "jws_qz_yy6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzYy6;

    @Column(name = "sh_cs", columnDefinition = "varchar(100)  DEFAULT ''")
    private String shCs;

    @Column(name = "jws_qz_yy5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzYy5;

    @Column(name = "jzs_zn_4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZn4;

    @Column(name = "jzs_zn_3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZn3;

    @Column(name = "jzs_zn_6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZn6;

    @Column(name = "jzs_zn_5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZn5;

    @Column(name = "jzs_zn_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZn2;

    @Column(name = "jzs_zn_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZn1;

    @Column(name = "jws_s_zg6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsSZg6;

    @Column(name = "jws_s_zg2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsSZg2;

    @Column(name = "idno", columnDefinition = "varchar(100)  DEFAULT ''")
    private String idno;

    @Column(name = "jws_s_zg3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsSZg3;

    @Column(name = "jws_s_zg4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsSZg4;

    @Column(name = "jws_s_zg5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsSZg5;

    @Column(name = "rh_blood", columnDefinition = "varchar(100)  DEFAULT ''")
    private String rhBlood;

    @Column(name = "workunit", columnDefinition = "varchar(100)  DEFAULT ''")
    private String workunit;

    @Column(name = "jws_diag_qt", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsDiagQt;

    @Column(name = "status", columnDefinition = "varchar(100)  DEFAULT ''")
    private String status;

    @Column(name = "ws_have", columnDefinition = "varchar(100)  DEFAULT ''")
    private String wsHave;

    @Column(name = "s_phone", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sPhone;

    @Column(name = "sf_record_time", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sfRecordTime;

    @Column(name = "s_nation", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sNation;

    @Column(name = "lc_flag", columnDefinition = "varchar(100)  DEFAULT ''")
    private String lcFlag;

    @Column(name = "ss_mc_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ssMc1;

    @Column(name = "ws_time_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String wsTime2;

    @Column(name = "ws_time_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String wsTime1;

    @Column(name = "perm_address", columnDefinition = "varchar(100)  DEFAULT ''")
    private String permAddress;

    @Column(name = "cj_qt", columnDefinition = "varchar(100)  DEFAULT ''")
    private String cjQt;

    @Column(name = "ss_mc_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ssMc2;

    @Column(name = "s_occupation", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sOccupation;

    @Column(name = "sex", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sex;

    @Column(name = "link_phone", columnDefinition = "varchar(100)  DEFAULT ''")
    private String linkPhone;

    @Column(name = "cj_4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String cj4;

    @Column(name = "cj_5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String cj5;

    @Column(name = "ycbs_have", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ycbsHave;

    @Column(name = "cj_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String cj2;

    @Column(name = "cj_3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String cj3;

    @Column(name = "cj_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String cj1;

    @Column(name = "jzs_ma_6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMa6;

    @Column(name = "jzs_ma_5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMa5;

    @Column(name = "jws_diag_jyb", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsDiagJyb;

    @Column(name = "jzs_ma_4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMa4;

    @Column(name = "jzs_ma_3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMa3;

    @Column(name = "jzs_ma_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMa2;

    @Column(name = "jzs_ma_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMa1;

    @Column(name = "sh_cfpfss", columnDefinition = "varchar(100)  DEFAULT ''")
    private String shCfpfss;

    @Column(name = "sf_record_man", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sfRecordMan;

    @Column(name = "jws_qz_mm4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzMm4;

    @Column(name = "jws_qz_mm3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzMm3;

    @Column(name = "s_bls1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sBls1;

    @Column(name = "jws_qz_mm2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzMm2;

    @Column(name = "jws_qz_mm1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzMm1;

    @Column(name = "s_bls0", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sBls0;

    @Column(name = "now_address", columnDefinition = "varchar(100)  DEFAULT ''")
    private String nowAddress;

    @Column(name = "jzs_xd_3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXd3;

    @Column(name = "jzs_xd_4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXd4;

    @Column(name = "jzs_xd_5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXd5;

    @Column(name = "jzs_xd_6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXd6;

    @Column(name = "qt_ybtype", columnDefinition = "varchar(100)  DEFAULT ''")
    private String qtYbtype;

    @Column(name = "jws_qz_mm6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzMm6;

    @Column(name = "jzs_xd_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXd1;

    @Column(name = "jws_qz_mm5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsQzMm5;

    @Column(name = "sx_have", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sxHave;

    @Column(name = "jzs_xd_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXd2;

    @Column(name = "nation_str", columnDefinition = "varchar(100)  DEFAULT ''")
    private String nationStr;

    @Column(name = "record_date", columnDefinition = "varchar(100)  DEFAULT ''")
    private String recordDate;

    @Column(name = "sh_rllx", columnDefinition = "varchar(100)  DEFAULT ''")
    private String shRllx;

    @Column(name = "link_man", columnDefinition = "varchar(100)  DEFAULT ''")
    private String linkMan;

    @Column(name = "sf_record_o_name", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sfRecordOName;

    @Column(name = "death_date", columnDefinition = "varchar(100)  DEFAULT ''")
    private String deathDate;

    @Column(name = "sf_update_man", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sfUpdateMan;

    @Column(name = "gms1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String gms1;

    @Column(name = "s_homedd", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sHomedd;

    @Column(name = "gms2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String gms2;

    @Column(name = "district_code", columnDefinition = "varchar(100)  DEFAULT ''")
    private String districtCode;

    @Column(name = "gms3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String gms3;

    @Column(name = "name", columnDefinition = "varchar(100)  DEFAULT ''")
    private String name;

    @Column(name = "ss_time_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ssTime2;

    @Column(name = "ss_time_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ssTime1;

    @Column(name = "birthday", columnDefinition = "varchar(100)  DEFAULT ''")
    private String birthday;

    @Column(name = "jws_exzl_diag", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jwsExzlDiag;

    @Column(name = "sh_ys", columnDefinition = "varchar(100)  DEFAULT ''")
    private String shYs;

    @Column(name = "jzs_xd_qt", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsXdQt;

    @Column(name = "jk_card_no", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jkCardNo;

    @Column(name = "sx_yy_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sxYy2;

    @Column(name = "sx_yy_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sxYy1;

    @Column(name = "jzs_ma_qt", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsMaQt;

    @Column(name = "rq_class", columnDefinition = "varchar(100)  DEFAULT ''")
    private String rqClass;

    @Column(name = "jzs_fa_4", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFa4;

    @Column(name = "district_name", columnDefinition = "varchar(100)  DEFAULT ''")
    private String districtName;

    @Column(name = "jzs_fa_5", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFa5;

    @Column(name = "jzs_fa_6", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFa6;

    @Column(name = "jzs_fa_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFa1;

    @Column(name = "ybtype", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ybtype;

    @Column(name = "qt_memo", columnDefinition = "varchar(100)  DEFAULT ''")
    private String qtMemo;

    @Column(name = "jzs_fa_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFa2;

    @Column(name = "blood_type", columnDefinition = "varchar(100)  DEFAULT ''")
    private String bloodType;

    @Column(name = "jzs_fa_3", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFa3;

    @Column(name = "ycf_flag", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ycfFlag;

    @Column(name = "w_card_no", columnDefinition = "varchar(100)  DEFAULT ''")
    private String wCardNo;

    @Column(name = "jzs_zn_qt", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsZnQt;

    @Column(name = "qt_gm", columnDefinition = "varchar(100)  DEFAULT ''")
    private String qtGm;

    @Column(name = "s_marry", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sMarry;

    @Column(name = "ycq_date", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ycqDate;

    @Column(name = "ss_have", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ssHave;

    @Column(name = "jzs_fa_qt", columnDefinition = "varchar(100)  DEFAULT ''")
    private String jzsFaQt;

    @Column(name = "ws_mc_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String wsMc1;

    @Column(name = "ws_mc_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String wsMc2;

    @Column(name = "sx_time_2", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sxTime2;

    @Column(name = "ycbs_str", columnDefinition = "varchar(100)  DEFAULT ''")
    private String ycbsStr;

    @Column(name = "sx_time_1", columnDefinition = "varchar(100)  DEFAULT ''")
    private String sxTime1;


}
