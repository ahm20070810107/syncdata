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
@Table(name = "ganzhou_village")
@org.hibernate.annotations.Table(appliesTo = "ganzhou_village", comment = "赣州自然村数据")
public class Village {

    @Id
    @Column(name = "id", columnDefinition = "varchar(20) comment '系统ID'")
    private String id;

    @Column(name = "p_id", columnDefinition = "varchar(20)  DEFAULT '' COMMENT '父级id'")
    private String pId;

    @Column(name = "name", columnDefinition = "varchar(50)  default '' comment '名称'")
    private String name;

    @Column(name = "short_name", columnDefinition = "varchar(50)  default '' comment '简称'")
    private String shortName;
}
