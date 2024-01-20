package com.projects.microsensors.controller.sensor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainPageController {
    @GetMapping
    public String getMainPage() {
        return "main-page";
    }

    @GetMapping("/terms")
    public String getTermsOfService() {
        return "terms";
    }

    @GetMapping("/privacy")
    public String getPrivacyPolicy() {
        return "privacy";
    }
}
