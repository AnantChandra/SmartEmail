package com.example.aditya.msu;

import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;

import static org.junit.Assert.*;

public class FromFieldSearchTermTest {

    Mail mail = null;
    Message result[] = null;
    FromFieldSearchTerm ffst = null;

    @Before
    public void init(){
        //Please enter your credentials
        mail = new Mail("yourUsername", "yourPassword");
        ffst = new FromFieldSearchTerm("azanwar@usc.edu");
    }

    @Test
    public void MatchTest() {
        mail.readGmail();
        result = mail.result;
        boolean expected = ffst.match(result[0]);
        assertEquals(false, expected);
    }
}