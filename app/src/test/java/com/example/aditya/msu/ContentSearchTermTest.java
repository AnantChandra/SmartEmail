package com.example.aditya.msu;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

public class ContentSearchTermTest {

    Mail mail = null;
    Message message = null;
    ContentSearchTerm cst = null;
    Message result[] = null;

    @Before
    public void init(){

        //Please enter your credentials
        mail = new Mail("yourUsername", "yourPassword");
        cst = new ContentSearchTerm("a");
    }

    @Test
    public void MatchTest() throws IOException, MessagingException {
        mail.readGmail();
        result = mail.result;
        assertEquals(true, cst.match(result[0]));
    }

    @Test
    public void getTextFromMimeMultipartTest() throws IOException, MessagingException {
        mail.readGmail();
        result = mail.result;
        MimeMultipart mimeMultipart = (MimeMultipart) result[0].getContent();
        String getText = ContentSearchTerm.getTextFromMimeMultipart(mimeMultipart);
        //these are the first 8 characters hard coded - please change as per your email
        String expected = "\n" + " How to";
        assertEquals(expected, getText.substring(0,8));
    }
}