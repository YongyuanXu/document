package com.example.text.exception;

public enum TextExceptionEnum {

    GPT_RECORDS_EXCEPTION("400001", "GPT RECORDS SHOULD NOT BE EMPTY"),
    GPT_PARAMS_EXCEPTION("400002", "GPT PARAMS EXCEPTION");

    private final String code;
    private final String msg;

    TextExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
