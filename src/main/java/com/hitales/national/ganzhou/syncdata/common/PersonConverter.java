package com.hitales.national.ganzhou.syncdata.common;

import com.google.common.collect.Lists;
import com.hitales.national.ganzhou.syncdata.entity.*;
import com.hitales.national.ganzhou.syncdata.enums.*;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;
import java.util.List;

/**
 * @Author: jingang
 * @Date: 2019/7/10 14:08
 * @Description:
 */
public class PersonConverter {

    private PersonConverter(){}

    private Person person;
    private PersonTag personTag;

    private Citizen citizen;

    private CitizenEhr citizenEhr;

    private List<CitizenEhrFamilyHistory> familyHistories;

    private List<CitizenEhrGeneticHistory> geneticHistories;

    private List<CitizenEhrMedicalHistory> medicalHistories;


    public static PersonConverter convert(Person person,PersonTag personTag){
        PersonConverter converter=new PersonConverter();
        converter.citizen=converter.convertCitizen(person,personTag);
        converter.citizenEhr=converter.convertEhr(person,personTag);
        converter.familyHistories=converter.convertFamilyHistories(person,personTag);
        converter.geneticHistories=converter.convertGeneticHistories(person,personTag);
        converter.medicalHistories=converter.convertMedicalHistories(person,personTag);



        return converter;
    }

    private Citizen convertCitizen(Person person,PersonTag personTag){
        Citizen citizen=new Citizen();
        citizen.setBirthday(null);person.getBirthday();
        citizen.setBloodTypeAbo(BloodTypeABO.TYPE_O);person.getBloodType();
        citizen.setDisabilityState(null);person.getCj1();//cj_1到cj_5
        citizen.setEducationDegree(EducationDegree.JUNIOR_COLLEGE_EDUCATION);person.getEducation();
        citizen.setContactName(null);person.getLinkMan();
        citizen.setContactPhone(null);person.getLinkPhone();
        citizen.setBloodTypeRh(BloodTypeRH.TYPE_UNKNOWN);person.getRhBlood();
        citizen.setResidentType(ResidentType.NON_REGISTERED);person.getSHomedd();//多一个流动人口
        citizen.setMaritalState(MaritalState.UNMARRIED);person.getSMarry();
        citizen.setOccupationType(OccupationType.BUSINESS);person.getSOccupation();
        citizen.setGender(CitizenGender.FEMALE);person.getSex();
        citizen.setWorkUnit(person.getWorkunit());
        citizen.setMedicalPayment(MedicalPayment.COMMERCIAL_MEDICAL_INSURANCE);person.getYbtype();//有两个

        return citizen;
    }

    private CitizenEhr convertEhr(Person person,PersonTag personTag){
        CitizenEhr ehr=new CitizenEhr();
        ehr.setDrugAllergyHistory(null);person.getGms1();//gms1到gms3
        ehr.setExposureHistory(null);person.getSBls0();//s_bls0到s_bls1
        ehr.setHabitatDrinkingWater(HabitatDrinkingWater.FILTERED_WATER);person.getShYs();
        ehr.setHabitatFuelType(HabitatFuelType.COAL);person.getShRllx();
        ehr.setHabitatKitchenExhaustFacility(HabitatKitchenExhaustFacility.CHIMNEY);person.getShCfpfss();
        ehr.setHabitatLivestockBar(HabitatLivestockBar.INDOOR);person.getShQxl();
        ehr.setHabitatToilet(HabitatToilet.SANITARY_TOILET);person.getShCs();

        return ehr;
    }

    private List<CitizenEhrFamilyHistory> convertFamilyHistories(Person person,PersonTag personTag){
        List<CitizenEhrFamilyHistory> familyHistories= Lists.newArrayList();

        convertFamilyHistory(MedicalFamilyRelationship.FATHER,"jzsFa",person, familyHistories);
        convertFamilyHistory(MedicalFamilyRelationship.MOTHER,"jzsMa",person, familyHistories);
        convertFamilyHistory(MedicalFamilyRelationship.SIBLING,"jzsXd",person, familyHistories);
        convertFamilyHistory(MedicalFamilyRelationship.CHILDREN,"jzsZn",person, familyHistories);

        return familyHistories;
    }



    private List<CitizenEhrGeneticHistory> convertGeneticHistories(Person person,PersonTag personTag){
        if(!"2".equals(person.getYcbsHave())){
            return null;
        }

        List<CitizenEhrGeneticHistory> geneticHistories= Lists.newArrayList();

        CitizenEhrGeneticHistory geneticHistory=new CitizenEhrGeneticHistory();
        geneticHistory.setName(person.getYcbsStr());

        geneticHistories.add(geneticHistory);

        return geneticHistories;
    }

    private List<CitizenEhrMedicalHistory> convertMedicalHistories(Person person,PersonTag personTag){
        if(!"2".equals(person.getYcbsHave())){
            return null;
        }

//        `jws_qz_mm1` varchar(100) DEFAULT '' COMMENT '既往史-疾病-确诊时间月1',	jws_qz_mm1到jws_qz_mm6
//        `jws_qz_yy1` varchar(100) DEFAULT '' COMMENT '既往史-疾病-确诊时间年1',	jws_qz_yy1到jws_qz_yy6
//        `jws_s_zg1` varchar(100) DEFAULT '' COMMENT '既往史-疾病1： 1 无 2 高血压 3 糖尿病 4 冠心病 5 慢性阻塞性肺疾病 6 恶性肿瘤 7 脑卒中 8 严重精神障碍 9 结核病 10 肝炎 11 其他法定传染病 12 职业病 13 其他',	jws_s_zg1到jws_s_zg6


//        `ss_have` varchar(100) DEFAULT '' COMMENT '既往史-手术-有无：	 1 无 2 有',
//                `ss_mc_1` varchar(100) DEFAULT '' COMMENT '既往史-手术1名称',	ss_mc_1到ss_mc_2
//        `ss_time_1` varchar(100) DEFAULT '' COMMENT '既往史-手术1时间',	ss_time_1到ss_time_2
//        `status` varchar(100) DEFAULT '' COMMENT '档案状态',
//                `sx_have` varchar(100) DEFAULT '' COMMENT '既往史-输血-有无：	 1 无 2 有',
//                `sx_time_1` varchar(100) DEFAULT '' COMMENT '既往史-输血1时间',	sx_time_1到sx_time_2
//        `sx_yy_1` varchar(100) DEFAULT '' COMMENT '既往史-输血1原因',	sx_yy_1到sx_yy_2
//        `workunit` varchar(100) DEFAULT '' COMMENT '工作单位',
//                `ws_have` varchar(100) DEFAULT '' COMMENT '既往史-外伤-有无：	 1 无 2 有',
//                `ws_mc_1` varchar(100) DEFAULT '' COMMENT '既往史-外伤1名称',	ws_mc_1到ws_mc_2
//        `ws_time_1` varchar(100) DEFAULT '' COMMENT '既往史-外伤1时间',	ws_time_1到ws_time_2


        List<CitizenEhrMedicalHistory> medicalHistories= Lists.newArrayList();

        CitizenEhrGeneticHistory geneticHistory=new CitizenEhrGeneticHistory();
        geneticHistory.setName(person.getYcbsStr());

        geneticHistories.add(geneticHistory);

        return medicalHistories;
    }


    private void convertFamilyHistory(MedicalFamilyRelationship relationship,String propertyPrefix,Person person, List<CitizenEhrFamilyHistory> familyHistories) {
        CitizenEhrFamilyHistory familyHistory=new CitizenEhrFamilyHistory();
        familyHistory.setType(relationship);
        BeanWrapper wrapper = new BeanWrapperImpl(person);
        for (int i=1;i<=6;i++){
            wrapper.getPropertyValue(propertyPrefix+i);
            familyHistory.setDisease(Lists.newArrayList(FamilyMedicalDisease.CORONARY_DISEASE));person.getJzsFa1();//jzs_fa_1到jzs_fa_6;我们这边没有无
        }
        familyHistories.add(familyHistory);
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public CitizenEhr getCitizenEhr() {
        return citizenEhr;
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