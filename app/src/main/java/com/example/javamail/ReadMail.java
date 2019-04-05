package com.example.javamail;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;

public class ReadMail extends AsyncTask<Void, Void, Void> {
    private Session session;
    private Store store;
    private IMAPFolder folder;
    private String subject;
    private Flag flag;

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        try {
            Properties read_props = new Properties();

            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            read_props.setProperty("mail.store.protocol", "imaps");

            session = Session.getDefaultInstance(read_props, null);

            store = session.getStore("imaps");
            store.connect("imap.gmail.com", "630sunghee@gmail.com", "Gybhunji501");

            folder = (IMAPFolder) store.getFolder("inbox");

            if(!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                Log.e("email!!", "No of Messages : " + folder.getMessageCount());
                Log.e("email!!", "No of Unread Messages : " + folder.getUnreadMessageCount());
                Log.e("email!!", "length : " + messages.length);

                for(int i = messages.length - 1; i >= 0; i--) {
                    Log.e("email!!", "*****************************************************************************");
                    Log.e("email!!", "MESSAGE : " + (i + 1) + ":");

                    Message msg = messages[i];
                    subject = msg.getSubject();

                    Log.e("email!!", "Subject : " + subject);
                    Log.e("email!!", "From : " + msg.getFrom()[0]);
                    Log.e("email!!", "To : " + msg.getAllRecipients()[0]);
                    Log.e("email!!", "Date : " + msg.getReceivedDate());
                    Log.e("email!!", "Size : " + msg.getSize());
                    Log.e("email!!", "Body : \n" + msg.getContent());
                    Log.e("email!!", msg.getContentType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


//        read_props.put("mail.smtp.host", "smtp.gmail.com");
//        read_props.put("mail.smtp.socketFactory.port", "465");
//        read_props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        read_props.put("mail.smtp.auth", "true");
//        read_props.put("mail.smtp.port", "465");

//        //Creating a new session
//        session = Session.getDefaultInstance(read_props,
//                new javax.mail.Authenticator() {
//                    //Authenticating the password
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
//                    }
//                });

        return null;
    }
}
