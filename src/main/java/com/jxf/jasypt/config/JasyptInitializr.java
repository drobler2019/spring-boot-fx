package com.jxf.jasypt.config;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JasyptInitializr implements ApplicationListener<JasyptEvent> {


    @Value("classpath:/jasypt.fxml")
    private Resource resource;
    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public JasyptInitializr(@Value("${spring.application.name}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(JasyptEvent event) {
        Platform.runLater(() -> {
            try {
                var loader = new FXMLLoader(this.resource.getURL());
                loader.setControllerFactory(this.applicationContext::getBean);
                var parent = (Parent) loader.load();
                var stage = event.getStage();
                stage.setTitle(this.applicationTitle);
                stage.setScene(new Scene(parent,800,600));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

}
