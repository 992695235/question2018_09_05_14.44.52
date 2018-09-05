package com.itmuch.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
  public static void main(String[] args) {
    SpringApplication.run(ZuulApplication.class, args);
  }
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver() {
      @Override
      public boolean isMultipart(HttpServletRequest request) {
        String method = request.getMethod().toLowerCase();
        if (!Arrays.asList("put", "post").contains(method)) {
          return false;
        }
        String contentType = request.getContentType();
        return (contentType != null &&contentType.toLowerCase().startsWith("multipart/"));
      }
    };
  }
}
