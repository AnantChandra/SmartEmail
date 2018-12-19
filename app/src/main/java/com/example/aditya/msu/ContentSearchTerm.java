package com.example.aditya.msu;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;

public class ContentSearchTerm extends SearchTerm {
    public String content;

    public ContentSearchTerm(String content) {
        this.content = content;
    }

    @Override
    public boolean match(Message message) {
        try {
            String messageContent = "";
            //String contentType = message.getContentType().toLowerCase();
            if (message.isMimeType("text/plain")
                    || message.isMimeType("text/html")) {
                messageContent = message.getContent().toString();
                if (messageContent.contains(content)) {
                    return true;
                }
            }
            else {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                messageContent = getTextFromMimeMultipart(mimeMultipart);
                if (messageContent.contains(content)) {
                    return true;
                }
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static String getTextFromMimeMultipart(
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
}
