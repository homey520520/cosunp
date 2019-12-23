package com.cosun.cosunp.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author:homey Wong
 * @date:2018/12/28  下午 4:50
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Configuration
public class UserSecurityInterceptor extends WebMvcConfigurerAdapter {
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/weixin/punchClock");
        addInterceptor.excludePathPatterns("/account/**");
        addInterceptor.addPathPatterns("/account/toMainPage");
        addInterceptor.addPathPatterns("/order/createsinglegoods");
        addInterceptor.addPathPatterns("/fileupdown/**");
        addInterceptor.addPathPatterns("/person/**");
        addInterceptor.addPathPatterns("/rules/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();
            int interval = session.getMaxInactiveInterval();
            if (session.getAttribute("account") != null) {
                return true;
            }
            String url = "/account/tologin";
            response.sendRedirect(url);
            return false;
            // return true;
        }
    }

}

