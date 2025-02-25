package com.jxf.jasypt.controller;

import com.jxf.jasypt.service.JasyptService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class JasyptController {

    private final JasyptService jasyptService;

    @FXML
    private TextField secretKey;

    @FXML
    private TextField value;

    @FXML
    private TextField result;

    public JasyptController(JasyptService jasyptService) {
        this.jasyptService = jasyptService;
    }

    public void encryptText() {
        result.setText(jasyptService.encrypt(secretKey, value));
    }

    public void decryptText() {
        result.setText(jasyptService.decrypt(value));
    }
}
