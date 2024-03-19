package com.thinkgem.jeesite.modules.sys.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 
 */
/**
 * 数字工具类
 * @author oa
 * @version 2017-07-07
 */
public class MathUtils {

    /**
     * 去除字符串数字中小数中无用的0
     * @param value
     * @return
     */
    public static String removeZone(String value) {
        if(value != null && value.trim().length() > 0) {
            while (value.indexOf(".") > -1 && (value.endsWith("0") || value.endsWith("."))) {
                value = value.substring(0, value.length() - 1);
            }
        }
        return value;
    }

    /**
     * 格式化数据，并四舍五入
     * @param value
     * @param numDigits 指定的位数，按此位数进行舍入
     * @return
     */
    public static String formatNum(Double value,int numDigits){
        if(value != null){
            NumberFormat nf = NumberFormat.getInstance();
//            nf.setRoundingMode(RoundingMode.HALF_UP);
//            nf.setMinimumFractionDigits(numDigits);
//            nf.setMaximumFractionDigits(numDigits);
            String s=nf.format(value);
            s=s.replaceAll(",","");
            return  s;
        }
        return "";
    }

    /**
     * 返回某个数字按指定位数舍入后的数字
     * @param number 需要进行舍入的数字
     * @param numDigits 指定的位数，按此位数进行舍入
     * @return
     */
    public static Double round(Double number,int numDigits) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(numDigits, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static void main(String[] args) {
        System.out.println(MathUtils.round(2.122D, 1));
    }
}
