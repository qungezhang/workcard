package cn.stylefeng.guns.core.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static java.lang.String.valueOf;

public class RandomStringUtil {
    private final static char[] cs = "零一二三四五六七八九".toCharArray();
    private final static char[] regx = "甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戌亥".toCharArray();
    private final static char[] capital = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final static char[] capitalLow = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /**
     * @param passLength
     *            : 要生成多少长度的字符串
     * @param type
     *            : 需要哪种类型
     * @return 根据传入的type来判定
     */

    // 可以根据自己需求来删减下面的代码，不要要的类型可以删掉

    // type=0：纯数字(0-9)
    // type=1：全小写字母(a-z)
    // type=2：全大写字母(A-Z)
    // type=3: 数字+小写字母
    // type=4: 数字+大写字母
    // type=5：大写字母+小写字母
    // type=6：数字+大写字母+小写字母
    // type=7：固定长度33位：根据UUID拿到的随机字符串，去掉了四个"-"(相当于长度33位的小写字母加数字)

    public static String getRandomCode(int passLength, int type) {
        StringBuffer buffer = null;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        r.setSeed(new Date().getTime());
        switch (type) {
            case 0:
                buffer = new StringBuffer("0123456789");
                break;
            case 1:
                buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyz");
                break;
            case 2:
                buffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                break;
            case 3:
                buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
                break;
            case 4:
                buffer = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                break;
            case 5:
                buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
                break;
            case 6:
                buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
                sb.append(buffer.charAt(r.nextInt(buffer.length() - 10)));
                passLength -= 1;
                break;
            case 7:
                String s = UUID.randomUUID().toString();
                sb.append(s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24));
        }

        if (type != 7) {
            int range = buffer.length();
            for (int i = 0; i < passLength; ++i) {
                sb.append(buffer.charAt(r.nextInt(range)));
            }
        }
        return sb.toString();
    }
    
    /**
     *数1,2字转一,二
     * @param number  数字
     * @return  字符串
     */
    private static String NumberToString(int number){
    	StringBuffer buffer = new StringBuffer("");
    	while(number > 0){
    		buffer.append(cs[number % 10]);
    		number/=10;
    	}
    	return buffer.reverse().toString();
//        String temp="";
//        String temp1="";
//        while(number>0){
//            temp += cs[number % 10];
//            //	System.out.println(temp);
//            number/=10;
//        }
//        char[] ch = temp.toCharArray();
//        for (int i = ch.length-1; i >=0 ; i--) {
//            temp1 += ch[i];
//        }
//        return temp1;
    }
    
    /**
     *数1,2字转01,02
     * @param number  数字
     * @return  字符串
     */
    public static String NumberTo01(int number,int length) {
    	if(length > 0){
    		return String.format(MessageFormat.format("%0{0}d", length), number);
    	}
    	return number+"";
//        return String.format("%02d", number);
    }
    
    
    /**
     *数1,2字转甲,乙
     * @param number  数字
     * @return  字符串
     */
    private static String NumberToHeavenlyStems(int number) {
        return valueOf(regx[number-1]);
    }

    /**
     *
     *
     * @param number 数字
     * @return 字符串
     */
    private static String NumberToRE(int number, char[] cs) {
        return valueOf(cs[number - 1]);
    }
    /**
     *数1,2字转大写A,B
     * @param number  数字
     * @return  字符串
     */
    private static String NumberToCapital(int number) {
        return valueOf(capital[number-1]);
    }


    public static String NumberToRelease(String rule,int number) {
        if (rule.equals("01")) {
            return NumberTo01(number,2);
        }
        if (rule.equals("001")) {
            String s = NumberTo01(number, 3);
            return s;
        }
        if (rule.equals("一")) {
            return NumberToString(number);
        }
        if (rule.equals("甲")) {
            return NumberToHeavenlyStems(number);
        }
        if (rule.equals("A")) {
            return NumberToCapital(number);
        }
        return null;
    }

    public static Integer getIndexByRuleAndStart(String rule, String start) {
        if (StringUtils.isBlank(start)) {
            return 1;
        }
        if (rule.contains("一")) {
            for (int i = 0; i < cs.length; i++) {
                char c = cs[i];
                if (start.equals(String.valueOf(c))) {
                    return i + 1;
                }
            }
        }
        if (rule.contains("甲")) {
            for (int i = 0; i < regx.length; i++) {
                char c = regx[i];
                if (start.equals(String.valueOf(c))) {
                    return i + 1;
                }
            }
        }
        if (rule.contains("A")) {
            for (int i = 0; i < capital.length; i++) {
                char c = capital[i];
                if (start.equals(String.valueOf(c))) {
                    return i + 1;
                }
            }
        }
        return Integer.valueOf(start);
    }
    
//    /**
//     * 获取编号
//     * @param configEnum
//     * @param num
//     * @return
//     */
//    public static String getCode(ConfigEnum configEnum,Integer num){
//    	String code = null;
//    	if(!Assert.isNull(num) && num > 0){
//    		switch (configEnum) {
//    		case COMPANY: //企业编号  4位数字
//    			code = NumberTo01(num, 4);
//    			break;
//    		case COMPANY_BREVITY: //企业简码    两位，第一位数字，第二位字母 [0-9][a-z]
////    			if(num > 260){
////        			throw new RRException("请更新企业简码生成规则");
////        		}
//        		num -= 1;
//        		code = num/26+valueOf(capitalLow[num%26]);
//    			break;
//    		case INSTALL: //安装单号  三位业务编号+6位日期+三位序号		100安装
//    			code = "100"+DateUtils.format(new Date(), "yyMMdd")+NumberTo01(num, 3);
//    			break;
//    		case MAINTAIN: //维修单号  三位业务编号+6位日期+三位序号            	101维修
//    			code = "101"+DateUtils.format(new Date(), "yyMMdd")+NumberTo01(num, 3);
//    			break;
//    		case MAINTENANCE: //维护单号  三位业务编号+6位日期+三位序号  	102维护
//    			code = "102"+DateUtils.format(new Date(), "yyMMdd")+NumberTo01(num, 3);
//    			break;
//    		case EMPLOYEE: //员工编号 S+5位数字
//    			code = "S"+ NumberTo01(num, 5);
//    			break;
//    		case WORKER: //工人编号 6位数字
//    			code = NumberTo01(num, 6);
//    			break;
//            case ASSIGNMENT: //特殊工种作业单号  三位业务编号+6位日期+三位序号    200特殊工种作业单
//                code = "200"+DateUtils.format(new Date(), "yyMMdd")+NumberTo01(num, 3);
//                break;
//            case PATROL: //巡检单号  三位业务编号+6位日期+三位序号    201巡检
//                code = "201"+DateUtils.format(new Date(), "yyMMdd")+NumberTo01(num, 3);
//                break;
//            case PROJECT: //项目编号
//    		case DEPARTMENT: //部门编号
//    		default:
//    			code = num.toString();
//    		}
//    	}
//    	return code;
//    }

    /**
     * 下一个数字
     * @param value
     * @return
     */
    public static Integer addOneCode(String value) {
		if(!Assert.isNull(value)){
			return Integer.valueOf(value)+1;
		}
        return null;
    }

    /**
     * 计算百分比
     * @param num 数量
     * @param total 总数
     * @param scale 设置精确几位小数
     * @return
     */
    public static String accuracy(double num, double total, int scale){
        DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;//百分数
        return df.format(accuracy_num);
    }
    
//    /**
//     * 下一个数字code
//     * @param oldCompanyCode
//     * @return
//     */
//    public static String addOneCode(String oldCompanyCode) {
//        if (StringUtils.isNotBlank(oldCompanyCode)) {
//            String addOneCode = new DecimalFormat("").format((Integer.valueOf(oldCompanyCode) + 1));
//            return addOneCode;
//        }
//        return null;
//    }
//    
//    /**
//     * 下一个企业code
//     * @param oldCompanyCode
//     * @return
//     */
//    public static String getNextCompanyCode(String oldCompanyCode) {
//        if (StringUtils.isNotBlank(oldCompanyCode)) {
//            String formatCode = new DecimalFormat("0000").format((Integer.valueOf(oldCompanyCode) + 1));
//            return formatCode;
//        }
//        return null;
//    }
//    /**
//     * 下一个企业简码
//     * @param oldCode
//     * @return
//     */
//    public static String getNextCode(String oldCode) {
//        char[] chars = oldCode.toCharArray();
//        int i1 = chars[0] - '0';
//        for (int i = 0; i < 10; i++) {
//            if (i1 == i) {
//                for (int j = 0; j < capitalLow.length; j++) {
//                    char c = capitalLow[j];
//                    char aChar = chars[1];
//                    if (aChar == capitalLow[(capitalLow.length - 1)]) {
//                        return valueOf(i + 1) + valueOf(capitalLow[0]);
//                    } else if (aChar == c) {
//                        return valueOf(i) + valueOf(capitalLow[j + 1]);
//
//                    }
//                }
//            }
//
//        }
//
//        return null;
//    }

}
