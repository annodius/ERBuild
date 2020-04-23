package ru.aora.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aora.erp.component.CoreModuleIdentifier;
import ru.aora.erp.component.ServiceLoggingAspect;

@Configuration
@ComponentScan("ru.aora.erp.service")
@EnableAspectJAutoProxy
public class ServiceConfig {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CoreModuleIdentifier coreModuleIdentifier() {
        return new CoreModuleIdentifier();
    }

    @Bean
    public ServiceLoggingAspect serviceLoggingAspect() {
        return new ServiceLoggingAspect();
    }

//    @Bean //todo: используем или нет???
//    public CommonsRequestLoggingFilter logFilter() {
//        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setIncludeQueryString(true);
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(true);
//        filter.setMaxPayloadLength(10000);
//        filter.setIncludeHeaders(false);
//        filter.setAfterMessagePrefix("RQ Data : ");
//        return filter;
//    }
}
