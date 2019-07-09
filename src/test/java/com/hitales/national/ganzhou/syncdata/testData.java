package com.hitales.national.ganzhou.syncdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hitales.national.ganzhou.syncdata.dao.PersonRepository;
import com.hitales.national.ganzhou.syncdata.dao.PersonTagRepository;
import com.hitales.national.ganzhou.syncdata.entity.Person;
import com.hitales.national.ganzhou.syncdata.entity.PersonTag;
import com.hitales.national.ganzhou.syncdata.service.SaveCreepDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:11:06
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class testData {

    @Autowired
    SaveCreepDataService saveCreepDataService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonTagRepository personTagRepository;

    @Test
    public void test1(){
        saveCreepDataService.saveClinic();
    }

    @Test
    public void test2(){
        saveCreepDataService.saveVillage();
    }


    @Test
    public void test3(){
        saveCreepDataService.saveCitizenList();
    }

    @Test
    public void test4(){
        saveCreepDataService.saveCitizenDetail();
    }

    // 生成居民信息
    @Test
    public void test5(){
        String ss = "{'personid':'360722000000003393','district_code':'360722100','district_name':'嘉定镇','jk_card_no':'','name':'刘冬梅','p_card_no':'36072210000000032','w_card_no':'36072210000000177','sex':'2','birthday':'1988-11-30','idno':'360722198811300062','workunit':'代屋村岭背组','s_phone':'15083945073','link_man':'张招礼','link_phone':'15083945073','s_homedd':'1','s_nation':'01','nation_str':'','blood_type':'5','rh_blood':'3','education':'7','s_occupation':'4','s_marry':'2','ycf_flag':'0','lc_flag':'','ycq_date':'2011-05-27','rq_class':'','ybtype':'3','yb2type':'','qt_ybtype':'','gms1':'1','gms2':'','gms3':'','qt_gm':'','s_bls0':'1','s_bls1':'','ycbs_have':'1','ycbs_str':'','cj_1':'1','cj_2':'','cj_3':'','cj_4':'','cj_5':'','cj_qt':'','sh_cfpfss':'2','sh_rllx':'1','sh_ys':'1','sh_cs':'1','sh_qxl':'','fmid':'360722000000000258','now_address':'嘉定镇代屋村','perm_address':'嘉定镇代屋村','death_date':'','status':'1','qt_memo':'','record_date':'2011-11-4 22:18:23','sf_record_time':'2011-11-04','sf_record_o_name':'信丰县嘉定镇卫生院','sf_record_man':'管理员','sf_update_time':'2011-11-07','sf_update_man':'','jws_s_zg1':'1','jws_qz_yy1':'','jws_qz_mm1':'','jws_s_zg2':'','jws_qz_yy2':'','jws_qz_mm2':'','jws_s_zg3':'','jws_qz_yy3':'','jws_qz_mm3':'','jws_s_zg4':'','jws_qz_yy4':'','jws_qz_mm4':'','jws_s_zg5':'','jws_qz_yy5':'','jws_qz_mm5':'','jws_s_zg6':'','jws_qz_yy6':'','jws_qz_mm6':'','ss_mc_1':'','ss_time_1':'','ss_mc_2':'','ss_time_2':'','ws_mc_1':'','ws_time_1':'','ws_mc_2':'','ws_time_2':'','sx_yy_1':'','sx_time_1':'','sx_yy_2':'','sx_time_2':'','jws_exzl_diag':'','jws_diag_jyb':'','jws_diag_qt':'','ss_have':'1','ws_have':'1','sx_have':'1','jzs_fa_1':'1','jzs_fa_2':'','jzs_fa_3':'','jzs_fa_4':'','jzs_fa_5':'','jzs_fa_6':'','jzs_fa_qt':'','jzs_ma_1':'1','jzs_ma_2':'','jzs_ma_3':'','jzs_ma_4':'','jzs_ma_5':'','jzs_ma_6':'','jzs_ma_qt':'','jzs_xd_1':'1','jzs_xd_2':'','jzs_xd_3':'','jzs_xd_4':'','jzs_xd_5':'','jzs_xd_6':'','jzs_xd_qt':'','jzs_zn_1':'1','jzs_zn_2':'','jzs_zn_3':'','jzs_zn_4':'','jzs_zn_5':'','jzs_zn_6':'','jzs_zn_qt':''}";
        JSONObject jsonObject = JSON.parseObject(ss);
        jsonObject.keySet().forEach(value->{
            System.out.println("@Column(name = \""+ value +"\", columnDefinition = \"varchar(100)  DEFAULT ''\")");
            System.out.println("private String "+underlineToCamel(value) +";\r\n");
        });
    }

    // 生成档案信息（疾病标签）
    @Test
    public void test6(){
        String ss = "{\"name\":\"钱文金\",\"sex_name\":\"男\",\"birthday\":\"1989-08-19\",\"p_card_no\":\"36072210000000036\",\"blood_xa\":\"\",\"blood_xb\":\"\",\"blood_xo\":\"\",\"blood_ab\":\"\",\"blood_bx\":\"1\",\"blood_rhy\":\"\",\"blood_rha\":\"\",\"blood_rhb\":\"1\",\"mxb_1\":\"1\",\"mxb_2\":\"\",\"mxb_3\":\"\",\"mxb_4\":\"\",\"mxb_5\":\"\",\"mxb_6\":\"\",\"mxb_7\":\"\",\"mxb_8\":\"\",\"mxb_9\":\"\",\"mxb_10\":\"\",\"mxb_11\":\"\",\"mxb_12\":\"\",\"mxb_13\":\"\",\"mxb_14\":\"\",\"mxb_qtvalue\":\"\",\"gms_2\":\"\",\"gms_3\":\"\",\"gms_4\":\"\",\"gms_5\":\"\",\"gms_qt\":\"\",\"home_address\":\"信丰县嘉定镇水北村天一组\",\"home_phone\":\"\",\"spec_linkman\":\"李峰\",\"spec_linkphone\":\"\",\"record_o_name\":\"信丰县嘉水北村第七卫生所\",\"record_o_phone\":\"\",\"fz_doctor\":\"陈金生\",\"fz_doc_phone\":\"15979802601\",\"qt_memo\":\"\",\"reCode\":\"1\"}";
        JSONObject jsonObject = JSON.parseObject(ss);
        jsonObject.keySet().forEach(value->{
            System.out.println("@Column(name = \""+ value +"\", columnDefinition = \"varchar(40)  DEFAULT ''\")");
            System.out.println("private String "+underlineToCamel(value) +";\r\n");
        });
    }
    /**
     * 下划线 转 驼峰
     * @param param
     * @return
     */
    public String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(param.charAt(i));
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Test
    public void test10(){
        String ss = "{\"name\":\"刘荣俊\",\"sex_name\":\"男\",\"birthday\":\"2001-04-25\",\"p_card_no\":\"36072211020001528\",\"blood_xa\":\"\",\"blood_xb\":\"\",\"blood_xo\":\"\",\"blood_ab\":\"\",\"blood_bx\":\"1\",\"blood_rhy\":\"\",\"blood_rha\":\"\",\"blood_rhb\":\"1\",\"mxb_1\":\"1\",\"mxb_2\":\"\",\"mxb_3\":\"\",\"mxb_4\":\"\",\"mxb_5\":\"\",\"mxb_6\":\"\",\"mxb_7\":\"\",\"mxb_8\":\"\",\"mxb_9\":\"\",\"mxb_10\":\"\",\"mxb_11\":\"\",\"mxb_12\":\"\",\"mxb_13\":\"\",\"mxb_14\":\"\",\"mxb_qtvalue\":\"\",\"gms_2\":\"\",\"gms_3\":\"\",\"gms_4\":\"\",\"gms_5\":\"\",\"gms_qt\":\"\",\"home_address\":\"小河村委会\",\"home_phone\":\"\",\"spec_linkman\":\"\",\"spec_linkphone\":\"\",\"record_o_name\":\"信丰县小河卫生院\",\"record_o_phone\":\"\",\"fz_doctor\":\"赖二姣\",\"fz_doc_phone\":\"\",\"qt_memo\":\"\",\"reCode\":\"1\"}";
        Person person = JSON.parseObject(ss, Person.class);
        personRepository.save(person);
    }

    @Test
    public void test11(){
        String ss = "{\"name\":\"许景嘉\",\"sex_name\":\"男\",\"birthday\":\"1960-10-16\",\"p_card_no\":\"36072220120000932\",\"blood_xa\":\"\",\"blood_xb\":\"\",\"blood_xo\":\"\",\"blood_ab\":\"\",\"blood_bx\":\"\",\"blood_rhy\":\"\",\"blood_rha\":\"\",\"blood_rhb\":\"1\",\"mxb_1\":\"1\",\"mxb_2\":\"\",\"mxb_3\":\"\",\"mxb_4\":\"\",\"mxb_5\":\"\",\"mxb_6\":\"\",\"mxb_7\":\"\",\"mxb_8\":\"\",\"mxb_9\":\"\",\"mxb_10\":\"\",\"mxb_11\":\"\",\"mxb_12\":\"\",\"mxb_13\":\"\",\"mxb_14\":\"\",\"mxb_qtvalue\":\"\",\"gms_2\":\"\",\"gms_3\":\"\",\"gms_4\":\"\",\"gms_5\":\"\",\"gms_qt\":\"\",\"home_address\":\"江西省信丰县崇仙乡崇仙村圩上\",\"home_phone\":\"\",\"spec_linkman\":\"\",\"spec_linkphone\":\"\",\"record_o_name\":\"信丰县崇仙卫生院\",\"record_o_phone\":\"\",\"fz_doctor\":\"张先明\",\"fz_doc_phone\":\"13807972619\",\"qt_memo\":\"\",\"reCode\":\"1\"}";
        PersonTag person = JSON.parseObject(ss, PersonTag.class);
        person.setPersonid("360722000000505725");
        personTagRepository.save(person);
    }
}
