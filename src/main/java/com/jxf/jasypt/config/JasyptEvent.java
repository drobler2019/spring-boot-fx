package com.jxf.jasypt.config;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class JasyptEvent extends ApplicationEvent {

    public JasyptEvent(Object source) {
        super(source);
    }

    public Stage getStage() {
        return (Stage) this.getSource();
    }

}
