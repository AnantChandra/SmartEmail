package com.example.aditya.msu;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class HomePageActivity extends AppCompatActivity {

    static Database db;
    String path = Environment.getDataDirectory().getAbsolutePath() + "/sample1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

//        Reader json = null;
//
//        File dir = new File(path);
//        dir.mkdirs();
//        File file = new File(path + "/savedFile.txt");
//
//        FileOutputStream fos = null;
//        try
//        {
//            fos = new FileOutputStream(file);
//            System.out.println("before reading file 4");
//        }
//        catch (FileNotFoundException e) {e.printStackTrace();}
//        try
//        {
//            try
//            {
//                System.out.println("before reading file 5");
//                fos.write("hey".getBytes());
//                System.out.println("before reading file 6");
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }
//        finally
//        {
//            try
//            {
//                fos.close();
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }

//        try {
//            System.out.println("before reading file");
//            json = new FileReader("/Users/manipushpakgupta/Documents/Androidapps/my_smart_usc/libs/database.json");
//            System.out.println("after reading file");
//            Gson gson = new Gson();
//            db = gson.fromJson(json, Database.class);
//            System.out.print(db.emailIDs.get(0));
//        }
//
//        catch (FileNotFoundException e) {
//            System.out.println("That file could not be found.");
//            System.out.println();
//        }
//        catch (JsonParseException e) {
//            System.out.println("That file is not a well-formed JSON file.");
//            System.out.println();
//        }
//        catch (IOException ioe) {
//            System.out.println(ioe.getMessage());
//        }
//        finally {
//            if(json != null) {
//                try {
//                    json.close();
//                }
//                catch (IOException ioe) {
//                    System.out.println(ioe.getMessage());
//                }
//            }
//        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        TextView tv = (TextView) findViewById(R.id.text_view);
//        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    public void keyWordButton(View v) {
        EditText keyWord = findViewById(R.id.editText);
        final String keyText = keyWord.getText().toString();
        Button newKeyWord = new Button(this);
        newKeyWord.setText(keyText);

        LinearLayout ll = (LinearLayout)findViewById(R.id.homepage);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(newKeyWord, lp);

        newKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Main2Activity.class);
                intent.putExtra("keyWord", keyText);
                intent.putExtra("email", "");
                startActivity(intent);
            }
        });
        Log.v("HEY", "before adding to db");
        //db.keywords.add(keyText);
        Log.v("HEY", "after adding to db");
        //saveToFile();
        Log.v("HEY", "after savetofile");

    }

    public void emailButton(View v) {
        EditText emailID = findViewById(R.id.editText2);
        final String emailText = emailID.getText().toString();
        Button newEmailID = new Button(this);
        newEmailID.setText(emailText);

        LinearLayout ll = (LinearLayout)findViewById(R.id.homepage);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(newEmailID, lp);

        newEmailID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Main2Activity.class);
                intent.putExtra("email", emailText);
                intent.putExtra("keyWord", "");
                startActivity(intent);
            }
        });
        //db.emailIDs.add(emailText);
        //saveToFile();
    }

    public void allEmails(View v) {
        Intent intent = new Intent(HomePageActivity.this, Main2Activity.class);
        intent.putExtra("email", "");
        intent.putExtra("keyWord", "");
        startActivity(intent);
    }

    public void saveToFile() {
        Writer outputJson = null;
        try {
            Log.v("HEY", "Entering try block");

            //OutputStream os = openFileOutput("database.json", MODE_PRIVATE);
            //BufferedWriter lout = new BufferedWriter(new OutputStreamWriter(os));
            //outputJson = new FileWriter("database.json");
            OutputStreamWriter osw = new OutputStreamWriter(getApplicationContext().openFileOutput("database.json", Context.MODE_PRIVATE));
            osw.write(1);
            osw.close();
            //Gson gson = new Gson();
            Log.v("HEY", "before toJson");
            //gson.toJson(db, osw);
            Log.v("HEY", "after toJson");
        }
        catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        finally {
            if(outputJson != null) {
                try {
                    outputJson.close();
                }
                catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }
    }
}
