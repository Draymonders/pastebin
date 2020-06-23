package cn.draymonder.pastebin.config;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import cn.draymonder.pastebin.entity.PasteEntity;
import cn.draymonder.pastebin.entity.SummaryVO;
import cn.draymonder.pastebin.utils.TimeUtil;
import com.mongodb.client.result.DeleteResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ExecutableRemoveOperation.ExecutableRemove;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时任务
 * @Date 2020/06/23 08:01
 * @auther Draymonder
 */
@Component
@Slf4j
public class CronJobs {
  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * 定时时间设定为2小时
   */
  private static final long ONE_HOUR = 60 * 60 * 1000;

  /**
   * 定时删除掉过期的文件
   */
  @Scheduled(fixedRate = 2 * ONE_HOUR)
  public void removeExpiredFile() {

    // 找到所有的定时数据
    List<Integer> notChooseCondition = List.of(0);
    Query allCronCondition = Query.query(where("expireTimeSecond").nin(notChooseCondition));
    List<PasteEntity> allCronPasteEntity = mongoTemplate.find(allCronCondition, PasteEntity.class);

    // 找到需要删除的数据
    List<PasteEntity> needRemoveEntity = new ArrayList<>();
    allCronPasteEntity.forEach(cronPasteEntity -> {
      long createTime = cronPasteEntity.getCreateTime();
      long expireTime = cronPasteEntity.getExpireTimeSecond();
      long currentTime = System.currentTimeMillis();
      if (createTime + expireTime <= currentTime)
        needRemoveEntity.add(cronPasteEntity);
    });

    // 将过期定时数据进行删除
    List<String> needRemoveIds = needRemoveEntity.stream().map(PasteEntity::getId).collect(
        Collectors.toList());
    Query needRemoveQuery = Query.query(where("_id").in(needRemoveIds));
    DeleteResult removeResult = mongoTemplate.remove(needRemoveQuery, PasteEntity.class);

    log.info("定时删除任务执行于 {}, 共删除{}个过期文件", TimeUtil.timestamp2String(System.currentTimeMillis()),
        removeResult.getDeletedCount());
  }

  /**
   * 定时统计不同语言的占用情况
   */
  @Scheduled(fixedRate = ONE_HOUR)
  public void cronLanguageSummary() {
    mongoTemplate.dropCollection(SummaryVO.class);

    List<PasteEntity> allEntity = mongoTemplate.findAll(PasteEntity.class);
    List<SummaryVO> summaryVOList = new ArrayList<>();
    Map<String, List<PasteEntity>> languageMap = allEntity.stream()
        .collect(groupingBy(PasteEntity::getLanguage));
    int sum = allEntity.size();
    languageMap.forEach((language, entities) -> {
      int count = entities.size();
      int percent = (count * 100) / sum;
      summaryVOList
          .add(SummaryVO.builder().language(language).count(count).percent(percent).build());
    });
    mongoTemplate.insertAll(summaryVOList);
    log.info("定时统计任务执行于 {}", TimeUtil.timestamp2String(System.currentTimeMillis()));
  }
}
