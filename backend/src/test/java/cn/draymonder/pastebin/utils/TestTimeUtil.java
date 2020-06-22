package cn.draymonder.pastebin.utils;

import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Date 2020/06/18 17:58
 * @auther Draymonder
 */
public class TestTimeUtil {

  @Test
  void testTimeStampFormat() {
    String now = TimeUtil.timestamp2String(System.currentTimeMillis());
    System.out.println(now);
  }
}
