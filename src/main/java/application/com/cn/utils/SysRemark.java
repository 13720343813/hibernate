package application.com.cn.utils;

import org.apache.commons.lang.StringUtils;

public class SysRemark {
    public static String startFix = "【";
    public static String endFix = "】";
    public static String newLine = "<br/>";

    private static String _fix(String remark) {
        return startFix + remark + endFix;
    }

    public static String append(String existsRemark, String newRemark) {
        int rows = StringUtils.countMatches(existsRemark, startFix);
        String newIndex = (new Integer(rows + 1)).toString() + ": ";
        if (existsRemark != null && !existsRemark.isEmpty()) {
            existsRemark = existsRemark + newLine;
        } else {
            existsRemark = "";
        }

        return existsRemark + newIndex + _fix(newRemark);
    }

}
