package com.example.monitorh20;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private static final String URL_READ = "https://api.thingspeak.com/channels/2782709/fields/1.json?api_key=BIQ169NLKIRSRNIO&results=1";

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.main_txt);
        readJSON();
    }

    public void readJSON() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL_READ, new AsyncHttpResponseHandler() {
            @Override
            public void onSucess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    String response = new String(responseBody);
                    processJSON(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void processJSON(String json) {
        try {
            JSONObject root = new JSONObject(json);
            JSONArray feeds = root.getJSONArray("feeds");
            String valor, texto = "";

            for (int i = 0; i < feeds.length(); i++) {
                valor = feeds.getJSONObject(i).getString("field1");
                texto = texto + valor + "\n";
            }
            txt.setText(texto);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void servoAbierta(View view) {
        String url = "https://api.thingspeak.com/update?api_key=A0T3NQX2W7RSGMOW&field1=1";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSucess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    String response = new String(responseBody);
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void servoCerrada(View view) {
        String url = "https://api.thingspeak.com/update?api_key=A0T3NQX2W7RSGMOW&field1=0";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSucess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    String response = new String(responseBody);
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}