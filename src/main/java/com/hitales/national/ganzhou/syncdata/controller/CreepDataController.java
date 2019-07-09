package com.hitales.national.ganzhou.syncdata.controller;

import com.hitales.national.ganzhou.syncdata.service.SaveCreepDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-02
 * @time:14:20
 */

@RestController
@Slf4j
@RequestMapping("creep")
public class CreepDataController {

    @Autowired
    SaveCreepDataService saveCreepDataService;

    @GetMapping("clinic")
    public String creepClinic(){
       return saveCreepDataService.saveClinic();
    }

    @GetMapping("village")
    public String creepVillage(){
        return saveCreepDataService.saveVillage();
    }

    @GetMapping("citizenList")
    public String creepCitizenList(){
        return saveCreepDataService.saveCitizenList();
    }

    @GetMapping("citizenDetail")
    public String creepCitizenDetail(){
        return saveCreepDataService.saveCitizenDetail();
    }

    @GetMapping("hello")
    public String test(){
        return "hello";
    }

}
