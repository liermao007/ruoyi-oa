package com.thinkgem.jeesite.common;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author oa
 */
public class Result<T> {

    private int code;

    private T result;

    private String message;

    public Result(int code, T result, String message) {
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public static Result success() {
        return new Result(200, "", "");
    }

    public static <T> Result success(T result) {
        return new Result(200, result, "");
    }

    public static Result error() {
        return new Result(500, "", "");
    }

    public static Result error(String msg) {
        return new Result(500, "", msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
