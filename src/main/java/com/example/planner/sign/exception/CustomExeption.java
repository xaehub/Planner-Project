package com.example.planner.sign.exception;

public class CustomExeption extends RuntimeException{

    public CustomExeption() {
        super("이메일 또는 비밀번호가 잘못되었습니다.");
    }

    public CustomExeption(String message) {
        super(message);
    }
}
