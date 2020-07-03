package cn.draymonder.pastebin.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description: 日志log切面
 * @Date 2020/07/03 12:38
 * @auther Draymonder
 */
@Aspect
@Component
@Order(100)
@Slf4j
public class WebLogAspect {

  @Pointcut("within(cn.draymonder.pastebin.web..*)")
  public void webLog() {
  }

  @Pointcut(value = "execution (* cn.draymonder.pastebin.config.CronJobs.*())")
  public void cronJob() {
  }

  @Before(value = "webLog()")
  public void doBefore(JoinPoint joinPoint) {
    // 获取请求
    // HttpServletRequest httpServletRequest =
    //    ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    String method = joinPoint.getSignature().getName();
    String clazz = joinPoint.getTarget().getClass().getName();
    log.info("访问方法 {}.{}", clazz, method);

  }

  /**
   * 对定时任务进行时间的统计
   */
  @Around(value = "cronJob()")
  public void summaryTime(ProceedingJoinPoint joinPoint) {
    long start = System.currentTimeMillis();
    String clazz = joinPoint.getTarget().getClass().getName();
    String method = joinPoint.getSignature().getName();
    try {
      joinPoint.proceed();
    } catch (Throwable throwable) {
      log.error("{}.{} 执行异常 {}", clazz, method, throwable.getMessage());
    }
    long end = System.currentTimeMillis();
    log.info("{}.{} 执行时间 {}ms", clazz, method, end - start);
  }

}
