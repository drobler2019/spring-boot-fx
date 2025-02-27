package com.jxf.jasypt.service;

import javafx.scene.control.TextField;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.jxf.jasypt.constants.JasyptUtil.*;

@Service
public class JasyptServiceImpl implements JasyptService {


    private static final String VALUE_ID_FIELD_RESULTADO = "resultado";
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
        try {
            return String.format(FORMAT_JASYPT, stringEncryptor.encrypt(valueText));
        } catch (EncryptionInitializationException e) {
            throw new EncryptionOperationNotPossibleException(ERROR_MESSAGE_ASCII);
        }

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
        } catch (EncryptionOperationNotPossibleException | StringIndexOutOfBoundsException e) {
            throw new EncryptionOperationNotPossibleException(ERROR_MESSAGE_KEY_SECRET_OR_VALUE);
        } catch (EncryptionInitializationException e) {
            throw new EncryptionOperationNotPossibleException(ERROR_MESSAGE_ASCII);
        }
    }

    private void validateValues(TextField... values) {
        var keyText = values[0];
        var valueText = values[1];
        if (validValueFiled(keyText.getText())) {
            throw new IllegalArgumentException(message(keyText));
        }
        if (validValueFiled(valueText.getText())) {
            if(valueText.getId().equals(VALUE_ID_FIELD_RESULTADO)) {
                throw new IllegalArgumentException(ERROR_MESSAGE_FIELD_VALUE);
            }
            throw new IllegalArgumentException(message(valueText));
        }
    }

    private String message(TextField keyText) {
        return new StringBuilder().append("Campo *")
                .append(keyText.getId())
                .append("* obligatorio").toString();
    }


}
