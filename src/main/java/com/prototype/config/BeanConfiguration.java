package com.prototype.config;

import com.prototype.common.constants.ApplicationConstant;
import com.prototype.interceptor.GlobalExceptionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 所有有关springmvc 的配置只需要继承WebMvcConfigurerAdapter重写里面的方法即可
 * <p/>
 * 1、覆盖spring boot默认生成的bean @Bean
 * 2、增加拦截器  addInterceptors
 * 3、添加参数解析器 addArgumentResolvers
 * 4、配置路径映射  configurePathMatch
 * 5、参数格式化工具（用于接收参数） addFormatters
 * 6、配置消息转换器(用于@RequestBody和@ResponseBody) configureMessageConverters
 */
@Configuration
public class BeanConfiguration extends WebMvcConfigurerAdapter {

    @Autowired(required = false)
    private ServerProperties properties;

    @Autowired(required = false)
    private ErrorAttributes errorAttributes;

    /**
     * 替换spring boot默认生成的messageSource
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(ApplicationConstant.I18N_MSG, ApplicationConstant.I18N_CODE);
        messageSource.setDefaultEncoding(ApplicationConstant.I18N_ENCODIND);
        return messageSource;
    }


    /**
     * 替换默认的DefaultAnnotationHandlerMapping
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }



    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
    public BasicErrorController basicErrorController() {
        if (properties == null) {
            properties = new ServerProperties();
        }
        if (errorAttributes == null) {
            errorAttributes = new DefaultErrorAttributes();
        }
        return new GlobalExceptionController(errorAttributes, this.properties.getError());
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(ApplicationConstant.I18N_VALIDATION, ApplicationConstant.I18N_CODE);
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

}
