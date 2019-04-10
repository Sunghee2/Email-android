package com.example.javamail;

import android.util.Log;

import java.util.Date;

import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

public class Message {
    private String subject;
    private String from;
    private Date date;
    private String contentType;
    private Object body;

    public Message(String subject, String from, Date date, String contentType, Object body) {
        this.subject = subject;
        this.from = from;
        this.date = date;
        this.contentType = contentType;
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

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public Object getBody() {
        Log.e("type!!!", contentType);
        if(contentType.contains("multipart")) {
            Multipart multipart = (Multipart) body;

            try {
                int numberOfParts = multipart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(partCount);
                    body = part.getContent().toString();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if(contentType.contains("TEXT/PLAIN") || contentType.contains("TEXT/HTML")) {
            if(body != null) {
                body = body.toString();
            }
        }
        Log.e("body", body.toString());
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
