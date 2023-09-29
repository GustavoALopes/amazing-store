package com.developerjorney.configurations;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

@Configuration
public class RequestScopeCDI {

    @Bean
    public CustomScopeConfigurer requestScopeForTest() {
        final var scope = new CustomScopeConfigurer();
        final var config = new HashMap<String, Object>();

        config.put(WebApplicationContext.SCOPE_REQUEST,  new org.springframework.web.context.request.RequestScope());
        scope.setScopes(config);

        return scope;
    }
}
