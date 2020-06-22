package cn.draymonder.pastebin.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import cn.draymonder.pastebin.entity.PasteEntity;
import cn.draymonder.pastebin.entity.PasteVO;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @Description: 测试 mongoTemplate
 * @Date 2020/06/18 11:19
 * @auther Draymonder
 */
@SpringBootTest
public class TestPasteDao {

  @Autowired
  MongoTemplate mongoTemplate;

  @Test
  void testCreateEntity() {
    PasteEntity pasteEntity = PasteEntity.builder().poster("draymonder")
        .shortUrl("xxxx")
        .content("#include <bits/stdc++.h>")
        .createTime(System.currentTimeMillis())
        .expireTimeSecond(60L)
        .language("c++")
        .build();
    PasteEntity alreadyPaste = mongoTemplate.insert(pasteEntity);

    System.out.println(alreadyPaste);

    pasteEntity = mongoTemplate
        .findOne(Query.query(where("shortUrl").is("xxxx")), PasteEntity.class);
    assert pasteEntity != null;
  }

  @Test
  void testSearch() {
    PasteEntity pasteEntity = mongoTemplate
        .findOne(Query.query(where("shortUrl").is("yyy")), PasteEntity.class);
    assert pasteEntity == null;
  }

  @Test
  void testDelete() {
    PasteEntity pasteEntity = mongoTemplate
        .findOne(Query.query(where("shortUrl").is("xxxx")), PasteEntity.class);
    DeleteResult deleteResult = mongoTemplate.remove(pasteEntity);
    assert deleteResult.getDeletedCount() > 0;
  }
}

