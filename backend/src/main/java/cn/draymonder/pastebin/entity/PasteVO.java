package cn.draymonder.pastebin.entity;

import cn.draymonder.pastebin.utils.TimeUtil;
import java.text.SimpleDateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Date 2020/06/18 15:01
 * @auther Draymonder
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasteVO {

  /**
   * 创建时间
   */
  String pasteDate;
  /**
   * 过期时间
   */
  String expireDate;


  String content;

  String poster;

  String language;

  public static PasteVO converPastetVO(PasteEntity pasteEntity) {
    long pasteTimeSecond = pasteEntity.getCreateTime();
    long expireTimeSecond = 0;
    if (pasteEntity.getExpireTimeSecond() != 0) {
      expireTimeSecond = pasteTimeSecond + pasteEntity.getExpireTimeSecond();
    }
    String pasteDate = TimeUtil.timestamp2String(pasteTimeSecond);
    String expirtDate = (expireTimeSecond == 0) ? "" : TimeUtil.timestamp2String(expireTimeSecond);

    return PasteVO.builder()
        .poster(pasteEntity.getPoster())
        .language(pasteEntity.getLanguage())
        .content(pasteEntity.getContent())
        .pasteDate(pasteDate)
        .expireDate(expirtDate)
        .build();
  }
}
