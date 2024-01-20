package com.projects.microsensors.configuration;

import com.projects.microsensors.common.AppConstraints;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", AppConstraints.Path.MAIN_PAGE);
        registry.addViewController(AppConstraints.ExtendedPath.DASHBOARD_PAGE).setViewName(AppConstraints.ViewName.DASHBOARD);
        registry.addViewController(AppConstraints.ExtendedPath.MAIN_PAGE).setViewName(AppConstraints.ViewName.MAIN);
        registry.addViewController(AppConstraints.ExtendedPath.SIGN_UP_PAGE).setViewName(AppConstraints.ViewName.SIGN_UP);
        registry.addViewController(AppConstraints.ExtendedPath.LOG_IN_PAGE).setViewName(AppConstraints.ViewName.LOG_IN);
        registry.addViewController(AppConstraints.ExtendedPath.TERMS_OF_SERVICE).setViewName(AppConstraints.ViewName.TERMS_OF_SERVICE);
        registry.addViewController(AppConstraints.ExtendedPath.PRIVACY_POLICY).setViewName(AppConstraints.ViewName.PRIVACY_POLICY);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(AppConstraints.ExtendedPath.DASHBOARD_PAGE).allowedOrigins("http://localhost:8080");
    }
}
