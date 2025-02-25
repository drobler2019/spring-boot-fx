package com.jxf.jasypt;

import com.jxf.jasypt.config.JasyptEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage) {
        var jasyptEvent = new JasyptEvent(stage);
        context.publishEvent(jasyptEvent);
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    @Override
    public void init() {
        context = new SpringApplicationBuilder(JasyptApplication.class).run();
    }

}
