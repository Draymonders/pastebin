package cn.draymonder.pastebin.web;

import static java.util.stream.Collectors.groupingBy;

import cn.draymonder.pastebin.entity.PasteEntity;
import cn.draymonder.pastebin.entity.SummaryVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 页面统计控制岑
 * @Date 2020/06/23 13:11
 * @auther Draymonder
 */
@RestController
@Slf4j
@CrossOrigin
public class SummaryController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @GetMapping("/summary")
  public List<SummaryVO> summaryLanguage() {
    return mongoTemplate.findAll(SummaryVO.class);
  }
}
