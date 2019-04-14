package com.example.javamail;

import android.os.AsyncTask;
import android.util.Log;

import com.sun.mail.util.BASE64DecoderStream;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

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
        if(contentType.contains("multipart/MIXED")) {
            MimeMultipart mimeMultipart = (MimeMultipart) body;
            try {
                for (int i = 0; i < mimeMultipart.getCount(); i++) {
                    BodyPart part = mimeMultipart.getBodyPart(i);
                    str = part.getContent().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) body;
//            File f = new File("image" + new Date().getTime() + ".jpg");
//            try{
//                DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//
//                while ((bytesRead = base64DecoderStream.read(buffer)) != -1) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//            byte[] byteArray = IOUtils.toByteArray(base64DecoderStream);
//            byte[] encodeBase64 = Base64.encodeBase64(byteArray);
//            base64Content[0] = new String(encodeBase64, "UTF-8");
//            base64Content[1] = getContentTypeString(part);
        } else if(contentType.contains("multipart")) {

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
        } else if(contentType.contains("TEXT/PLAIN") || contentType.contains("TEXT/HTML") || contentType.contains("text/plain") || contentType.contains("text/html")) {
            if(body != null) {
                str = body.toString();
            }
        }
//        Log.e("body", str);
        return str;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
