package com.hitales.national.ganzhou.syncdata.service;

import com.hitales.national.ganzhou.syncdata.common.PersonConverter;
import com.hitales.national.ganzhou.syncdata.dao.CitizenDao;
import com.hitales.national.ganzhou.syncdata.dao.CitizenServeTagDao;
import com.hitales.national.ganzhou.syncdata.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jingang
 * @Date: 2019/7/10 13:46
 * @Description:
 */
@Service
@Slf4j
public class CitizenInfoService {

    @Autowired
    private CitizenDao citizenDao;

    @Autowired
    private CitizenServeTagDao citizenServeTagDao;

    public void updateCitizen(Long citizenId, Person person, PersonTag personTag){
        Long countyId = 100L;
        PersonConverter converter=PersonConverter.convert(person,personTag,citizenServeTagDao,countyId);
        Citizen citizen=converter.getCitizen();
        CitizenEhr ehr=converter.getCitizenEhr();
        List<CitizenServeTagMapping> citizenServeTags = converter.getCitizenServeTag();
        List<CitizenEhrFamilyHistory> familyHistories=converter.getFamilyHistories();
        List<CitizenEhrGeneticHistory> geneticHistories=converter.getGeneticHistories();
        List<CitizenEhrMedicalHistory> medicalHistories=converter.getMedicalHistories();

        //人群分类，服务标签
    }
}
