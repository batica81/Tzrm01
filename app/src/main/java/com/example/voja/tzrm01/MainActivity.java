package com.example.voja.tzrm01;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.cedarsoftware.util.io.JsonWriter;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView myTextView;

    private String username =  "voja_3079";
    private String password = "testpass123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.editText01);
        myTextView = (TextView) findViewById(R.id.tv_url_display);
        Button button1 = (Button) findViewById(R.id.button1);

//Iskljucivanje networking on main thread provere

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeSearch() throws IOException, JSONException {
        String myUrl = String.valueOf(mSearchBoxEditText.getText());
        URL url = new URL(myUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        JSONObject myJsonData = new JSONObject();
        myJsonData.put("Username", username);
        myJsonData.put("Password", password);
        String jsonString = myJsonData.toString();

        byte[] outputBytes = jsonString.getBytes("UTF-8");
        OutputStream os = urlConnection.getOutputStream();
        os.write(outputBytes);

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        String jsonString2 = IOUtils.toString(in);
        String jsonString3 = JsonWriter.formatJson(jsonString2);
        myTextView.setText(jsonString3);
        urlConnection.disconnect();
    }
}
