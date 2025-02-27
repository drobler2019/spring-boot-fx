package com.jxf.jasypt.config;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JasyptInitializr implements ApplicationListener<JasyptEvent> {

    private static final double WIDTH = 1200.0;
    private static final double HEIGHT = 700.0;

    @Value("classpath:/index.fxml")
    private Resource resource;
    @Value("classpath:/icon/spring.png")
    private Resource image;
    @Value("classpath:/index.css")
    private Resource stylesSheet;
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
            Image img;
            try {
                try (var stream = image.getInputStream()) {
                    img = new Image(stream);
                }
                var url = resource.getURL();
                var loader = new FXMLLoader(url);
                loader.setControllerFactory(applicationContext::getBean);
                Pane root = loader.load();
                var stage = event.getStage();
                stage.getIcons().add(img);
                stage.setTitle(applicationTitle);
                root.setMinSize(WIDTH, HEIGHT);
                root.setMaxSize(WIDTH,HEIGHT);
                root.setPrefSize(WIDTH,HEIGHT);
                var scene = new Scene(root);
                scene.getStylesheets().add(stylesSheet.getURL().toExternalForm());
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

}
