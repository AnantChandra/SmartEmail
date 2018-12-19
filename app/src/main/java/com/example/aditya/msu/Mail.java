package com.example.aditya.msu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static android.support.v4.content.ContextCompat.startActivity;

public class Mail extends javax.mail.Authenticator {
    public String _user;
    public String _pass;

    public String[] _to;
    public String _from;

    public String _port;
    public String _sport;

    public String _host;
    public String _rhost;

    public String _subject;
    public String _body;

    public boolean _auth;

    public boolean _debuggable;

    public Multipart _multipart;

    static Mail INSTANCE;
    public Message[] result = null;
    public boolean showNotif = false;
    Folder folder;
    Store store;
    Session session2;

    public Mail() {
        INSTANCE = this;
        _host = "smtp.gmail.com"; // default smtp server
        _port = "465"; // default smtp port
        _sport = "465"; // default socketfactory port

        _user = ""; // username
        _pass = ""; // password
        _from = ""; // email sent from
        _subject = ""; // email subject
        _body = ""; // email body

        _debuggable = false; // debug mode on or off - default off
        _auth = true; // smtp authentication - default on

        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a
        // handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap
                .getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public Mail(String user, String pass) {
        this();

        _user = user;
        _pass = pass;
    }

    public boolean send() throws Exception {
        Properties props = _setProperties();

        if (!_user.equals("") && !_pass.equals("") && _to.length > 0
                && !_from.equals("") && !_subject.equals("")
                && !_body.equals("")) {
            javax.mail.Session session = javax.mail.Session.getInstance(props,
                    this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            InternetAddress[] addressTo = new InternetAddress[_to.length];
            for (int i = 0; i < _to.length; i++) {
                addressTo[i] = new InternetAddress(_to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());

            // setup message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(_body);
            _multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(_multipart);

            // send email
            Transport.send(msg);
            return true;
        } else {
            return false;
        }
    }

    private boolean textIsHtml = false;

    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

    public boolean readGmail(){

        /*this will print subject of all messages in the inbox of sender@gmail.com*/

        //if(result == null) {
            this._rhost = "imap.gmail.com";//for imap protocol

            Properties props2 = System.getProperties();

            props2.setProperty("mail.store.protocol", "imaps");
            // I used imaps protocol here

            session2 = Session.getDefaultInstance(props2, null);
        //}

        try {
            store = session2.getStore("imaps");
            store.connect(this._rhost, this._user, this._pass);

            folder=store.getFolder("INBOX");//get inbox
            folder.open(Folder.READ_ONLY);//open folder only to read
            Message message[]=folder.getMessages();
            if(result != null) {
                if(message.length > result.length) {
                    showNotif = true;
                }
                else showNotif = false;
            }
            else showNotif = false;
            result = message;

            //close connections

//            folder.close(true);
//            store.close();
            return true;

        } catch (Exception e) {
//            Log.e("MailApp", "Could not read email", e);
            return false;
        }

    }


    public String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
            result = message.getContent().toString();
        }
//        else if (message.isMimeType("multipart/*")) {
//            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
//            result = getTextFromMimeMultipart(mimeMultipart);
//        }
        else {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    public String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        javax.activation.DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(
                (javax.activation.DataSource) source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }

    @Override
    public javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(_user, _pass);
    }

    public Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);

        if (_debuggable) {
            props.put("mail.debug", "true");
        }

        if (_auth) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    // the getters and setters
    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    public String[] getTo() {
        return _to;
    }

    public void setTo(String[] _to) {
        this._to = _to;
    }

    public String getFrom() {
        return _from;
    }

    public void setFrom(String _from) {
        this._from = _from;
    }

    public String getSubject() {
        return _subject;
    }

    public void setSubject(String _subject) {
        this._subject = _subject;
    }

    public static Mail getActivityInstance() {
        return INSTANCE;
    }
    public Message[] getData() {
        return this.result;
    }
    public boolean getShowNotif() {
        return showNotif;
    }
}