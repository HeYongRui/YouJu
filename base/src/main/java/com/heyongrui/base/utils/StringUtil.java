package com.heyongrui.base.utils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 验证是否是手机号码
     *
     * @param phone 手机号码
     */
    public static boolean isPhone(String phone) {
        boolean isPhone = true;
        if (phone.isEmpty()) {
            isPhone = false;
        } else {
            String value = phone.substring(0, 1);
            if (value.equals("1") && phone.length() == 11) {
                isPhone = true;
                // 验证手机的正则表达式
                String str = "^1[0-9]{10}$";
                Pattern pattern = Pattern.compile(str);
                Matcher matcher = pattern.matcher(phone);
                isPhone = matcher.matches();
            } else {
                isPhone = false;
            }
        }
        return isPhone;
    }

    /**
     * 验证是否是香港手机号码
     *
     * @param phone 手机号码
     */
    public static boolean isHongKongPhone(String phone) {
        boolean isPhone = true;
        isPhone = !phone.isEmpty() && phone.length() == 8;
        return isPhone;
    }

    /**
     * 验证是否是台湾手机号码
     *
     * @param phone 手机号码
     */
    public static boolean isTaiwanPhone(String phone) {
        boolean isPhone = true;
        isPhone = !phone.isEmpty() && phone.length() == 9;
        return isPhone;
    }

    /**
     * 验证是否是澳门手机号码
     *
     * @param phone 手机号码
     */
    public static boolean isMacauPhone(String phone) {
        boolean isPhone = true;
        isPhone = !phone.isEmpty() && phone.length() >= 7 && phone.length() <= 8;
        return isPhone;
    }

    /**
     * 验证URL是否为视频URL
     */
    public static boolean isVideoURl(String url) {
        int x = url.lastIndexOf(".");
        String videoType = url.substring(x);
        return videoType.equalsIgnoreCase(".mp4") || videoType.equalsIgnoreCase(".3gp")
                || videoType.equalsIgnoreCase(".AVI") || videoType.equalsIgnoreCase(".WMV")
                || videoType.equalsIgnoreCase(".rmvb") || videoType.equalsIgnoreCase(".flv");
    }


    /**
     * 隐藏手机号码中间几位数字
     */
    public static String hidePhoneMiddle(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    /**
     * 验证是否6-18位字母数字组合密码
     *
     * @param password 密码
     */
    public static boolean isPassword(String password) {
        boolean isPassword = false;
        if (password.isEmpty()) {
            isPassword = false;
        } else {
            String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
            Pattern pattern = Pattern.compile(str);
            Matcher matcher = pattern.matcher(password);
            isPassword = matcher.matches();
        }
        return isPassword;

    }

    /**
     * 验证是否是网址
     *
     * @param string 待验证字符串
     */
    public static boolean isUrl(String string) {
        boolean isUrl = false;
        if (!StringUtils.isTrimEmpty(string)) {
            Pattern pattern = Pattern
                    .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
            isUrl = pattern.matcher(string).matches();
        }
        return isUrl;
    }

    /**
     * 截取网址中的文件名和后缀返回
     */
    public static String getFileName(String url) {
        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
        Matcher mc = pat.matcher(url);//条件匹配
        while (mc.find()) {
            //截取文件名和后缀名作为文件名
            return mc.group();
        }
        return "";
    }

    /**
     * 解析字符串
     *
     * @param string 待解码字符串
     */
    public static String decodeString(String string) {
        String str = null;
        try {
            if (!string.isEmpty() && string != null) {
                str = new String(string.getBytes("ISO-8859-1"), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 比较版本号大小(前者大返回正数，后者大返回负数)
     */
    public static int compareVersion(@NonNull String version1, @NonNull String version2) {
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    /**
     * MD532位加密大写
     */
    public static String encryptMD5_32Bit(String source) {
        String message = source + "#g5Fv;7Dvk";
        MessageDigest messageDigest = null;
        StringBuilder builder = new StringBuilder();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(message.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    builder.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    builder.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 从字符串中获取数字
     */
    public static String getNumberFromString(String string) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return m.replaceAll("").trim().toString() + "";
    }

    /**
     * 从字符串中判断是否包含汉字字符
     */
    public static boolean isHaveChinese(String string) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return m.find();
    }

    public static boolean isNumber(String string) {
        if (StringUtils.isTrimEmpty(string)) return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }

    /**
     * 获取字符串(网址)中对应参数值
     */
    public static String getParmFromUrl(String url, String name) {
        String parm = "";
        Map<String, String> parmMap = getParmMap(url);
        if (parmMap != null) {
            parm = parmMap.get(name);
        }
        return parm;
    }

    /**
     * 获取字符串(网址)中参数键值对
     */
    public static Map<String, String> getParmMap(String url) {
        //截取?后面的带&的键值对，并解析成Map
        Map<String, String> map = null;
        String[] urlParts = url.split("\\?");
        if (urlParts.length > 1) {
            String query = urlParts[1];
            if (!StringUtils.isTrimEmpty(query) && query.indexOf("&") > -1 && query.indexOf("=") > -1) {
                map = new HashMap<>();
                String[] arrTemp = query.split("&");
                for (String str : arrTemp) {
                    String[] qs = str.split("=");
                    map.put(qs[0], qs[1]);
                }
            }
        }
        return map;
    }

    /**
     * 格式化金额
     *
     * @param price          待格式化的价格
     * @param roundingMode   四舍五入模式
     * @param isSymbol       是否带有国际币种符号
     * @param isGroupingUsed 是否启用分组(数字每隔几位一个逗号)
     */
    public static String formatPrice(double price, RoundingMode roundingMode, boolean isMinFraction, boolean isSymbol, boolean isGroupingUsed) {
        DecimalFormat formater = new DecimalFormat();
        //保留两位小数
        if (isMinFraction) {
            formater.setMinimumFractionDigits(2);//保证23.20不会变成23.2
        }
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(3);
        formater.setGroupingUsed(isGroupingUsed);
//        formater.setRoundingMode(halfUp ? RoundingMode.HALF_UP : RoundingMode.FLOOR);
        formater.setRoundingMode(roundingMode == null ? RoundingMode.UP : roundingMode);
        String result = formater.format(price);
        if (isSymbol) {
            result = Currency.getInstance(Locale.CHINA).getSymbol(Locale.CHINA) + result;
        }
        return result;
//        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);//设置货币符号
//        //保留两位小数
//        if (isMinFraction) {
//            format.setMinimumFractionDigits(2);//保证23.20不会变成23.2
//        }
//        format.setMaximumFractionDigits(2);
//        format.setRoundingMode(halfUp ? RoundingMode.HALF_UP : RoundingMode.FLOOR);
//        String result = format.format(price);
//        return result;
    }

    /**
     * 每个4位加一个空格
     *
     * @param string 带转换字符
     */
    public static String regexString(String string) {
        String regex = "(.{4})";
        return string.replaceAll(regex, "$1\t\t");
    }

    /**
     * 把数字格式化成以千/K为单位的字符串
     *
     * @param units 带转换的数值
     */
    public static String formatUnitCount(int units) {
        if (units <= 0) return "0";
        if (units > 0 && units < 1000) return units + "";
        BigDecimal b1 = new BigDecimal(Double.toString(units));
        BigDecimal b2 = new BigDecimal(Double.toString(1000));
        BigDecimal divide = b1.divide(b2, 1, BigDecimal.ROUND_FLOOR);
        double d = divide.doubleValue();
        //去掉小数点后多余的0
        String result = divide.stripTrailingZeros().toPlainString();
        if (d > 0 && d < 10) {
            result = result + "K";
        } else if (d >= 10) {
            result = "10K+";
        }
        return result;
    }
}
