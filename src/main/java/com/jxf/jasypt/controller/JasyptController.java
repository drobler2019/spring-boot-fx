package com.jxf.jasypt.controller;

import com.jxf.jasypt.service.JasyptService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import static com.jxf.jasypt.constants.JasyptUtil.validValueFiled;

@Component
public class JasyptController {

    private final JasyptService jasyptService;

    @FXML
    private TextField llaveSecreta;

    @FXML
    private TextField valor;

    @FXML
    private TextField resultado;

    @FXML
    private Label mensajeError;

    public JasyptController(JasyptService jasyptService) {
        this.jasyptService = jasyptService;
    }

    public void encryptText() {
        try {
            resultado.setText(jasyptService.encrypt(llaveSecreta, valor));
            if(!validValueFiled(mensajeError.getText())) {
                mensajeError.setText(null);
            }
        } catch (RuntimeException e) {
            mensajeError.setText(e.getMessage());
        }

    }

    public void decryptText() {
        try {
            resultado.setText(jasyptService.decrypt(llaveSecreta,valor));
            if(!validValueFiled(mensajeError.getText())) {
                mensajeError.setText(null);
            }
        } catch (RuntimeException e) {
            mensajeError.setText(e.getMessage());
        }

    }
}
