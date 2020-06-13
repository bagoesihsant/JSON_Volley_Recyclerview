package com.example.androidregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Schedule> scheduleList;
    private final String JSON_URL = "http://192.168.43.132/AndroidTest/schedules.php";
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        scheduleList = new ArrayList<>();

        extractSchedule();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, scheduleList);
        recyclerView.setAdapter(adapter);


    }

    private void extractSchedule()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject scheduleObject = response.getJSONObject(i);
                                Schedule schedule = new Schedule();
                                schedule.setSchedule(scheduleObject.getString("schedule"));
                                schedule.setSchedule_teacher(scheduleObject.getString("schedule_teacher"));
                                scheduleList.add(schedule);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(HomeActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);

    }

}
