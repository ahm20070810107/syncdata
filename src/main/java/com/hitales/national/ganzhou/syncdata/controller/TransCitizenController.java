package com.hitales.national.ganzhou.syncdata.controller;

import com.hitales.national.ganzhou.syncdata.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-15
 * @time:15:49
 */

@RestController
@Slf4j
@RequestMapping("convert")
public class TransCitizenController {

    @Autowired
    CitizenService citizenService;

    @GetMapping("citizen")
    public String convertCitizen(@RequestParam("toDbFlag") Boolean toDbFlag){
         return citizenService.importToDb(toDbFlag);
     }
}
