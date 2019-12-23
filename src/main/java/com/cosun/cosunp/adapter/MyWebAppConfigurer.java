package com.cosun.cosunp.adapter;

import com.cosun.cosunp.interceptor.UserSecurityInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.Configuration;

/**
 * @author:homey Wong
 * @date:2019/1/2  下午 3:22
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Configuration
public class MyWebAppConfigurer extends  WebMvcConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(UserSecurityInterceptor.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { logger.info("addResourceHandlers");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
