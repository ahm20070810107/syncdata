package com.hitales.national.ganzhou.syncdata.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hitales.national.ganzhou.syncdata.common.IdCard;
import com.hitales.national.ganzhou.syncdata.common.PersonConverter;
import com.hitales.national.ganzhou.syncdata.dao.*;
import com.hitales.national.ganzhou.syncdata.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private PersonListRepository personListRepository;

    @Autowired
    private GB2260Dao gb2260Dao;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonTagRepository personTagRepository;

    @Autowired
    private CitizenServeTagMappingDao citizenServeTagMappingDao;

    @Autowired
    private CitizenServeTagDao citizenServeTagDao;

    @Autowired
    private CountyDao countyDao;

    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private CitizenEhrDao citizenEhrDao;

    @Autowired
    private CitizenEhrFamilyHistoryDao citizenEhrFamilyHistoryDao;

    @Autowired
    private CitizenEhrGeneticHistoryDao citizenEhrGeneticHistoryDao;

    @Autowired
    private CitizenEhrMedicalHistoryDao citizenEhrMedicalHistoryDao;

    @Value("${hitales.national.ganzhou.townFlag}")
    private Boolean townFlag;

    @Value("${hitales.national.ganzhou.valueList}")
    private String valueList;


    private final Sort sort = new Sort(Sort.Direction.ASC,"idno");
    private final int pageSize = 1000;
    private final int batchSaveSize = 1100;

    private final String CARD_TYPE = "身份证";

    private Person transPerson(Person person){

        person.setDistrictCode(person.getDistrictCode() +"001");
        person.setSNation(CommonToolsService.getGanzhouNation(person.getSNation()));
        return person;
    }

    public String importToDb(Boolean toDbFlag){
        // 信丰县id
        Long countyId = getCountyId(360722000000000L);
        SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(CommonToolsService.MAX_READ_SIZE);
        String[] villageTownList = valueList.split(",");
        List<String> villageList = villageRepository.findByPIdIn(Lists.newArrayList(villageTownList))
                .stream().map(Village::getId).collect(Collectors.toList());

        Sheet verifySheet = commonToolsService.getNewSheet(verifyWorkbook, "居民错误信息", "个人编号,编号,工作编号,姓名,身份证号,出生日期,现住址,联系电话,责任医生,建档机构,状态,所属村代码,所属村名称,备注", ",");

        int verifyRowCount = 1;
        int dealCount = 1;
        Map<String, String> villageMap = new HashMap<>();
        // 用于批量保存档案信息
        ConvertSaveEntityList convertSaveList = new ConvertSaveEntityList();
        Set<String> idCardSet = new HashSet<>();
        for (int i = 0;; i++) {
            Pageable pageable = PageRequest.of(i,pageSize,sort);
            Page<Person> personPage ;
            if(townFlag){
                personPage = personRepository.findByDistrictCodeIn(villageList,pageable);
            }else{
                personPage = personRepository.findByDistrictCodeIn(Lists.newArrayList(villageTownList),pageable);
            }

            if(personPage.getContent().isEmpty()){
                break;
            }

            for(Person person : personPage.getContent()){
//                Person sPerson = transPerson(person);
                String errorInfo = getCitizenErrorInfo(CARD_TYPE,person.getStatus(), person.getIdno(),person.getName(),person.getNowAddress(), person.getDistrictCode() + "001", idCardSet, villageMap);

                log.info("正在处理第{}个人信息。。。",dealCount++ );
                if(!Strings.isNullOrEmpty(errorInfo)){
                    Row verifyRow = verifySheet.createRow(verifyRowCount++);
                    PersonTag personList = personTagRepository.findByPersonid(person.getPersonid()).orElse(new PersonTag());
                    commonToolsService.fillSheetRow(verifyRow,person.getPersonid(),person.getPCardNo(),person.getWCardNo(),person.getName(),person.getIdno(),person.getBirthday(),
                            person.getNowAddress(), person.getSPhone(),personList.getFzDoctor(), personList.getRecordOName(),
                            "1".equals(person.getStatus())?"正常":Strings.isNullOrEmpty(person.getStatus())?"待完善":"死亡",person.getDistrictCode(),person.getDistrictName(),errorInfo);
                    // 若有错则不做保存
                    continue;
                }
                if(!toDbFlag){
                    continue;
                }
                // save person
                PersonTag personTag = personTagRepository.findByPersonid(person.getPersonid()).orElse(new PersonTag());
                savePerson(person, personTag,convertSaveList,countyId);
                if(convertSaveList.getMedicalHistories().size() >= batchSaveSize || convertSaveList.getFamilyHistories().size() >= batchSaveSize){
                    batchSaveEntities(convertSaveList);
                    convertSaveList = new ConvertSaveEntityList();
                }
            }
        }
        batchSaveEntities(convertSaveList);
        commonToolsService.saveExcelFile(verifyWorkbook, "居民错误信息列表");
        String outInfo = String.format("居民信息转换完成,共%d条居民信息有问题未处理，详情请查看[verify.output.path]下excel",verifyRowCount - 1);
        log.info(outInfo);
        return outInfo;
    }

  void batchSaveEntities(ConvertSaveEntityList convertSaveList){
      citizenServeTagMappingDao.saveAll(convertSaveList.getCitizenServeTag());
      citizenEhrFamilyHistoryDao.saveAll(convertSaveList.getFamilyHistories());
      citizenEhrGeneticHistoryDao.saveAll(convertSaveList.getGeneticHistories());
      citizenEhrMedicalHistoryDao.saveAll(convertSaveList.getMedicalHistories());
  }

  @javax.transaction.Transactional
  void savePerson(Person person, PersonTag personTag,ConvertSaveEntityList convertSaveList,Long countyId){
       PersonConverter personConverter = PersonConverter.convert(person,personTag,citizenServeTagDao,countyId);
       Citizen citizen = personConverter.getCitizen();
       citizenDao.save(citizen);

       // 居民ehr
      personConverter.getCitizenEhr().setCitizenId(citizen.getId());
      citizenEhrDao.save(personConverter.getCitizenEhr());

      // 服务标签
      personConverter.getCitizenServeTag().forEach(citizenTag->citizenTag.setCitizenId(citizen.getId()));
      convertSaveList.getCitizenServeTag().addAll(personConverter.getCitizenServeTag());

      // 家族史
      personConverter.getFamilyHistories().forEach(family-> family.setEhrId(personConverter.getCitizenEhr().getId()));
      convertSaveList.getFamilyHistories().addAll(personConverter.getFamilyHistories());

      // 遗传史
      personConverter.getGeneticHistories().forEach(genetic->genetic.setEhrId(personConverter.getCitizenEhr().getId()));
      convertSaveList.getGeneticHistories().addAll(personConverter.getGeneticHistories());

      // 用药史
      personConverter.getMedicalHistories().forEach(medical->medical.setEhrId(personConverter.getCitizenEhr().getId()));
      convertSaveList.getMedicalHistories().addAll(personConverter.getMedicalHistories());

  }

   private Long getCountyId(Long location){
        return countyDao.findByLocation(location)
                .orElseThrow(()->new RuntimeException("信丰县在数据库中(行政县列表)未找到，请检查！"))
                .getId();
   }

   private String getCitizenErrorInfo(String cardType,String status, String idCard, String idName, String address, String village, Set<String> idCardSet, Map<String, String> villageMap){
        Integer count = 1;
        StringBuilder sb = new StringBuilder();
        if(!"身份证".equals(cardType) && !"出生证明".equals(cardType)){
            sb.append(count++).append("、证件类型只能为【身份证】或【出生证明】且不能为空\r\n");
        }
        if(Strings.isNullOrEmpty(status)){
            sb.append(count++).append("、居民状态为待完善\r\n");
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
