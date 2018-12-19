package com.example.aditya.msu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.mail.Address;
import javax.mail.Message;

public class Main2Activity extends AppCompatActivity {

    private ListView lview;
    TextView tv;
    ArrayList<String> flist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        final String keyWord = bundle.getString("keyWord");
        final String email = bundle.getString("email");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = (TextView) findViewById(R.id.text_view);
        tv.setMovementMethod(new ScrollingMovementMethod());

        lview = (ListView) findViewById(R.id.dynamic);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    Message results[] = Mail.getActivityInstance().getData();

                    try {
                        if (results != null) {

                            ContentSearchTerm cst = null;
                            FromFieldSearchTerm ffst = null;
                            if(!keyWord.equals("")) cst = new ContentSearchTerm(keyWord);
                            if(!email.equals("")) ffst = new FromFieldSearchTerm(email);

                            for (int i = results.length - 1; i >= results.length - 100; i--) {
                                //print subjects of all mails in the inbox
                                boolean flagcst = false;
                                boolean flagffst = false;
                                boolean flagall = false;
                                if(cst != null) flagcst = cst.match(results[i]);
                                else if(ffst != null) flagffst = ffst.match(results[i]);
                                else flagall = true;
                                if(flagcst || flagffst || flagall) {
                                    String content = Mail.getActivityInstance().getTextFromMessage(results[i]);
                                    Address[] recipients = results[i].getAllRecipients();
                                    Address[] senders = results[i].getFrom();
                                    String recipient_list = "";
                                    String sender_list = "";

                                    if (recipients != null) {
                                        for (int k = 0; k < recipients.length; k++) {
                                            recipient_list += recipients[k].toString() + "; ";
                                        }
                                    }
                                    if (senders != null) {
                                        for (int k = 0; k < senders.length; k++) {
                                            sender_list += senders[k].toString() + "; ";
                                        }
                                    }

                                    //                            Log.v("From: ", sender_list);
                                    //                            Log.v("To: ", recipient_list);
                                    //                            Log.v("Date: ", message[i].getReceivedDate().toString());
                                    //                            Log.v("Subject: ", message[i].getSubject());
                                    //                            Log.v("Content: ", content);

                                    String temp = "From: " + sender_list + "\n"
                                            + "To: " + recipient_list + "\n"
                                            + "Date: " + results[i].getReceivedDate().toString() + "\n"
                                            + "Subject: " + results[i].getSubject() + "\n"
                                            + "Content: " + content + "\n"
                                            + "\n\n\n\n\n"
                                            + "========================================" + "\n"
                                            + "========================================" + "\n"
                                            + "\n\n\n\n";

                                    flist.add(temp);
                                    Log.v("Content: ", temp);
                                    tv.append(temp);

                                    //anything else you want
                                }
                            }
                        } else {
                            Log.v("Mail not read", "Not Success");
                        }

                    } catch (Exception e) {
                        Log.e("MailApp", "Could not read email", e);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                Main2Activity.this,
                android.R.layout.simple_list_item_1,
                flist );

        thread.start();

    }

}
