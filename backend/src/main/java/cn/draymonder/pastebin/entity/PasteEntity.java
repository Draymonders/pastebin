package cn.draymonder.pastebin.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Description: 实体
 * @Date 2020/06/18 10:58
 * @auther Draymonder
 */
@Data
@Builder
public class PasteEntity {

  /**
   * id
   */
  @Id
  String id;

  /**
   * 短url地址
   */

  String shortUrl;

  /**
   * 提交者
   */
  String poster;

  /**
   * 存放时间
   */
  long expireTimeSecond;

  /**
   * 创建时间
   */
  long createTime;

  /**
   * 提交所使用的语言
   */
  String language;

  /**
   * 文本内容
   */
  String content;
}
