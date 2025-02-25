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

    @Value("classpath:/index.fxml")
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
                var url = resource.getURL();
                var loader = new FXMLLoader(url);
                loader.setControllerFactory(applicationContext::getBean);
                Parent root = loader.load();
                var stage = event.getStage();
                stage.setTitle(applicationTitle);
                var scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

}
