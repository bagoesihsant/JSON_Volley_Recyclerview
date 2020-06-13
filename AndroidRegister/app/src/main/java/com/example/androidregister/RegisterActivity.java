package com.example.androidregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password;
    private TextView txtLinkLogin;
    private Button btn_register;
    private ProgressBar bar;
    private static String URL_REGISTER = "http://192.168.43.132/AndroidTest/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bar = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);

        txtLinkLogin = findViewById(R.id.txtLinkLogin);

        btn_register = findViewById(R.id.bt_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        txtLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void register()
    {
        bar.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.GONE);

        final String strName = this.name.getText().toString().trim();
        final String strEmail = this.email.getText().toString().trim();
        final String strPassword = this.password.getText().toString().trim();

        StringRequest strRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1"))
                            {
                                Toast.makeText(RegisterActivity.this, "Register success.", Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register error!" + e.toString(), Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                            btn_register.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register error!" + error.toString(), Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        btn_register.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",strName);
                params.put("email",strEmail);
                params.put("password",strPassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strRequest);

    }
}
