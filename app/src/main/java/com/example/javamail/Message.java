package com.example.javamail;

import java.util.Date;

import javax.mail.Address;

public class Message {
    private String subject;
    private String from;
    private Date date;
    private String body;

    public Message(String subject, String from, Date date, String body) {
        this.subject = subject;
        this.from = from;
        this.date = date;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
