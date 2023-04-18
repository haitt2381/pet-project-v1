package com.example.petproject.service;

public interface IEmailService {
    void sendSimpleMessage(String to, String subject, String text);
}