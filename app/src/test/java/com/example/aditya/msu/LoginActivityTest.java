package com.example.aditya.msu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest {

    LoginActivity la = null;

    @Before
    public void init(){
        la = new LoginActivity();
    }

    @Test
    public void RequestContactsTest(){
        boolean requestVal = la.mayRequestContacts();
        assertEquals(true, requestVal);
    }

    @Test
    public void isEmailValidTest() {
        String email = "anantcha@usc.edu";
        boolean valid =  LoginActivity.isEmailValid(email);
        assertEquals(true, valid);
    }

    @Test
    public void isEmailInvalidTest() {
        String email = "anantcha[at]usc.edu";
        boolean valid =  LoginActivity.isEmailValid(email);
        assertEquals(false, valid);
    }

    @Test
    public void isPasswordValidTest() {
        String password = "ThisIsNotActuallyMyPassword";
        boolean valid =  LoginActivity.isPasswordValid(password);
        assertEquals(true, valid);
    }

    @Test
    public void isPasswordInvalidTest() {
        String password = "Boo";
        boolean valid =  LoginActivity.isPasswordValid(password);
        assertEquals(false, valid);
    }
}