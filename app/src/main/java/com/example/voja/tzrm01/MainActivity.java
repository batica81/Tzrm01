package com.example.voja.tzrm01;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView myTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.editText01);
        myTextView = (TextView) findViewById(R.id.tv_url_display);
        Button button1 = (Button) findViewById(R.id.button1);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    makeSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }



    private void makeSearch() throws IOException {
        String myUrl = String.valueOf(mSearchBoxEditText.getText());
        URL url = new URL(myUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

//        copyInputStreamToOutputStream(in, System.out);
        myTextView.setText(IOUtils.toString(in));


//        myTextView.setText("YO!");
    }

}
