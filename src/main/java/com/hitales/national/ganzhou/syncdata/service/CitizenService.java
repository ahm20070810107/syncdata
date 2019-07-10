package com.hitales.national.ganzhou.syncdata.service;

import com.google.common.base.Strings;
import com.hitales.national.ganzhou.syncdata.common.IdCard;
import com.hitales.national.ganzhou.syncdata.common.Phone;
import com.hitales.national.ganzhou.syncdata.dao.CitizenDao;
import com.hitales.national.ganzhou.syncdata.dao.GB2260Dao;
import com.hitales.national.ganzhou.syncdata.entity.Citizen;
import com.hitales.national.ganzhou.syncdata.entity.GB2260;
import com.hitales.national.ganzhou.syncdata.enums.CitizenGender;
import com.hitales.national.ganzhou.syncdata.enums.IdType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Map<String,Long> villageMap;

    public boolean verify(String citizenSheet,String countySheet){
         SXSSFWorkbook verifyWorkbook = new SXSSFWorkbook(CommonToolsService.MAX_READ_SIZE);
         boolean verifyResult = verifyCitizen(citizenSheet,countySheet,verifyWorkbook);

         commonToolsService.saveExcelFile(verifyWorkbook, citizenSheet);
         return verifyResult;
    }

    @Transactional
    public boolean importToDb(String citizenSheet,String countySheet){
        if(!verify(citizenSheet,countySheet)){
            return false;
        }
//        String countyPrefix = commonToolsService.getCountyPrefix(countySheet, true);
        villageMap = new HashMap<>();
        List<Citizen> citizens = new ArrayList<>();
        XSSFSheet sourceDataSheet = commonToolsService.getSourceSheetByName(citizenSheet);
        // 将excel中数据全部取出转换为citizen再统一存储
        for (int i = 1; i <= sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            citizens.add(sheetRowToCitizen(row));
        }

        // 存储入数据库
        int SAVE_COUNT = 500;
        int i = 0;
        for(; (i+1)*SAVE_COUNT <= citizens.size(); i ++){
            citizenDao.saveAll(citizens.subList(i*SAVE_COUNT, (i+1)*SAVE_COUNT));
        }
        if(i*SAVE_COUNT != citizens.size()) {
            citizenDao.saveAll(citizens.subList(i * SAVE_COUNT, citizens.size()));
        }
        return true;
    }



    private Citizen sheetRowToCitizen(Row citizenRow){
        Citizen citizen = new Citizen();

        String cardType = CommonToolsService.getCellValue(citizenRow.getCell(0));
        String idCard = CommonToolsService.getCellValue(citizenRow.getCell(1));
        String idName = CommonToolsService.getCellValue(citizenRow.getCell(2));
        String nation = CommonToolsService.getCellValue(citizenRow.getCell(3));
        String address = CommonToolsService.getCellValue(citizenRow.getCell(4)).trim();
        String phone = CommonToolsService.getCellValue(citizenRow.getCell(5));
        String village = CommonToolsService.getCellValue(citizenRow.getCell(6));

        if(cardType.equals(IdType.ID.getDesc())) {
            IdCard cardInfo = IdCard.tryParse(idCard);
            if (Objects.isNull(cardInfo)) {
                throw new RuntimeException(String.format("身份证号码【%s】格式错误！", idCard));
            }
            citizen.setGender(getGender(cardInfo.getGender()));
            citizen.setBirthday(cardInfo.getBirthday().toDate());
        }

        citizen.setIdType( cardType.equals(IdType.BIRTH.getDesc())? IdType.BIRTH : IdType.ID);
        citizen.setIdNo(idCard);
        citizen.setNation(commonToolsService.getNation(nation));
        citizen.setAddress(address);
        if(Phone.match(phone)) {
            citizen.setPhone(phone);
        }
        citizen.setIdName(idName);
        citizen.setLocation(Long.parseLong(village));
        return citizen;
    }

    public CitizenGender getGender(Integer gender){
        if(gender.equals(1)){
            return CitizenGender.MALE;
        }
        if(gender.equals(2)){
            return CitizenGender.FEMALE;
        }
        return CitizenGender.NOT_SPECIFIED;
    }


    private boolean verifyCitizen(String citizenSheet, String countySheet, SXSSFWorkbook verifyWorkbook) {
        int verifyRowCount = 1;
        Set<String> idCardSet = new HashSet<>();
        Map<String, String> villageMap = new HashMap<>();
        boolean result = true;
        XSSFSheet sourceDataSheet = commonToolsService.getSourceSheetByName(citizenSheet);

        Sheet verifySheet = commonToolsService.getNewSheet(verifyWorkbook, citizenSheet, "原始行号,证件类型,证件号码,证件姓名,民族,家庭住址,本人电话,所属自然村,备注", ",");

        for (int i = 1; i <= sourceDataSheet.getLastRowNum(); i++) {
            Row row = sourceDataSheet.getRow(i);
            String cardType = CommonToolsService.getCellValue(row.getCell(0));
            String idCard = CommonToolsService.getCellValue(row.getCell(1));
            String idName = CommonToolsService.getCellValue(row.getCell(2));
            String nation = CommonToolsService.getCellValue(row.getCell(3));
            String address = CommonToolsService.getCellValue(row.getCell(4)).trim();
            String phone = CommonToolsService.getCellValue(row.getCell(5));
            String village = CommonToolsService.getCellValue(row.getCell(6));
            String errorInfo = getCitizenErrorInfo(cardType, idCard, idName, address, phone, village, idCardSet, villageMap);

            if(!Strings.isNullOrEmpty(errorInfo)){
                Row verifyRow = verifySheet.createRow(verifyRowCount++);
                result = false;
                commonToolsService.fillSheetRow(i+1,verifyRow,cardType,idCard,idName,nation,address,phone,village,errorInfo);
            }
        }
        return result;
    }

    public String getCitizenErrorInfo(String cardType, String idCard, String idName, String address, String phone, String village, Set<String> idCardSet, Map<String, String> villageMap){
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
//        if(Strings.isNullOrEmpty(phone) || !Phone.match(phone)){
//            sb.append(count++).append("、电话号码为空或格式不正确\r\n");
//        }
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
