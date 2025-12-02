package com.duli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;

/**
 * @author luoyi-
 */
@Configuration
@SpringBootApplication
@MapperScan(basePackages = {"com.duli.*.mapper"})
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        /**
         * 单个数据大小
         */
        factory.setMaxFileSize(DataSize.parse("102400KB"));
        /**
         * 总上传数据大小
         */
        factory.setMaxRequestSize(DataSize.parse("102400KB"));
        return factory.createMultipartConfig();
    }

    // 新增：服务器启动完成后打印可点击访问链接
    @Bean
    public ApplicationListener<ServletWebServerInitializedEvent> webServerInitializedListener() {
        return event -> {
            // 获取启动端口
            int port = event.getWebServer().getPort();
            // 获取项目上下文路径（你的项目是根路径，即""）
            ServletContext servletContext = event.getApplicationContext().getServletContext();
            String contextPath = servletContext == null ? "/" : servletContext.getContextPath();
            if (contextPath.isEmpty()) {
                contextPath = "/";
            }
            // 拼接基础链接和Swagger文档链接
            String homeUrl = "http://localhost:" + port + contextPath;
            String swaggerUrl = homeUrl + "swagger-ui.html"; // 你的项目集成了Swagger，可直接访问

            // 打印带格式的链接（蓝色下划线，IDEA控制台可直接点击）
            System.out.println("\n==========================================================");
            System.out.println("✅ 仓库管理系统启动成功！");
            System.out.println("📌 首页访问：\033[4;34m" + homeUrl + "\033[0m");
            System.out.println("📋 API文档：\033[4;34m" + swaggerUrl + "\033[0m");
            System.out.println("==========================================================\n");
        };
    }

}