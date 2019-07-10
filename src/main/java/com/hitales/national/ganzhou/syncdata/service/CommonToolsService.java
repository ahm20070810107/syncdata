package com.hitales.national.ganzhou.syncdata.service;

import com.google.common.base.Strings;
import com.hitales.national.ganzhou.syncdata.enums.Nation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
    @Value("${middleExcel.sourceFile}")
    private String sourceFile;

    @Value("${middleExcel.verifyResultFile}")
    private String verifyResultFile;

    private Map<String, Nation> mapNation = new HashMap<>();

    public static final Integer MAX_READ_SIZE = 1000;

    @PostConstruct
    private void init(){
        if(Strings.isNullOrEmpty(sourceFile)){
            throw new RuntimeException("excel路径为空！");
        }
        for(Nation nation : Nation.values()){
            mapNation.put(nation.getDesc(),nation);
        }
    }

    public Nation getNation(String nation){
        if(Strings.isNullOrEmpty(nation))
            return null;
        return mapNation.get(nation);
    }

    public XSSFSheet getSourceSheetByName(String sheetName){
        if(Strings.isNullOrEmpty(sheetName)){
            throw new RuntimeException("sheetName不能为空！");
        }
        XSSFWorkbook xssfSourceWorkbook;
        try {
            xssfSourceWorkbook = new XSSFWorkbook(sourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        XSSFSheet xssfSheet = xssfSourceWorkbook.getSheet(sheetName);
        if(Objects.isNull(xssfSheet)){
            throw new RuntimeException(String.format("【%s】sheet在excel中不存在！", sheetName));
        }

        return xssfSheet;
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
        String savePath = filePathPrefix + "_" + fileSuffix + suffix;
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

    public void fillSheetRow(int index,Row row, String ... params){
        int cellIndex = 1;
        row.createCell(0).setCellValue(index);
        for(String param : params){
            row.createCell(cellIndex++).setCellValue(param);
        }
    }

    public String getCountyPrefix(String countySheet, boolean prefix){
        Sheet sheet = getSourceSheetByName(countySheet);
        String code = sheet.getRow(1).getCell(2).getStringCellValue();
        if(prefix && !Objects.isNull(code) && code.length() > 6){
            return code.substring(0,6);
        }
        return Objects.isNull(code)?"": code;
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
