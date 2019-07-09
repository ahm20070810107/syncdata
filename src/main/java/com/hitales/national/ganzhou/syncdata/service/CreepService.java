package com.hitales.national.ganzhou.syncdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:10:50
 */

@Service
public class CreepService {

    @Value("${hitales.third.cookie}")
    private String cookie;

    @Value("${hitales.third.ipAddress}")
    private String ipAddress;

    @Autowired
    RestTemplate restTemplate;

    String creepClinicData(){
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(clinicVillageHeaders());
        return restTemplate.exchange("http://"+ ipAddress +"/jkda/service/jkda_data_action.ashx?actName=GetDict&rand=0.5511042864785785&actTag=D610_O&orgType=", HttpMethod.GET,request, String.class).getBody();

    }

    String creepVillageData(){
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(clinicVillageHeaders());
        return restTemplate.exchange("http://"+ ipAddress +"/jkda/service/jkda_data_action.ashx?actName=GetDict&actTag=D602&treeType=1&rand=0.9632054330018911", HttpMethod.GET,request, String.class).getBody();

    }


    String creepCitizenList(Integer page, Integer rows){
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(citizenBody(page,rows),citizenHeaders());

        return restTemplate.exchange("http://"+ ipAddress +"/jkda/service/jkda_data_action.ashx", HttpMethod.POST,request, String.class).getBody();
    }

    String creepCitizenDetail(String pCode){
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(clinicVillageHeaders());
        return restTemplate.exchange("http://"+ ipAddress +"/jkda/service/jkda_data_action.ashx?actName=GetPerson&rand=0.5403722331637291&actTag=D101_F&p_code=" + pCode, HttpMethod.GET,request, String.class).getBody();
    }

    /**
     * 居民档案卡信息(标签)
     * @return String
     */
    String creepCitizenTag(String pCode){

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(clinicVillageHeaders());
        return restTemplate.exchange("http://"+ ipAddress +"/jkda/service/jkda_data_action.ashx?actName=GetVisit&rand=0.5396013645940736&actTag=D300_F&sf_tag=102&sf_no=102&sf_date=&p_code=" + pCode, HttpMethod.GET,request, String.class).getBody();
    }

    private HttpHeaders clinicVillageHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.add("Host","10.49.48.136");
        headers.add("Cache-Control","max-age=0");
        headers.add("Connection" ,"keep-alive");
        headers.add("Keep-Alive","timeout=20");
        headers.add("Accept-Encoding","gzip, deflate");
        headers.add("Upgrade-Insecure-Requests","1");
        headers.add("Accept-Language","zh-CN,zh;q=0.9");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        headers.add("X-Requested-With","XMLHttpRequest");
//        headers.add("Content-Length","428");
        headers.add("Cookie", cookie);
        return headers;
    }

    private HttpHeaders citizenHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json, text/javascript, */*; q=0.01");
        headers.add("Host","10.49.48.136");
        headers.add("Connection" ,"keep-alive");
        headers.add("Keep-Alive","timeout=20");
        headers.add("Origin","http://10.49.48.136");
        headers.add("Accept-Encoding","gzip, deflate");
        headers.add("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        headers.add("Accept-Language","zh-CN,zh;q=0.9");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        headers.add("Cookie", cookie);
        headers.add("X-Requested-With","XMLHttpRequest");
        headers.add("Referer","http://10.49.48.136/jkda/visit/jkda_person_visit.aspx?pkey=14738B783B29CC80E3C943CD15861AC9F2513443B1CADAAEF33AF52AB3C6E05FF5BDE007703F04063112FDA028C742949152E88A06206CD59B9FC68AECCFBDC06C08D82397FAAAEF3CD64605AF7A5489C6898C325A1888BF");
        return headers;
    }

    private MultiValueMap<String, Object> citizenBody(Integer page, Integer rows){
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("actName","GetPerson");
        bodyMap.add("actTag","D101_G");
        bodyMap.add("district_code","360722");
        bodyMap.add("actFlag","view");
        bodyMap.add("page", page);
        bodyMap.add("rows", rows);
        String[] keys = "org_right,p_card_no,name,idno,py_code,record_org,record_man,sex,s_birthday,e_birthday,s_homedd,personid,ybno,record_s_time,record_e_time,fz_doctor,sf_s_time,sf_e_time,s_status,tj_table,focus_groups,cjr_groups,special_groups,keyword,focus_fh,tj_s_date,tj_e_date".split(",");
        for(String key : keys){
            bodyMap.add(key,"");
        }
        String[] keys0 = "sf_days,h_zysf,d_order,d_order_desc,contain_r_org".split(",");
        for(String key : keys0){
            bodyMap.add(key,"0");
        }
        return bodyMap;
    }


}
