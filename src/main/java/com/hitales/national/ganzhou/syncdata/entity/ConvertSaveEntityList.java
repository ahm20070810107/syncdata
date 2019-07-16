package com.hitales.national.ganzhou.syncdata.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-16
 * @time:14:00
 */

@Data
public class ConvertSaveEntityList {

    List<CitizenServeTagMapping> citizenServeTag = new ArrayList<>();

    List<CitizenEhrFamilyHistory> familyHistories = new ArrayList<>();

    List<CitizenEhrGeneticHistory> geneticHistories = new ArrayList<>();

    List<CitizenEhrMedicalHistory> medicalHistories = new ArrayList<>();


}
