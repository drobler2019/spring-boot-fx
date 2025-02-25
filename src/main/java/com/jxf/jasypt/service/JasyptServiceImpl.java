package com.jxf.jasypt.service;

import javafx.scene.control.TextField;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jxf.jasypt.constants.JasyptUtil.FORMAT_JASYPT;

@Service
public class JasyptServiceImpl implements JasyptService {

    private final PooledPBEStringEncryptor stringEncryptor;

    public JasyptServiceImpl(@Qualifier("encryptor") PooledPBEStringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    @Override
    public String encrypt(TextField secretKey, TextField value) {
        var keyText = secretKey.getText();
        var valueText = value.getText();
        if (valid(keyText) || valid(valueText)) {
            throw new IllegalArgumentException("valor no valido");
        }
        stringEncryptor.setPassword(keyText);
        return String.format(FORMAT_JASYPT, stringEncryptor.encrypt(valueText));
    }

    @Override
    public String decrypt(TextField value) {
        String valueText = value.getText();
        if (valid(valueText)) {
            throw new IllegalArgumentException("valor no valido");
        }
        return stringEncryptor.decrypt(valueText);
    }

    private boolean valid(String value) {
        return value == null || value.isBlank();
    }

}
