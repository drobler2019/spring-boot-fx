package com.jxf.jasypt.service;

import javafx.scene.control.TextField;

public interface JasyptService {
    String encrypt(TextField secretKey, TextField value);
    String decrypt(TextField secretKey, TextField value);
}
