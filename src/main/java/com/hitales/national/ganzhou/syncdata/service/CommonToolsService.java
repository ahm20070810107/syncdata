package com.hitales.national.ganzhou.syncdata.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.hitales.national.ganzhou.syncdata.enums.Nation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-25
 * @time:13:51
 */

@Component
@Slf4j
public class CommonToolsService {

    @Value("${verify.output.path}")
    private String verifyResultFile;

    private static Map<String, Nation> mapNation = new HashMap<>();
    private static Map<String,String> mapGanzhou = new HashMap<>();
    public static final Integer MAX_READ_SIZE = 1000;

    @PostConstruct
    private void init(){
        String ganzhouNation = "{'01':'汉族','1':'汉族','02':'蒙古族','03':'回族','04':'藏族','05':'维吾尔族','06':'苗族','07':'彝族','08':'壮族','09':'朝鲜族','10':'布依族','11':'满族','12':'侗族','13':'瑶族','14':'白族','15':'土家族','16':'哈尼族','17':'哈萨克族','18':'傣族','19':'黎族','20':'傈僳族','21':'佤族','22':'畲族','23':'高山族','24':'拉枯族','25':'水族','26':'东乡族','27':'纳西族','28':'景颇族','29':'柯尔克孜族','30':'土族','31':'达翰尔族','32':'仫佬族','33':'羌族','34':'布朗族','35':'撒拉族','36':'毛南族','37':'仡佬族','38':'锡伯族','39':'阿昌族','40':'普米族','41':'塔吉克族','42':'怒族','43':'乌孜别克族','44':'俄罗斯族','45':'鄂温克族','46':'德昂族','47':'保安族','48':'裕固族','49':'京族','50':'塔塔尔族','51':'独龙族','52':'鄂伦春族','53':'赫哲族','54':'门巴族','55':'珞巴族','56':'基诺族','57':'其他','58':'外国血统'}";
        JSONObject jsonObject = JSON.parseObject(ganzhouNation);
        for(Map.Entry<String,Object> nation : jsonObject.entrySet()){
             mapGanzhou.put(nation.getKey(),nation.getValue().toString());
        }
        for(Nation nation : Nation.values()){
            mapNation.put(nation.getDesc(),nation);
        }
    }

    public String getGanzhouNation(String key){
        if(mapGanzhou.containsKey(key)){
            return mapGanzhou.get(key);
        }
        return "";
    }

    public static Nation getNation(String nation){
        if(Strings.isNullOrEmpty(nation))
            return null;
        return mapNation.get(nation);
    }


    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        // 判断数据的类型
        switch (cell.getCellTypeEnum()) {
            case NUMERIC: // 数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = cell.getDateCellValue();
                    return sdf.format(date);
                } else{//处理数值格式
                    return  NumberToTextConverter.toText(cell.getNumericCellValue());
                }
            case STRING: // 字符串
                return cell.getStringCellValue();
            case BOOLEAN: // Boolean
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: // 公式
                return String.valueOf(cell.getCellFormula());
            case BLANK: // 空值
                return "";
            case ERROR: // 故障
                return  "非法字符";
            default:
                return  "未知类型";
        }
    }

    public void saveExcelFile(Workbook workbook, String fileSuffix){
        saveExcelFile(workbook,verifyResultFile,fileSuffix,".xlsx");
    }


    public void saveExcelFile(Workbook workbook, String filePathPrefix, String fileSuffix, String suffix ){
        String savePath = filePathPrefix + fileSuffix + suffix;
        saveExcelFile(savePath,workbook);
    }

    void saveExcelFile(String savePathName,Workbook workbook){
        File file = new File(savePathName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(file.exists()) {
            if(!file.delete()){
                throw new RuntimeException(String.format("旧校验结果【%s】删除失败，不能写入新校验结果！",savePathName));
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(savePathName);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Sheet getNewSheet(Workbook workbook, String sheetName, String headerDes, String splitChar){
        Sheet sheet = workbook.createSheet(sheetName);
        String[] headers = headerDes.split(splitChar);
        Row row = sheet.createRow(0);
        for(int i = 0; i<headers.length; i++){
            row.createCell(i).setCellValue(headers[i]);
        }
        return sheet;
    }

    public Sheet getSheet(String filePathName,String sheetName ){
        if(Strings.isNullOrEmpty(sheetName)){
            return null;
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(filePathName);
            return workbook.getSheet(sheetName);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void fillSheetRow(Row row, String ... params){
        int cellIndex = 0;
        for(String param : params){
            row.createCell(cellIndex++).setCellValue(param);
        }
    }


    static boolean isNumberic(String value, Integer length){
        if(!Objects.isNull(length)){
            if(!length.equals(value.length())){
                return false;
            }
        }
        for(char ch : value.toCharArray()){
            if(ch < '0' || ch > '9'){
                return false;
            }
        }
        return true;
    }


}
