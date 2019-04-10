package com.example.javamail;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.imap.IMAPFolder;

public class ReadMail extends AsyncTask<Integer, Void, ArrayList<com.example.javamail.Message>> {

    public int totalMessages;
    private Session session;
    private Store store;
    private IMAPFolder folder;
    private String subject;
    private ArrayList<com.example.javamail.Message> list;

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    @Override
    protected ArrayList<com.example.javamail.Message> doInBackground(Integer... integers) {
        try {
            Properties read_props = new Properties();

            read_props.setProperty("mail.store.protocol", "imaps");

            session = Session.getDefaultInstance(read_props, null);

            store = session.getStore("imaps");
            store.connect("imap.gmail.com", Config.EMAIL, Config.PASSWORD);

            folder = (IMAPFolder) store.getFolder("inbox");

            if(!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                list = new ArrayList<com.example.javamail.Message>();
                totalMessages = folder.getMessageCount();
//                Log.e("email!!", "No of Messages : " + folder.getMessageCount());
//                Log.e("email!!", "No of Unread Messages : " + folder.getUnreadMessageCount());
//                Log.e("email!!", "length : " + messages.length);
//                Log.e("sdafsfa", "afdasd" + integers[0]);
                int start_num = messages.length - 1 - Integer.parseInt(integers[0].toString());
                Log.e("email!!!!", "start : " + start_num +" end : " + (start_num - 15 >= 0? start_num - 15 : 0));
                for(int i = start_num; i > (start_num - 15 >= 0? start_num - 15 : 0); i--) {
//                    Log.e("email!!", "*****************************************************************************");
//                    Log.e("email!!", "MESSAGE : " + (i + 1) + ":");

                    Message msg = messages[i];
//                    Log.e("????", msg.toString());
//                    Log.e("????", msg.getFrom()[0].toString());
                    subject = msg.getSubject();
//
                    String contentType = msg.getContentType();
                    String messageContent = "";

                    if(contentType.contains("multipart")) {
                        Multipart multipart = (Multipart) msg.getContent();
                        int numberOfParts = multipart.getCount();
                        for(int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(partCount);
                            messageContent = part.getContent().toString();
                        }
                    } else if(contentType.contains("TEXT/PLAIN") || contentType.contains("TEXT/HTML")) {
                        Object content = msg.getContent();
                        if(content != null) {
                            messageContent = content.toString();
                        }
                    }

                    list.add(new com.example.javamail.Message(subject, MimeUtility.decodeText(msg.getFrom()[0].toString()), msg.getReceivedDate(), messageContent));

//                    Log.e("email!!", "Subject : " + subject);
//                    Log.e("email!!", "From : " + msg.getFrom()[0]);
//                    Log.e("email!!", "To : " + msg.getAllRecipients()[0]);
//                    Log.e("email!!", "Date : " + msg.getReceivedDate());
//                    Log.e("email!!", "Size : " + msg.getSize());
//                    Log.e("email!!", "Body : \n" + messageContent);
//                    Log.e("email!!", msg.getContentType());
                }
                Log.e("klhj", "end!!!!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    protected void onPostExecute(ArrayList<com.example.javamail.Message> list) {

    }
}