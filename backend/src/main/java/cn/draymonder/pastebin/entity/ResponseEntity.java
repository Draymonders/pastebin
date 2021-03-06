package cn.draymonder.pastebin.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 返回实体
 * @Date 2020/06/18 14:42
 * @auther Draymonder
 */
@Data
public class ResponseEntity<T> {

  public static final int SUCCESS_CODE = 0;
  public static final int ERROR_CODE = -1;

  int code;

  String message;

  T data;

  public ResponseEntity() {
  }

  public ResponseEntity(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> ResponseEntity<T> ok() {
    return new ResponseEntity<T>(SUCCESS_CODE, "", null);
  }

  public static <T> ResponseEntity<T> ok(T data) {
    return new ResponseEntity<T>(SUCCESS_CODE, "", data);
  }

  public static <T> ResponseEntity<T> ok(String message, T data) {
    return new ResponseEntity<>(SUCCESS_CODE, message, data);
  }

  public static <T> ResponseEntity<T> error() {
    return new ResponseEntity<T>(ERROR_CODE, "", null);
  }

  public static <T> ResponseEntity<T> error(T data) {
    return new ResponseEntity<T>(ERROR_CODE, "", data);
  }

  public static <T> ResponseEntity<T> error(String message, T data) {
    return new ResponseEntity<>(ERROR_CODE, message, data);
  }
}
