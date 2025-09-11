package com.hospitalRegistration.common;

public class Result {
    private int code;
    private String message;
    private Object data;

    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Object data) {
        this.data = data;
    }
    private Result() {}
    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }


    // getter 方法
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static int successCode(){
        return 200;
    }

    public static int errorCode(){
        return 500;
    }

    public static Result successWithUserId(Object data) {
        Result r = new Result();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        return r;
    }

    public static Result fail(String message) {
        return new Result(500, message, null);
    }
}