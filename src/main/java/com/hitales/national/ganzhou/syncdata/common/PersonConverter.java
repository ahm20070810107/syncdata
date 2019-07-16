package com.hitales.national.ganzhou.syncdata.common;

import com.google.common.collect.Lists;
import com.hitales.commons.enums.EnumCollector;
import com.hitales.commons.enums.YesNo;
import com.hitales.national.ganzhou.syncdata.dao.CitizenServeTagDao;
import com.hitales.national.ganzhou.syncdata.entity.*;
import com.hitales.national.ganzhou.syncdata.enums.*;
import com.hitales.national.ganzhou.syncdata.service.CommonToolsService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: jingang
 * @Date: 2019/7/10 14:08
 * @Description:
 */
public class PersonConverter {

    private PersonConverter(){}

//    private Person person;
//    private PersonTag personTag;

    private Citizen citizen;

    private CitizenEhr citizenEhr;

    private List<CitizenServeTagMapping> citizenServeTagMappings;

    private List<CitizenEhrFamilyHistory> familyHistories;

    private List<CitizenEhrGeneticHistory> geneticHistories;

    private List<CitizenEhrMedicalHistory> medicalHistories;


    public static PersonConverter convert(Person person, PersonTag personTag, CitizenServeTagDao citizenServeTagDao,Long countyId){
        PersonConverter converter=new PersonConverter();
        converter.citizen=converter.convertCitizen(person,personTag);
        converter.citizenEhr=converter.convertEhr(person,personTag);
        converter.citizenServeTagMappings = converter.convertCitizenTag(personTag,citizenServeTagDao,countyId);
        converter.familyHistories=converter.convertFamilyHistories(person,personTag);
        converter.geneticHistories=converter.convertGeneticHistories(person,personTag);
        converter.medicalHistories=converter.convertMedicalHistories(person,personTag);



        return converter;
    }

    private List<CitizenServeTagMapping> convertCitizenTag(PersonTag personTag, CitizenServeTagDao citizenServeTagDao, Long countyId){
        List<CitizenServeTagMapping> citizenServeTagMappings =  new ArrayList<>();
        List<String> tagList = Lists.newArrayList();
        if("1".equals(personTag.getMxb4())){
            tagList.add("冠心病");
        }
        if("1".equals(personTag.getMxb5())){
            tagList.add("慢性阻塞性肺疾病");
        }
        if("1".equals(personTag.getMxb6())){
            tagList.add("恶性肿瘤");
        }
        if("1".equals(personTag.getMxb7())){
            tagList.add("脑卒中");
        }
        if("1".equals(personTag.getMxb10())){
            tagList.add("肝炎");
        }
        if("1".equals(personTag.getMxb12())){
            tagList.add("职业病");
        }
        if("1".equals(personTag.getMxb13()) && Strings.isNotBlank(personTag.getMxbQtvalue())){
            tagList.add(personTag.getMxbQtvalue());
        }
        if("1".equals(personTag.getMxb14())){
            tagList.add("哮喘");
        }
        for(String tag : tagList){
            CitizenServeTagMapping citizenServeTagMapping = new CitizenServeTagMapping();
            citizenServeTagMapping.setPackageId(0L);
            Optional<CitizenServeTag> citizenServeTag = citizenServeTagDao.findTopByCountyIdAndTagName(countyId,tag);
            citizenServeTag.ifPresent(citizenTag-> citizenServeTagMapping.setTagId(citizenTag.getId()));
            if(!citizenServeTag.isPresent()){
               citizenServeTagMapping.setTagId(saveCitizenTag(tag,citizenServeTagDao,countyId));
            }
            citizenServeTagMapping.setCreateTime(new Date());
            citizenServeTagMapping.setUpdateTime(citizenServeTagMapping.getCreateTime());
            citizenServeTagMappings.add(citizenServeTagMapping);
        }
        return citizenServeTagMappings;
    }

    private Long saveCitizenTag(String tagName, CitizenServeTagDao citizenServeTagDao, Long countId){
        CitizenServeTag citizenServeTag = new CitizenServeTag();
        citizenServeTag.setCountyId(countId);
        citizenServeTag.setTagName(tagName);
        citizenServeTag.setCreateTime(new Date());
        citizenServeTag.setUpdateTime(citizenServeTag.getCreateTime());
        return citizenServeTagDao.save(citizenServeTag).getId();
    }

    private Citizen citizenServiceTag(Citizen citizen, PersonTag personTag){
        if("1".equals(personTag.getMxb1())){
            return citizen;
        }
        if("1".equals(personTag.getMxb2())){
            citizen.setCrowdHypertension(YesNo.YES);
        }
        if("1".equals(personTag.getMxb3())){
            citizen.setCrowdDiabetes2(YesNo.YES);
        }
        if("1".equals(personTag.getMxb8())){
            citizen.setCrowdMentalDisorder(YesNo.YES);
        }
        // 肺结核
        if("1".equals(personTag.getMxb9())){
            citizen.setCrowdPulmonaryTuberculosis(YesNo.YES);
        }
        // 是否其他传染病
        if("1".equals(personTag.getMxb11())){
            citizen.setCrowdInfection(YesNo.YES);
        }
        return citizen;
    }

    /**
     * 居民基础信息转换
     * @param person
     * @param personTag
     * @return
     */
    private Citizen convertCitizen(Person person,PersonTag personTag){
        Citizen citizen=new Citizen();
        citizen.setCrowds(Lists.newArrayList());

        IdCard idCard=IdCard.tryParse(person.getIdno());

        citizen.setBirthday( idCard.getBirthday().toDate() );;
        citizen.setBloodTypeAbo( parseEnum(BloodTypeABO.class,person.getBloodType()) );
        citizen.setDisabilityState( parseEnums(DisabilityState.class,person,"cj",Lists.newArrayList("1","2","3","4","5")) );//key没对齐,差1
        citizen.setEducationDegree( parseEnum(EducationDegree.class,person.getEducation()) );
        citizen.setContactName( person.getLinkMan() );
        citizen.setContactPhone( person.getLinkPhone() );
        citizen.setBloodTypeRh( parseEnum(BloodTypeRH.class,person.getRhBlood()) );
        citizen.setResidentType( parseEnum(ResidentType.class,person.getSHomedd()) );//多一个流动人口
        citizen.setMaritalState( parseEnum(MaritalState.class,person.getSMarry()) );
        citizen.setOccupationType( parseEnum(OccupationType.class,person.getSOccupation()) );
        citizen.setGender(ConvertUtil.getGender(idCard.getGender()));
        citizen.setWorkUnit(person.getWorkunit());
        citizen.setMedicalPayment( parseEnum(MedicalPayment.class,person.getYbtype()) );//有两个

        citizen.setIdName(person.getName());
        citizen.setIdNo(person.getIdno());
        citizen.setLocation(Long.parseLong(person.getDistrictCode()));
        citizen.setAddress(person.getNowAddress());
        citizen.setIdType(IdType.ID);
        citizen.setNation(CommonToolsService.getNation(person.getSNation()));
        if(Phone.match(person.getSPhone())) {
            citizen.setPhone(person.getSPhone());
        }

        //死亡设置为永久失访
        if("2".equals(person.getStatus())){
            citizen.setFollowState(FollowState.MISS_FOREVER);
            citizen.setLostFollowReason(LostFollowReason.DEAD);
        }

        // 残疾状况
        if(Strings.isNotBlank(person.getCj1()) && !"1".equals(person.getCj1())){
            citizen.setCrowdDisabled(YesNo.YES);
        }
        // 孕产妇
        if( "1".equals(person.getYcfFlag())){
            citizen.setCrowdMaternal(YesNo.YES);
        }
        // 是否贫困
        if("2".equals(person.getRqClass()) || "3".equals(person.getRqClass())){
            citizen.setCrowdPoor(YesNo.YES);
        }

        return citizenServiceTag(citizen,personTag);
    }

    /**
     *  枚举值转枚举对象
     * @param clazz 枚举类
     * @param value 值
     * @param <T>
     * @return
     */
    private <T> T parseEnum(Class clazz,String value){
        if(Strings.isBlank(value)){
            return null;
        }

        try {
            return EnumCollector.forClass(clazz).keyOf(Integer.parseInt(value));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 枚举值列表转枚举对象列表
     * @param clazz 枚举类
     * @param person 居民
     * @param propertyPrefix 属性前缀
     * @param propertyPostfixs 属性后缀列表
     * @param <T>
     * @return
     */
    private <T> List<T> parseEnums(Class clazz,Person person,String propertyPrefix,List<String> propertyPostfixs){
        List<T> list=Lists.newArrayList();
        try {
            BeanWrapper wrapper = new BeanWrapperImpl(person);
            for (String propertyPostfix : propertyPostfixs) {
                Object value=wrapper.getPropertyValue(propertyPrefix+propertyPostfix);
                String strVal=value==null?"":value.toString();
                T item=parseEnum(clazz,strVal);
                if(Objects.nonNull(item)){
                    list.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * 居民健康档案转换
     * @param person
     * @param personTag
     * @return
     */
    private CitizenEhr convertEhr(Person person,PersonTag personTag){
        CitizenEhr ehr=new CitizenEhr();
        ehr.setDrugAllergyHistory( parseEnums(DrugAllergyType.class,person,"gms",Lists.newArrayList("1","2","3")) );//key没对齐,差1
        ehr.setExposureHistory( parseEnums(ExposureType.class,person,"sBls",Lists.newArrayList("0","1")) );//key没对齐,差1
        ehr.setHabitatDrinkingWater( parseEnum(HabitatDrinkingWater.class,person.getShYs()) );
        ehr.setHabitatFuelType( parseEnum(HabitatFuelType.class,person.getShRllx()) );
        ehr.setHabitatKitchenExhaustFacility( parseEnum(HabitatKitchenExhaustFacility.class,person.getShCfpfss()) );
        ehr.setHabitatLivestockBar( parseEnum(HabitatLivestockBar.class,person.getShQxl()) );
        ehr.setHabitatToilet( parseEnum(HabitatToilet.class,person.getShCs()) );

        return ehr;
    }

    /**
     * 居民家族史转换
     * @param person
     * @param personTag
     * @return
     */
    private List<CitizenEhrFamilyHistory> convertFamilyHistories(Person person,PersonTag personTag){
        List<CitizenEhrFamilyHistory> familyHistories= Lists.newArrayList();

        convertFamilyHistory(MedicalFamilyRelationship.FATHER,"jzsFa",person, familyHistories);
        convertFamilyHistory(MedicalFamilyRelationship.MOTHER,"jzsMa",person, familyHistories);
        convertFamilyHistory(MedicalFamilyRelationship.SIBLING,"jzsXd",person, familyHistories);
        convertFamilyHistory(MedicalFamilyRelationship.CHILDREN,"jzsZn",person, familyHistories);

        return familyHistories;
    }

    /**
     * 居民遗传史转换
     * @param person
     * @param personTag
     * @return
     */
    private List<CitizenEhrGeneticHistory> convertGeneticHistories(Person person,PersonTag personTag){
        List<CitizenEhrGeneticHistory> geneticHistories= Lists.newArrayList();
        if(!"2".equals(person.getYcbsHave())){
            return geneticHistories;
        }

        CitizenEhrGeneticHistory geneticHistory=new CitizenEhrGeneticHistory();
        geneticHistory.setName(person.getYcbsStr());

        geneticHistories.add(geneticHistory);

        return geneticHistories;
    }

    /**
     * 居民既往史转换
     * @param person
     * @param personTag
     * @return
     */
    private List<CitizenEhrMedicalHistory> convertMedicalHistories(Person person,PersonTag personTag){

        List<CitizenEhrMedicalHistory> medicalHistories= Lists.newArrayList();

        convertMedicalDiseaseHistory(person,medicalHistories);
        convertMedicalHistory("ssHave","ssMc","ssTime",MedicalHistoryType.DISEASE,person,medicalHistories);
        convertMedicalHistory("sxHave","sxYy","sxTime",MedicalHistoryType.BLOOD_TRANS,person,medicalHistories);
        convertMedicalHistory("wsHave","wsMc","wsTime",MedicalHistoryType.WOUND,person,medicalHistories);



        return medicalHistories;
    }

    /**
     * 居民既往疾病史转换
     * @param person
     * @param medicalHistories
     */
    private void convertMedicalDiseaseHistory(Person person, List<CitizenEhrMedicalHistory> medicalHistories){
        BeanWrapper wrapper = new BeanWrapperImpl(person);
        for (int i = 1; i <=6 ; i++) {
            Object year=wrapper.getPropertyValue("jwsQzYy"+i);
            Object month=wrapper.getPropertyValue("jwsQzMm"+i);
            Object disease=wrapper.getPropertyValue("jwsSZg"+i);
            if(year!=null&&month!=null&&disease!=null){
                String yearStr=year.toString();
                String monthStr=month.toString();
                String diseaseStr=disease.toString();
                if(!StringUtils.isEmpty(yearStr)&&!StringUtils.isEmpty(monthStr)&&!StringUtils.isEmpty(diseaseStr)){
                    Date confirmDate=null;
                    try {
                        confirmDate= new SimpleDateFormat("yyyy-MM").parse(yearStr+"-"+monthStr);
                    } catch (ParseException e) {
                       continue;
                    }

                    CitizenEhrMedicalHistory history=new CitizenEhrMedicalHistory();
                    history.setDisease( parseEnum(CitizenMedicalDisease.class,diseaseStr) ); //我们这边没有无和其他
                    history.setConfirmDate(confirmDate);
                    history.setType(MedicalHistoryType.DISEASE);

                    medicalHistories.add(history);
                }
            }

        }

    }

    /**
     * 居民既往史(手术、输血、外伤)转换
     * @param havePropertyName
     * @param namePropertyName
     * @param timePropertyName
     * @param type
     * @param person
     * @param medicalHistories
     */
    private void convertMedicalHistory(String havePropertyName,String namePropertyName,String timePropertyName,MedicalHistoryType type,Person person, List<CitizenEhrMedicalHistory> medicalHistories){

        BeanWrapper wrapper = new BeanWrapperImpl(person);
        Object have=wrapper.getPropertyValue(havePropertyName);
        if(!"2".equals(have)){
            return;
        }

        for (int i = 1; i <=2 ; i++) {
            Object name=wrapper.getPropertyValue(namePropertyName+i);
            Object date=wrapper.getPropertyValue(timePropertyName+i);

            if(name!=null&&date!=null){
                String nameStr=name.toString();
                String dateStr=date.toString();
                if(!StringUtils.isEmpty(nameStr)&&!StringUtils.isEmpty(dateStr)){

                    Date confirmDate=null;
                    try {
                        confirmDate= new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    } catch (ParseException e) {
                        continue;
                    }

                    CitizenEhrMedicalHistory history=new CitizenEhrMedicalHistory();
                    history.setName(nameStr);
                    history.setConfirmDate(confirmDate);
                    history.setType(type);

                    medicalHistories.add(history);
                }
            }

        }

    }


    /**
     * 居民家族史转换（按家庭关系）
     * @param relationship
     * @param propertyPrefix
     * @param person
     * @param familyHistories
     */
    private void convertFamilyHistory(MedicalFamilyRelationship relationship,String propertyPrefix,Person person, List<CitizenEhrFamilyHistory> familyHistories) {
        CitizenEhrFamilyHistory familyHistory=new CitizenEhrFamilyHistory();
        familyHistory.setType(relationship);
        familyHistory.setDisease( parseEnums(FamilyMedicalDisease.class,person,propertyPrefix,Lists.newArrayList("1","2","3","4","5","6")) );//我们这边没有无和其他

        familyHistories.add(familyHistory);
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public CitizenEhr getCitizenEhr() {
        return citizenEhr;
    }

    public List<CitizenServeTagMapping> getCitizenServeTag(){
        return this.citizenServeTagMappings;
    }

    public List<CitizenEhrFamilyHistory> getFamilyHistories() {
        return familyHistories;
    }

    public List<CitizenEhrGeneticHistory> getGeneticHistories() {
        return geneticHistories;
    }

    public List<CitizenEhrMedicalHistory> getMedicalHistories() {
        return medicalHistories;
    }
}
