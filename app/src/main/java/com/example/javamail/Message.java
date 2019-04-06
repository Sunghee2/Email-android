package com.example.javamail;

import java.util.Date;

import javax.mail.Address;

public class Message {
    private String subject;
    private Address from;
    private Date date;
    private String body;

    public Message(String subject, Address from, Date date, String body) {
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

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
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
