package com.example.javamail;

import android.os.AsyncTask;
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

    public String getBody() {
        Log.e("type!!!", contentType);
        String str = "";
        if(contentType.contains("multipart")) {


            try {
//                for(int partCount = 0; partCount < multipart.getCount(); partCount++) {
//                    str = multipart.getBodyPart(partCount).toString();
//                }
                AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        String str = "";
                        Multipart multipart = (Multipart) body;
                        try {
                            int numberOfParts = multipart.getCount();
                            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(partCount);
                                str = part.getContent().toString();
//                                Log.e("...?", )
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        return str;
                    }
                };
                str = asyncTask.execute().get();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if(contentType.contains("TEXT/PLAIN") || contentType.contains("TEXT/HTML")) {
            if(body != null) {
                str = body.toString();
            }
        }
        Log.e("body", str);
        return str;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
