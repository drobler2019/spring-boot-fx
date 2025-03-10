package com.jxf.jasypt.constants;

public class JasyptUtil {

    public static final String ALGORITHM = "PBEWITHHMACSHA512ANDAES_256";
    public static final String KEY_OBTENTION_INTERATIONS = "1000";
    public static final String POOL_SIZE_VALUE = "1";
    public static final String SALT_GENERATOR_CLASS_NAME = "org.jasypt.salt.RandomSaltGenerator";
    public static final String GENERATOR_CLASS_NAME = "org.jasypt.iv.RandomIvGenerator";
    public static final String OUT_PUT_TYPE = "org.jasypt.iv.RandomIvGenerator";
    public static final String FORMAT_JASYPT = "ENC(%s)";
    public static final String ERROR_MESSAGE_ASCII = "El valor de la *llave Secreta* no es ASCII";
    public static final String ERROR_MESSAGE_KEY_SECRET_OR_VALUE = "*Llave Secreta* diferente o el valor del campo *resultado* con formato incorrecto";
    public static final String ERROR_MESSAGE_FIELD_VALUE = "Campo *resultado* no tiene valor para desencriptar";

    public static boolean validValueFiled(String value) {
        return value == null || value.isBlank();
    }

}
