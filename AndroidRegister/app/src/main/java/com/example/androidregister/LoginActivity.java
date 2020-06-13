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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private TextView link_register;
    private ProgressBar bar;
    private static String URL_LOGIN = "http://192.168.43.132/AndroidTest/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bar = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.bt_login);
        link_register = findViewById(R.id.txt_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inEmail = email.getText().toString().trim();
                String inPassword = password.getText().toString().trim();

                if(!inEmail.isEmpty() || !inPassword.isEmpty())
                {
                    login(inEmail, inPassword);
                }else
                {
                    email.setError("Please insert email.");
                    password.setError("Please insert password");
                }
            }
        });

        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login(final String paramEmail, final String paramPassword)
    {
        bar.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1"))
                            {
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jObject = jsonArray.getJSONObject(i);

                                    String name = jObject.getString("name").trim();
                                    String email = jObject.getString("email").trim();

                                    Toast.makeText(LoginActivity.this, "Login Success \n Your name " + name, Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                    bar.setVisibility(View.GONE);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login error! \n" + e.toString(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Login errors! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", paramEmail);
                params.put("password", paramPassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }


}
