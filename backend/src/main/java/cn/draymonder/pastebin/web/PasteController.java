package cn.draymonder.pastebin.web;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import cn.draymonder.pastebin.entity.PasteEntity;
import cn.draymonder.pastebin.entity.PasteVO;
import cn.draymonder.pastebin.entity.ResponseEntity;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 粘贴代码控制层
 * @Date 2020/06/18 14:40
 * @auther Draymonder
 */
@RestController
@CrossOrigin
@Slf4j
public class PasteController {

  @Autowired
  private MongoTemplate mongoTemplate;

  public static String getShortUrl() {
    String uuid = UUID.randomUUID().toString();
    uuid = uuid.replace("-", "");
    uuid = uuid.substring(0, 8);
    return uuid;
  }

  public static long transExpireTime(String expireTime) {
    if (expireTime == null) {
      return 0;
    }
    return Long.parseLong(expireTime);
  }

  @PostMapping("/")
  public ResponseEntity<Map<String, String>> createPaste(
      @RequestParam("poster") String poster,
      @RequestParam(value = "language", required = false) String language,
      @RequestParam(value = "expireTime", required = false) String expireTime,
      @RequestParam(value = "content", defaultValue = "") String content
  ) {
    log.info("参数 poster: {}, language: {}, expireTime: {}", poster, language, expireTime);
    String shortUrl = getShortUrl();
    if (null == language) {
      language = "text";
    }
    long transExpireTime = transExpireTime(expireTime);
    PasteEntity pasteEntity = PasteEntity.builder()
        .poster(poster)
        .language(language)
        .createTime(System.currentTimeMillis())
        .expireTimeSecond(transExpireTime)
        .shortUrl(shortUrl)
        .content(content)
        .build();
    mongoTemplate.save(pasteEntity);
    log.info("生成新的shortUrl: {}", shortUrl);
    return ResponseEntity.ok("生成成功", Map.of("shortUrl", shortUrl));
  }

  @GetMapping("/p/{shortUrl}")
  public ResponseEntity<PasteVO> getPaste(@PathVariable("shortUrl") String shortUrl) {
    PasteEntity pasteEntity = mongoTemplate
        .findOne(Query.query(where("shortUrl").is(shortUrl)), PasteEntity.class);
    if (Objects.nonNull(pasteEntity)) {
      log.info("访问shortUrl: {}", shortUrl);
      return ResponseEntity.ok("获取成功", PasteVO.converPastetVO(pasteEntity));
    } else {
      return ResponseEntity.error("获取失败", null);
    }
  }
}
