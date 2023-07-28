package com.example.lab6;

public class Constants {
    // SOAP_ACTION: url của web service
    public final static String SOAP_ACTION = "http://www.w3schools.com/xml/";
    // NAME_SPACE: tên không gian của web service
    public final static String NAME_SPACE = "http://www.w3schools.com/xml/";
    // URL: địa chỉ url của web service
    public final static String URL = "http://www.w3schools.com/xml/tempconvert.asmx?WSDL";
    // F_TO_C_METHOD_NAME: tên phương thức đổi F sang C


    public final static String F_TO_C_METHOD_NAME = "FahrenheitToCelsius";
    // C_TO_F_METHOD_NAME: tên phương thức đổi C sang F
    public final static String C_TO_F_METHOD_NAME = "CelsiusToFahrenheit";
    // F_TO_C_SOAP_ACTION: hành động soap tương ứng với phương thức F sang C

    public final static String F_TO_C_SOAP_ACTION = SOAP_ACTION + F_TO_C_METHOD_NAME;
    // C_TO_F_SOAP_ACTION: hành động soap tương ứng với phương thức C sang F
    public final static String C_TO_F_SOAP_ACTION = SOAP_ACTION + C_TO_F_METHOD_NAME;


}









