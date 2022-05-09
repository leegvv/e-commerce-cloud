package net.arver.commerce.conf;

import net.arver.commerce.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * WebMvcConfig.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginUserInfoInterceptor())
                .addPathPatterns("/**").order(0);
    }

    @Override
    protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui/**")
                .addResourceLocations("classpath:/META_INF/resources/");

        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META_INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META_INF/resources/webjars");

        super.addResourceHandlers(registry);
    }
}
