package com.example.aditya.msu;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

import static org.junit.Assert.*;

public class MailTest {

    Mail mail = null;
    Mail correctMail = null;
    Mail incorrectMail = null;
    Message message = null;
    Properties props = null;
    Multipart multipart;
    MimeMultipart mimeMultipart = null;
    Message[] result = null;
    String user = null;
    String pass = null;
    String _user = null;
    String _pass = null;
    String _body = null;
    String _from = null;
    boolean notif = false;

    @Before
    public void init(){
        //Please enter your credentials
        correctMail = new Mail("YourEmail", "YourPassword");
        incorrectMail = new Mail("invalidEmail", "RandomText");
        mail = new Mail("YourEmail", "YourPassword");

        props = new Properties();
        props.put("mail.smtp.host", correctMail._host);
        if (correctMail._debuggable) {
            props.put("mail.debug", "true");
        }
        if (correctMail._auth) {
            props.put("mail.smtp.auth", "true");
        }
        props.put("mail.smtp.port", correctMail._port);
        props.put("mail.smtp.socketFactory.port",correctMail._sport);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        mimeMultipart = new MimeMultipart();

        correctMail._user = new String();
        correctMail._pass = new String();
        correctMail._to = new String[10];

        _user = mail._user;
        _pass = mail._pass;
        _body = mail._body;
        _from = mail._from;

        notif = mail.showNotif;
    }

    @Test
    public void FromTest(){
        assertEquals(_from, mail.getFrom());
    }

    @Test
    public void BodyTest() throws Exception {
        correctMail.send();
        mail.setBody(_body);
        assertEquals("", _body);
        assertNotNull(_body);
    }

    @Test
    public void MimeTest() throws IOException, MessagingException {
        assertEquals("", correctMail.getTextFromMimeMultipart(mimeMultipart));
        assertNotNull(correctMail.getTextFromMimeMultipart(mimeMultipart));
    }

    @Test
    public void PropTest() {
        assertEquals(props, correctMail._setProperties());
    }

    @Test
    public void SendTest() throws Exception {
        assertEquals(false, correctMail.send());
    }

    @Test
    public void ReadGmailTest() {
        assertEquals(true, mail.readGmail());
    }

    @Test
    public void TetTextFromMessageTest() throws IOException, MessagingException {
        mail.readGmail();
        result = mail.result;
        String firstEmail = mail.getTextFromMessage(result[0]);
        //these are the first 8 characters hard coded - please change as per your email
        String expected = "\n" + " How to";
        assertEquals(expected, firstEmail.substring(0,8));
    }

    @Test
    public void NotifTest(){
        mail.readGmail();
        assertEquals(notif, mail.getShowNotif());
    }
}
