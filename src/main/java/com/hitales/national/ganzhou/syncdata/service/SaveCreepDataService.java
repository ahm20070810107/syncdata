package com.hitales.national.ganzhou.syncdata.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hitales.national.ganzhou.syncdata.dao.*;
import com.hitales.national.ganzhou.syncdata.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:37
 */

@Service
@Slf4j
public class SaveCreepDataService {

    @Autowired
    private CreepService creepService;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonTagRepository personTagRepository;

    @Autowired
    private PersonListRepository personListRepository;

    private final Integer CITIZEN_BATCH_NO = 100;

    private final Integer CITIZEN_MAX_THREAD = 6;

    private final Pageable pageable = PageRequest.of(0, CITIZEN_BATCH_NO);

    public String saveClinic(){
        String clinicData = creepService.creepClinicData();
        if(Strings.isBlank(clinicData)){
            return "源系统爬取医疗机构数据为空，存储失败";
        }
        List<Clinic> clinicList = JSON.parseArray(clinicData,Clinic.class);

        clinicList.stream().filter(Objects::nonNull).forEach(clinic -> {
            if(Strings.isBlank(clinic.getOrgName())){
                log.error("医疗机构数据【{}】编码为空已被丢弃",clinic.toString());
            }
            Optional<Clinic> clinicOptional = clinicRepository.findByOrgCode(clinic.getOrgCode());
            clinicOptional.ifPresent(dbClinic->clinic.setId(dbClinic.getId()));
            clinicRepository.save(clinic);
        });

        return "数据抓起已经成功，请到ganzhou_clinic表中查看";
    }

    public String saveVillage(){

        String villageData = creepService.creepVillageData();
        if(Strings.isBlank(villageData)){
            return "源系统爬取行政村数据为空，存储失败";
        }
        List<Village> clinicList = JSON.parseArray(villageData,Village.class);
        clinicList.stream().filter(Objects::nonNull).forEach(village -> {
            Optional<Village> villageOptional = villageRepository.findById(village.getId());
            villageOptional.ifPresent(dbClinic->village.setId(dbClinic.getId()));
            villageRepository.save(village);
        });

        return "数据抓起已经成功，请到ganzhou_village表中查看";
    }


    public String saveCitizenList(){
        Integer gap = 1000;
        String citizenList = creepService.creepCitizenList(1,1);
        JSONObject jsonObject = JSON.parseObject(citizenList);
        Integer total = jsonObject.getInteger("total");
        if(total.compareTo(0) <= 0){
            return "未查询到居民列表数据，本次爬取结束！";
        }
        double creepTimeDouble = total*1.0/gap;
        int creepTimes =(int)Math.ceil(creepTimeDouble);

        for (int i = 1; i <= creepTimes; i++) {
            log.info("开始爬取第{}批数据，每批数据量{}条,共{}批！",i,gap,creepTimes);
            String citizen = creepService.creepCitizenList(i,gap);
            JSONObject json = JSON.parseObject(citizen);
            JSONArray citizenArray = json.getJSONArray("rows");
            List<PersonList> personLists = JSON.parseArray(citizenArray.toJSONString(),PersonList.class);
            personLists = personLists.stream().filter(value->!personListRepository.findByPersonid(value.getPersonid()).isPresent()).collect(Collectors.toList());
            personListRepository.saveAll(personLists);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "列表爬取完成！";
    }

    public String saveCitizenDetail(){
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(CITIZEN_BATCH_NO);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CITIZEN_MAX_THREAD,CITIZEN_MAX_THREAD,60L, TimeUnit.SECONDS,queue);
        while (saveCitizenDetail(pageable,threadPoolExecutor)){

        }
        return "列表爬取完成！";
    }

    private boolean saveCitizenDetail(Pageable pageable,ThreadPoolExecutor threadPoolExecutor){
         List<PersonList> personLists = personListRepository.findBySyncState(0,pageable).getContent();
         if(Objects.isNull(personLists) || personLists.isEmpty()){
             return false;
         }
         log.info("开始爬取居民详情数据{}条",personLists.size());
         CountDownLatch countDownLatch = new CountDownLatch(personLists.size());
         personLists.forEach(value->

             threadPoolExecutor.execute(()->{

                 String personId = value.getPersonid();
                 if(!Strings.isBlank(personId)){
                     //获取居民详细信息
                     String personDetail = creepService.creepCitizenDetail(personId);
                     if(Strings.isBlank(personDetail)){
                         log.warn("personId为{}的居民无法获取居民详细信息！",personId);
                     }else{
                         try {
                             Person person = JSON.parseObject(personDetail, Person.class);
                             personRepository.save(person);
                         } catch (Exception e) {
                             System.out.println(personDetail);
                             e.printStackTrace();
                         }
                     }
                     // 获取档案信息
                     String personTagStr = creepService.creepCitizenTag(personId);

                     if(Strings.isBlank(personTagStr)){
                         log.warn("personId为{}的居民无法获取居民档案信息信息！",personId);
                     }else{
                         try {
                             PersonTag personTag = JSON.parseObject(personTagStr, PersonTag.class);
                             personTag.setPersonid(personId);
                             personTagRepository.save(personTag);
                         } catch (Exception e) {
                             System.out.println(personTagStr);
                             e.printStackTrace();
                         }
                     }
                 }else
                 {
                     log.warn("有居民信息的persionid为空,{}",JSONObject.toJSONString(value));
                 }
                 countDownLatch.countDown();
             })
        );
        personLists.forEach(value->value.setSyncState(1));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        personListRepository.saveAll(personLists);
         return true;
     }


}
