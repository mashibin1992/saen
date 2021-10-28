package com.jiangjun.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jiangjun.library.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


/**
 * Created by Administrator姜军 on 2018/3/9.
 */

public class StringUtil {


    private final static String TAG = "StringUtil";
    private static final String PATTER = "\\[.*?\\]";
    private static final String PATTER_IMG = "\\[img\\].*?\\[/img\\]";
    private static final String PATTER_HTML = "[&nbsp;|&hellip;|&mdash;|&alpha;| ]";
    private static final String YEAR_MONTH_DAY_24HOUR_MINUTE_SECOND_TEMPLATE = "yyyy-MM-dd HH:mm:ss";
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> timeFormater1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };

    /**
     * 删除str指定的后缀
     *
     * @param str
     * @param suffix
     * @return
     */
    public static String removeSuffix(String str, String suffix) {
        if (null == str)
            return null;
        if ("".equals(str.trim()))
            return "";

        if (null == suffix || "".equals(suffix))
            return str;

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }

        return "";
    }

    /**
     * 按长度截取字符串
     *
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        if (isBlank(str)) {
            return "";
        } else if (str.length() > length) {
            return str.substring(0, length);
        } else {
            return str;
        }
    }

    /**
     * 是否是空的（包括null、""、空格）
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (null == str)
            return true;
        if ("".equals(str.trim()))
            return true;

        return false;
    }

    /**
     * 判断富文本 是否包含空格
     *
     * @param str
     * @param sqlit
     * @param contains1
     * @param contains2
     * @return
     */
    public static boolean isBlankNbsp(String str, String sqlit, String contains1, String contains2) {


        String[] split = str.split(sqlit);

        for (int i = 0; i < split.length; i++) {
            if (!isBlank(split[i])) {
                if (!split[i].equals(contains1) && !split[i].equals(contains2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isBlank(Long str) {
        if (null == str)
            return true;
        return false;
    }

    /**
     * 将对象转成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    public static String add(String str, int num) {
        int i = num;
        if (!isBlank(str)) {
            int intStr = Integer.parseInt(str);
            i = i + intStr;
        }

        return Integer.toString(i);
    }

    /**
     * 判断一个字符串是否都为数字
     */
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    /**
     * @param str
     * @return
     */
    public static String getString(String str) {
        if (null == str)
            return "";
        return str;

    }

    /**
     * 计算两个数字字符串的和
     *
     * @param augend
     * @param addend
     * @return
     */
    public static String getSum(String augend, String second, String addend) {
        if (augend == null)
            augend = "0";
        if (second == null)
            second = "0";
        if (addend == null)
            addend = "0";
        int sum = Integer.parseInt(augend) + Integer.parseInt(second) + Integer.parseInt(addend);
        return new Integer(sum).toString();
    }

    public static String change(String str, int n, boolean isLeft) {
        if (str == null || str.length() >= n)
            return str;
        String s = "";
        for (int i = str.length(); i < n; i++)
            s += "0";
        if (isLeft)
            return s + str;
        else
            return str + s;
    }

    public static String getInString(String str) {
        if (str == null)
            return null;
        StringBuffer sb = new StringBuffer("");
        String s[] = str.split(",");
        if (s != null && s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                if (i != 0)
                    sb.append(",");
                sb.append("'").append(s[i]).append("'");
            }
        }
        return sb.toString();
    }

    public static String subInStringByFlag(String str, String flag) {
        if (isBlank(str))
            return null;
        StringBuffer sb = new StringBuffer(str);
        int index = str.lastIndexOf(flag);
        if (index < 0) {
            return str;
        } else {
            str = sb.delete(sb.length() - flag.length(), sb.length()).toString();
            index = str.indexOf(flag);
            if (index < 0) {
                return str;
            } else {
                return sb.deleteCharAt(0).toString();
            }
        }
    }

    /**
     * 根据标识获取str中最后一个flag后的内容
     *
     * @param str
     * @param flag
     * @return
     */
    public static String getLastStr(String str, String flag) {
        if (isBlank(str))
            return null;
        int index = str.lastIndexOf(flag);
        if (index < 0) {
            return str;
        } else {
            return str.substring(index + flag.length());
        }

    }

    /**
     * 获取正则表达式匹配的字符串，将$符处理一下，不然匹配时会认作分组
     *
     * @param str
     * @return
     */
    public static String getRegexStr(String str) {
        String ret = "";
        if (isBlank(str))
            return "";
        if (str.indexOf('$', 0) > -1) {
            while (str.length() > 0) {
                if (str.indexOf('$', 0) > -1) {
                    ret += str.subSequence(0, str.indexOf('$', 0));
                    ret += "\\$";
                    str = str.substring(str.indexOf('$', 0) + 1, str.length());
                } else {
                    ret += str;
                    str = "";
                }
            }

        } else {

            ret = str;
        }

        return ret;

    }

    /**
     * 根据正则表达式截取匹配的字符串列表
     * 从指定字符串中，按正则表达式要求，找出其中能匹配上的字符串列表
     *
     * @param str
     * @param regx
     * @return
     */
    public static List catchStr(String str, String regx) {
        if (isBlank(str))
            return null;

        List ret = new ArrayList();
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        int start = 0;
        int end = 0;
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
            ret.add(str.substring(start, end));
        }
        return ret;

    }

    /**
     * 过滤换行符
     *
     * @param str
     * @return
     */
    public static String filterNextLine(String str) {
        if (isBlank(str))
            return "";
        return str.replaceAll("[\n\r\u0085\u2028\u2029]", "");
    }

    public static String lpad(String str, int length) {
        if (isBlank(str))
            return "";
        int j = str.length();
        for (int i = j; i < length; i++) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 创建异常页面源码字符串，用于MOS Native替代抛出的异常页面
     *
     * @param errMsg
     * @return
     */
    public static String getAppException4MOS(String errMsg) {
        String str = "<!DOCTYPE html><html><head></head><body><span id=\"errinfospan\">" + errMsg + " </span></body></html>";
        return str;
    }

    /**
     * 字符串的解压
     *
     * @param str 对字符串解压
     * @return 返回解压缩后的字符串
     * @throws IOException
     */
    public static String unCompress(String str) throws IOException {
        if (isBlank(str)) {
            return str;
        }

        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(
                str.getBytes("ISO_8859_1"));
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
            out.write(buffer, 0, n);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("UTF-8");
    }

    /**
     * 验证姓名
     *
     * @param context
     * @param name
     * @return true为验证通过，false为输入有误
     */
    public static boolean verifyName(Context context, String name) {
        if (isEmpty(name)) {
            ToastUtils.Toast(context, "请输入真实姓名");
            return false;
        }
        if (hasEnglish(name)) {
            if (name.length() < 2 || name.length() > 20) {
                ToastUtils.Toast(context, "请填写真实姓名（2-20英文）");

                return false;
            } else {
                return true;
            }

        } else if (name.length() < 2 || name.length() > 5 || !isChinese(name)) {
            ToastUtils.Toast(context, "请填写真实姓名（2-20英文）");
            return false;
        }

        return true;
    }

    public static boolean isEmpty(String str) {
        if (null == str || str.trim().equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有英文字母
     *
     * @param s
     * @return
     */
    public static boolean hasEnglish(String s) {
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
                    || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断输入的字符串是否是中文
     *
     * @param strName
     * @return
     */
    public static final boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断输入的字符是否是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;

    }

    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 校验邮箱格式
     *
     * @param context
     * @param email
     */
    public static boolean verifyEmail(Context context, String email) {

        if (!emailFormat(email)) {
//            ToastUtils.Toast(context, R.string.verify_email_format_toast);
            return false;

        }
        if (!containsAny(email)) {
//            ToastUtils.Toast(context, R.string.verify_email_yahoo_toast);
            return false;
        }
        return true;
    }

    /**
     * 是否是正确的邮箱格式
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        // final String pattern1 =
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        final String pattern1 = "^([a-zA-Z0-9_\\-\\.]+)@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    /**
     * 是否包含无效的邮箱后缀
     */
    public static boolean containsAny(String email) {
        String searchChars = "@yahoo.cn";
        String searchCharTwo = "@yahoo.com.cn";
        if (email.contains(searchChars) || email.contains(searchCharTwo)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验密码是否正确
     * 6——18位
     */
    public static boolean verifyPass(Context context, String pass) {
        if (isEmpty(pass) || pass.length() < 6 || pass.length() > 18
                || isChinese(pass)) {
            Toast.makeText(context, R.string.verify_pass_toast,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 校验手机号
     *
     * @param context
     * @param mobile
     * @return
     */
    public static boolean verifyMobileTip(Context context, String mobile) {
        if (isEmpty(mobile)) {
            ToastUtils.Toast(context, "请填写正确的手机号");
            return false;
        }
        if (!isEmpty(mobile) && isEnglish(mobile)) {
            ToastUtils.Toast(context, "请填写正确的手机号");
            return false;
        }
        if (!phoneFormat(mobile)) {
            ToastUtils.Toast(context, "请填写正确的手机号");
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全是英文字母
     *
     * @param s
     * @return
     */
    public static boolean isEnglish(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
                    && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是正确的手机号码格式
     */
    public static boolean phoneFormat(String phone) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return phone != null && phone.length() == 11
                && pattern.matcher(phone).matches();
    }

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return 移动：139、138、137、136、135、134、188、187、184、183、182、178、159、158、157、152、151、150、1391、1301、198
     * 联通：186、185、176、156、155、132、131、130、1860、175、176、166
     * 电信：189、181、180、177、153、133、1890、1330、173、199
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobileNO(String mobiles, String tel) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex;
        if (isBlank(tel)) {
            telRegex = "^((13[0-9]|15[012356789]|16[6]|17[01345678]|18[0-9]|14[579]|19[0-9]))\\d{8}$";
        } else {
            telRegex = "^((" + tel + "))\\d{8}$";
            Log.e(TAG, "isMobileNO: " + tel);
        }
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证手机验证码
     *
     * @param context
     * @param verificationCode
     * @return true为验证通过，false为输入有误
     */
    public static boolean verifyCode(Context context, String verificationCode) {
        Log.d(TAG, "school length : " + verificationCode.length());
        if (isEmpty(verificationCode)) {
            Toast.makeText(context, R.string.re_phone_number,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 四舍五入保留小数点后几位
     *
     * @param index 需要处理的数
     * @param scale 需要小数点后保留的位数
     */
    public static float floatRoundHalfUp(float index, int scale) {
        float roundFloat;
        BigDecimal b = new BigDecimal(index);
        roundFloat = b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
        return roundFloat;
    }

    /**
     * 字符串过滤
     *
     * @param text
     * @return
     */
    public static String filterString(String text) {
        if (isEmpty(text)) {
            return text;
        } else {
            return text.replaceAll(PATTER_IMG, "").replaceAll(PATTER, "");
        }
    }

    /**
     * 字符串过滤
     *
     * @param text
     * @return
     */
    public static String filterHtml(String text) {
        if (isEmpty(text)) {
            return text;
        } else {
            return text.replaceAll(PATTER_HTML, "");
        }
    }

    public static String trim(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static String trim(EditText et) {
        return et.getText().toString().trim();
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     */
    public static String IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            errorInfo = "身份证号码无效";
            return errorInfo;
        } catch (ParseException e) {
            errorInfo = "身份证号码无效";
            return errorInfo;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;

        if (IDStr.length() == 18) {

            if (IDStr.substring(17, 18).equals("X")) {
                Ai = Ai + "X";
            } else {
                String strVerifyCode = ValCodeArr[modValue];
                Ai = Ai + strVerifyCode;
            }
        }


        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 截取返回
     *
     * @param str
     * @return
     */
    public static List<String> getSplit(String str) {
        List<String> list = new ArrayList<>();
        String[] split = str.split(",");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        return list;
    }

    public static String getSplit(String str, int post) {
        List<String> list = new ArrayList<>();
        String[] split = str.split(";");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }

        if (list.size() > post && !isBlank(list.get(post))) {
            return list.get(post);
        }


        return "";
    }

    //uri转成string
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String getFromMediaUri(Context context, ContentResolver resolver, Uri uri) {
        if (uri == null) return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
            if (pfd == null) {
                return null;
            }
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFilename = getTempFilename(context);
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }

            return new File(tempFilename).getAbsolutePath();
        } catch (Exception ignored) {

            ignored.getStackTrace();
        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }

    public static void closeSilently(@Nullable Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 代码中设置margin
     */

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @return boolean
     */

    public static String getLongTime(long time) {
        String str = "";
        time = time / 1000;
        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        int h = (int) (time / 3600);
        str = h + "小时" + m + "分" + s + "秒";
        return str;
    }

    /**
     * 智能格式化 , 不同的显示样子
     */
    public static String friendly_time3(long sdate) {
        String res = "";

      /*  String res = "";
        if (isEmpty(sdate))
            return "";

        Date date = StringUtil.toDate(sdate);
        if (date == null)
            return sdate;*/
        Date date = new Date(sdate);

        SimpleDateFormat format = dateFormater2.get();

        if (isToday(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "上午 hh:mm" : "下午 hh:mm");
            res = format.format(date);
        } else if (isYesterday(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "昨天 上午 hh:mm" : "昨天 下午 hh:mm");
            res = format.format(date);
        } else if (isCurrentYear(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "MM-dd 上午 hh:mm" : "MM-dd 下午 hh:mm");
            res = format.format(date);
        } else {
            format.applyPattern(isMorning(date.getTime()) ? "yyyy-MM-dd 上午 hh:mm" : "yyyy-MM-dd 下午 hh:mm");
            res = format.format(date);
        }
        return res;
    }

    /**
     * @return 判断一个时间是不是今天
     */
    public static boolean isToday(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }

    /**
     * @return 判断一个时间是不是上午
     */
    public static boolean isMorning(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int hour = time.hour;
        return (hour >= 0) && (hour < 12);
    }

    /**
     * @return 判断一个时间是不是
     */
    public static boolean isYesterday(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (time.monthDay - thenMonthDay == 1);
    }

    /**
     * @return 判断一个时间是不是今年
     */
    public static boolean isCurrentYear(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year);
    }

    public static String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sd.format(date);
    }

    public static String getStrTime(String timeStamp) {
        if (StringUtil.isBlank(timeStamp)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    public static String getStrTimeNYR(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List   <ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 是否是整数  如果是整数就显示整数，不然会显示.00
     *
     * @param money
     * @return
     */
    public static long getIsMoney(String money) {
        BigDecimal b1 = new BigDecimal(money);
        System.out.println(b1.intValue());
        return b1.longValue();
    }


    public static String getIsInteger(double money) {
        try {

            int i = String.valueOf(money).indexOf(".");
            if ((money % Integer.parseInt(String.valueOf(money).substring(0, i))) == 0) {
                return Integer.parseInt(String.valueOf(money).substring(0, i)) + "";
            } else {
                return money+"";
            }
        } catch (Exception e) {
            System.out.println(money);
            return money+"'";
        }


    }

    public static String getIsInteger(String money) {
        try {
            double v = Double.parseDouble(money);

            int i = String.valueOf(money).indexOf(".");
            if ((v%1)==0){
                return Integer.parseInt(String.valueOf(money).substring(0, i)) + "";
            }else {
                return v+"";
            }
        } catch (Exception e) {
            System.out.println(money);
            return money;
        }


    }


    /**
     * 获取double数据小数点后两位不进行四舍五入
     *
     * @param value
     * @return -1 , double
     */
    public static String getMoney(double value) {
        DecimalFormat dFormat = new DecimalFormat("#.0000");    //设置小数点后四位
        String money = dFormat.format(value);
        String frist = money.substring(0, 1);
        if (".".equals(frist)) {
            return "0" + money.substring(0, 3);
        }
        int index = getIndex(money, '.');
        if (index == -1) {
            return "0.0";
        }
        return money.substring(0, index + 3);
    }

    /**
     * 获取小数点的位数
     *
     * @param str
     * @param ch
     * @return i , -1
     */
    public static int getIndex(String str, char ch) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == (char) '.') {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断数字是否等于0
     *
     * @param money
     * @return
     */
    public static boolean getJudgmentZero(String money) {
        try {
            double valueOf = Double.valueOf(money);
            if (valueOf == 0) {
                return true;
            } else {
                //  return doubleToString(Double.parseDouble(money));
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
      /*  long v = (long) (num * 100);
        double i = v / 100;
        return i+"";*/
        return new DecimalFormat("0.00").format(num);
    }


    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //String转list
    public static List<String> stringToList(String strs) {

        String str[] = strs.split(",");
        return Arrays.asList(str);
    }

    public static List<String> stringToList2(String strs) {

        String str[] = strs.split(";");
        return Arrays.asList(str);
    }

    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }


    public static String getTimeItem(String time) {
        Date date = new Date();
//        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        SimpleDateFormat sd = new SimpleDateFormat(time);
        return sd.format(date);
    }


    /**
     * 没有秒
     *
     * @param time
     * @return
     */
    public static String getDatewithoutss(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long l = Long.valueOf(time);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }


    /**
     * 月日
     *
     * @param time
     * @return
     */
    public static String getMonthDay(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("MM-dd");
        return sd.format(date);
    }


    public static long parseYMDHMSStrToDate(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_24HOUR_MINUTE_SECOND_TEMPLATE);
        return simpleDateFormat.parse(dateStr).getTime();
    }

    /**
     * 时分
     *
     * @param time
     * @return
     */
    public static String getWhenPoints(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        return sd.format(date);
    }

    public static double convert(double value) {
        long l1 = Math.round(value * 100); //四舍五入
        double ret = l1 / 100.0; //注意：使用 100.0 而不是 100
        return ret;
    }

    public static void setTextAddDrawable(Context context,TextView textView,int image,String name) {
        try {
            SpannableString sp = new SpannableString("   "+name);
            //获取一张图片
            Drawable drawable = context.getDrawable(image);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
            sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
            textView.setText(sp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*
    public static String getTimeStame() {     
             //获取当前的毫秒值          
         long current_time = System.currentTimeMillis();          
        //将毫秒值转换为String类型数据          
         String time_stamp = String.valueOf(time);     
              //返回出去       
            return time_stamp;      */


    //    获取当前时间的时间戳
    public static String getTimeStame() {
        long current_time = System.currentTimeMillis();
        String time_stamp = String.valueOf(current_time);
        return time_stamp;
    }

    public static String getStringMMdd(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        long l = Long.valueOf(time);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    public static String getStringMMddHHmm(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long l = Long.valueOf(time);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

}
