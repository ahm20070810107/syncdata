package com.hitales.national.ganzhou.syncdata.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hitales.national.ganzhou.syncdata.common.ConvertUtil;
import com.hitales.national.ganzhou.syncdata.common.IdCard;
import com.hitales.national.ganzhou.syncdata.common.Phone;
import com.hitales.national.ganzhou.syncdata.dao.CitizenDao;
import com.hitales.national.ganzhou.syncdata.dao.GB2260Dao;
import com.hitales.national.ganzhou.syncdata.dao.PersonRepository;
import com.hitales.national.ganzhou.syncdata.entity.Citizen;
import com.hitales.national.ganzhou.syncdata.entity.GB2260;
import com.hitales.national.ganzhou.syncdata.entity.Person;
import com.hitales.national.ganzhou.syncdata.enums.IdType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:14:30
 */

@Service
@Slf4j
public class CitizenService {

    @Autowired
    private CommonToolsService commonToolsService;

    @Autowired
    private CitizenDao citizenDao;

    @Autowired
    private GB2260Dao gb2260Dao;

    @Autowired
    private PersonRepository personRepository;


    private final Sort sort = new Sort(Sort.Direction.ASC,"idno");
    private final int pageSize = 1000;


    private final String CARD_TYPE = "身份证";
    private Person transPerson(Person person){

        person.setDistrictCode(person.getDistrictCode() +"001");
        person.setSNation(commonToolsService.getGanzhouNation(person.getSNation()));
        return person;
    }

    @Transactional
    public boolean importToDb(Boolean toDbFlag){

        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(CommonToolsService.MAX_READ_SIZE);

        Sheet verifySheet = commonToolsService.getNewSheet(verifyWorkbook, "居民错误信息", "原始行号,证件类型,证件号码,证件姓名,民族,家庭住址,本人电话,所属自然村,备注", ",");

        int verifyRowCount = 1;
        Map<String, String> villageMap = new HashMap<>();
        Set<String> idCardSet = new HashSet<>();
        for (int i = 0;; i++) {
            Pageable pageable = PageRequest.of(i,pageSize,sort);
            Page<Person> personPage = personRepository.findByDistrictCodeIn(Lists.newArrayList("360722106204","360722106216"),pageable);
            if(personPage.getContent().isEmpty()){
                break;
            }

            // 存储入数据库
            for(Person person : personPage.getContent()){
                Person sPerson = transPerson(person);

                String errorInfo = getCitizenErrorInfo(CARD_TYPE, sPerson.getIdno(),sPerson.getName(),sPerson.getNowAddress(), sPerson.getDistrictCode(), idCardSet, villageMap);

                if(!Strings.isNullOrEmpty(errorInfo)){
                    Row verifyRow = verifySheet.createRow(verifyRowCount);
                    commonToolsService.fillSheetRow(verifyRowCount ++ ,verifyRow,CARD_TYPE, sPerson.getIdno(),sPerson.getName(),sPerson.getNowAddress(), sPerson.getSPhone(), sPerson.getDistrictCode(),errorInfo);
                    // 若有错则不做保存
                    continue;
                }
                if(!toDbFlag){
                    continue;
                }

                // save person
            }
        }


        commonToolsService.saveExcelFile(verifyWorkbook, "居民错误信息列表");
        return true;
    }

  private Citizen personToCitizen(Person person){
      Citizen citizen = new Citizen();
      IdCard cardInfo = IdCard.tryParse(person.getIdno());
      citizen.setGender(ConvertUtil.getGender(cardInfo.getGender()));
      citizen.setBirthday(cardInfo.getBirthday().toDate());
      citizen.setIdType(IdType.ID);
      citizen.setIdNo(person.getIdno());
      citizen.setAddress(person.getNowAddress());
      citizen.setNation(CommonToolsService.getNation(person.getSNation()));
      if(Phone.match(person.getSPhone())) {
          citizen.setPhone(person.getSPhone());
      }
      citizen.setIdName(person.getName());
      citizen.setLocation(Long.parseLong(person.getDistrictCode()));
      return citizen;
  }



    private String getCitizenErrorInfo(String cardType, String idCard, String idName, String address, String village, Set<String> idCardSet, Map<String, String> villageMap){
        Integer count = 1;
        StringBuilder sb = new StringBuilder();
        if(!"身份证".equals(cardType) && !"出生证明".equals(cardType)){
            sb.append(count++).append("、证件类型只能为【身份证】或【出生证明】且不能为空\r\n");
        }
        if("身份证".equals(cardType) && Objects.isNull(IdCard.tryParse(idCard))){
            sb.append(count++).append("、身份证号码为空或格式不正确\r\n");
        }
        if("出生证明".equals(cardType) && !IdCard.validBirthNo(idCard)){
            sb.append(count++).append("、出生证明为空或格式不正确\r\n");
        }
        if(citizenDao.findByIdNo(idCard).size() > 0){
            sb.append(count++).append("、身份证号码或出生证明在数据库中重复\r\n");
        }
        if(idCardSet.contains(idCard)){
            sb.append(count++).append("、身份证号码或出生证明在excel中重复\r\n");
        }
        if(Strings.isNullOrEmpty(idName) || idName.length() > 30){
            sb.append(count++).append("、身份证姓名为空或长度大于30\r\n");
        }
        if(!Strings.isNullOrEmpty(idCard)) {
            idCardSet.add(idCard);
        }
        if( address.length() > 200){
            sb.append(count++).append("、家庭住址长度大于200\r\n");
        }
        //自然村信息转换为long报错时增加错误信息，在数据库中查不到时增加错误信息
        try {
            if (villageMap.get(village) == null){
                List<GB2260> gb2260s = gb2260Dao.findByCanonicalCode(Long.parseLong(village));
                if (Objects.isNull(gb2260s)){
                    sb.append(count++).append("、所属自然村为空或不为整数\r\n");
                }else {
                    if(gb2260s.size() < 1 || !gb2260s.get(0).getDepth().equals(6)){
                        sb.append(count++).append("、所属自然村编码在数据库中不存在\r\n");
                    }else {
                        villageMap.put(gb2260s.get(0).getCanonicalCode().toString(), gb2260s.get(0).getName());
                    }
                }
            }
        }catch (NumberFormatException e){
            sb.append(count).append("、所属自然村为空或不为整数\r\n");
        }

        return sb.toString();

    }

}
