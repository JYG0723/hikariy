package nuc.jyg.hikariy.config;

import nuc.jyg.hikariy.interceptor.LoginRequiredInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Ji YongGuang.
 * @date 23:31 2019-04-28.
 * @description
 */
@Component
public class HikariWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/", "");
        super.addInterceptors(registry);
    }
}
