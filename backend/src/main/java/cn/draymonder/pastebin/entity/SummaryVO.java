package cn.draymonder.pastebin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description: 统计信息view object
 * @Date 2020/06/23 13:26
 * @auther Draymonder
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "summary")
public class SummaryVO {
  @Id
  String id;

  String language;

  int count;

  int percent;
}
