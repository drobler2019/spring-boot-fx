package com.jxf.jasypt.service;

import javafx.scene.control.TextField;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jxf.jasypt.constants.JasyptUtil.FORMAT_JASYPT;
import static com.jxf.jasypt.constants.JasyptUtil.validValueFiled;

@Service
public class JasyptServiceImpl implements JasyptService {

    private final ObjectFactory<PooledPBEStringEncryptor> objectFactory;

    public JasyptServiceImpl(@Qualifier("encryptor") ObjectFactory<PooledPBEStringEncryptor> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public String encrypt(TextField secretKey, TextField value) {
            validateValues(secretKey, value);
            var keyText = secretKey.getText();
            var valueText = value.getText();
            var stringEncryptor = objectFactory.getObject();
            stringEncryptor.setPassword(keyText);
            return String.format(FORMAT_JASYPT, stringEncryptor.encrypt(valueText));
    }

    @Override
    public String decrypt(TextField secretKey, TextField encryptedValue) {
        try {
            validateValues(secretKey, encryptedValue);
            var keyText = secretKey.getText();
            var valueText = encryptedValue.getText().substring(4, encryptedValue.getText().length() - 1);
            var stringEncryptor = objectFactory.getObject();
            stringEncryptor.setPassword(keyText);
            return stringEncryptor.decrypt(valueText);
        } catch (EncryptionOperationNotPossibleException e) {
            throw new EncryptionOperationNotPossibleException("Error: Llave secreta diferente o valor con formato incorrecto");
        }
    }

    private void validateValues(TextField... values) {
        var keyText = values[0];
        var valueText = values[1];
        if (validValueFiled(keyText.getText())) {
            throw new IllegalArgumentException(message(keyText));
        }
        if (validValueFiled(valueText.getText())) {
            throw new IllegalArgumentException(message(valueText));
        }
    }

    private String message(TextField keyText) {
        return new StringBuilder().append("campo \"").append(keyText.getId()).append("\" obligatorio").toString();
    }


}
