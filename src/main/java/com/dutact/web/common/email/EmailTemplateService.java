package com.dutact.web.common.email;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.lang.module.Configuration;
import java.util.Map;

@Service
public class EmailTemplateService {

    private final TemplateEngine templateEngine;

    public EmailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

//    @Bean
//    public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
//        TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/mail-templates");
//        configuration.setTemplateLoader(templateLoader);
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setConfiguration(configuration);
//        return freeMarkerConfigurer;
//    }
    public String generateEmailContent(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        System.out.println("templateName: " + templateName);
        System.out.println("variables: " + variables);
        context.setVariables(variables);
        System.out.println("context: " + context);
        System.out.println("content: " + templateEngine.process(templateName, context));
        return templateEngine.process(templateName, context);
    }
}
