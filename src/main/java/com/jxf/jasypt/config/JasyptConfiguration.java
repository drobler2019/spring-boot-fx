package com.jxf.jasypt.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.jxf.jasypt.constants.JasyptUtil.*;

@Configuration
public class JasyptConfiguration {

    @Bean("encryptor")
    public PooledPBEStringEncryptor encryptor() {
        var encryptor = new PooledPBEStringEncryptor();
        var config = new SimpleStringPBEConfig();
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(KEY_OBTENTION_INTERATIONS);
        config.setPoolSize(POOL_SIZE_VALUE);
        config.setSaltGeneratorClassName(SALT_GENERATOR_CLASS_NAME);
        config.setIvGeneratorClassName(GENERATOR_CLASS_NAME);
        config.setStringOutputType(OUT_PUT_TYPE);
        encryptor.setConfig(config);
        return encryptor;
    }

}
