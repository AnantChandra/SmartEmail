package com.example.aditya.msu;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

public class FromFieldSearchTerm extends SearchTerm {
    public String fromEmail;

    public FromFieldSearchTerm(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public boolean match(Message message) {
        try {
            Address[] fromAddress = message.getFrom();
            if(fromAddress != null && fromAddress.length > 0) {
                if(fromAddress[0].toString().contains(fromEmail)) {
                    return true;
                }
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
