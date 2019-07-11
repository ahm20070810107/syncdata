package com.hitales.national.ganzhou.syncdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-07-04
 * @time:15:03
 */
public class TestSomething {

    @Test
    public void test111(){
        String ss = "[{'value':'01','content':'汉族'},{'value':'02','content':'蒙古族'},{'value':'03','content':'回族'},{'value':'04','content':'藏族'},{'value':'05','content':'维吾尔族'},{'value':'06','content':'苗族'},{'value':'07','content':'彝族'},{'value':'08','content':'壮族'},{'value':'09','content':'朝鲜族'},{'value':'10','content':'布依族'},{'value':'11','content':'满族'},{'value':'12','content':'侗族'},{'value':'13','content':'瑶族'},{'value':'14','content':'白族'},{'value':'15','content':'土家族'},{'value':'16','content':'哈尼族'},{'value':'17','content':'哈萨克族'},{'value':'18','content':'傣族'},{'value':'19','content':'黎族'},{'value':'20','content':'傈僳族'},{'value':'21','content':'佤族'},{'value':'22','content':'畲族'},{'value':'23','content':'高山族'},{'value':'24','content':'拉枯族'},{'value':'25','content':'水族'},{'value':'26','content':'东乡族'},{'value':'27','content':'纳西族'},{'value':'28','content':'景颇族'},{'value':'29','content':'柯尔克孜族'},{'value':'30','content':'土族'},{'value':'31','content':'达翰尔族'},{'value':'32','content':'仫佬族'},{'value':'33','content':'羌族'},{'value':'34','content':'布朗族'},{'value':'35','content':'撒拉族'},{'value':'36','content':'毛南族'},{'value':'37','content':'仡佬族'},{'value':'38','content':'锡伯族'},{'value':'39','content':'阿昌族'},{'value':'40','content':'普米族'},{'value':'41','content':'塔吉克族'},{'value':'42','content':'怒族'},{'value':'43','content':'乌孜别克族'},{'value':'44','content':'俄罗斯族'},{'value':'45','content':'鄂温克族'},{'value':'46','content':'德昂族'},{'value':'47','content':'保安族'},{'value':'48','content':'裕固族'},{'value':'49','content':'京族'},{'value':'50','content':'塔塔尔族'},{'value':'51','content':'独龙族'},{'value':'52','content':'鄂伦春族'},{'value':'53','content':'赫哲族'},{'value':'54','content':'门巴族'},{'value':'55','content':'珞巴族'},{'value':'56','content':'基诺族'},{'value':'57','content':'其他'},{'value':'58','content':'外国血统'}]";
        JSONArray jsonArray = JSON.parseArray(ss);
        jsonArray.forEach(value->{
            JSONObject jsonObject = (JSONObject) value;
            System.out.print("'" + jsonObject.get("value") + "':'"+ jsonObject.get("content") + "'," );
        });

    }

}
