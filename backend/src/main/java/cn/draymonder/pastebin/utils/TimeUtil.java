package cn.draymonder.pastebin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Date 2020/06/18 17:57
 * @auther Draymonder
 */
public class TimeUtil {
  public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static String timestamp2String(long timestamp) {
    return sdf.format(new Date(timestamp));
  }
}
