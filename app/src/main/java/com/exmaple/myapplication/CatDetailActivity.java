package com.exmaple.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CatDetailActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String catId;
    private String url;
    private TextView catNameTv;
    private TextView tvDes;
    private TextView weight;
    private TextView temperament;
    private TextView origin;
    private TextView lifeSpan;
    private TextView dogLevel;
    private TextView tvWikiLink;
    private ImageView image;
    private Cat cat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        catId = getIntent().getStringExtra("id");
        url = "https://api.thecatapi.com/v1/images/search?breed_ids=" + catId;
        requestQueue = Volley.newRequestQueue(this);
        catNameTv = findViewById(R.id.cat_name);
        image = findViewById(R.id.image);
        tvDes = findViewById(R.id.tv_description);
        lifeSpan = findViewById(R.id.tv_life);
        dogLevel = findViewById(R.id.tv_dog);
        weight = findViewById(R.id.tv_weight);
        temperament = findViewById(R.id.tv_temp);
        origin = findViewById(R.id.tv_origin);

        tvWikiLink = findViewById(R.id.tv_wiki);
        ImageButton ibFav = findViewById(R.id.bt_favourite);
        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cat==null){
                    return;
                }
                ArrayList<Cat> cats;
                SharedPreferences sharedPreferences = getSharedPreferences("Cat", MODE_PRIVATE);
                String json = sharedPreferences.getString("favourites", "null");
                if (json.equals("null")){
                    cats = new ArrayList<>();
                    cats.add(cat);
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("favourites", gson.toJson(cats));
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Added Success", Toast.LENGTH_SHORT).show();

                }else{
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Cat>>(){}.getType();
                    cats = gson.fromJson(json, type);
                    boolean isColl = false;
                    for (int i = 0; i < cats.size(); i++) {
                        if (cats.get(i).getId().equals(cat.getId())){
                            isColl = true;
                        }
                    }
                    if (isColl){
                        Toast.makeText(getApplicationContext(),"Added  Success", Toast.LENGTH_SHORT).show();
                    }else{
                        cats.add(cat);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("favourites", gson.toJson(cats));
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Added Success", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                Type arrayListType = new TypeToken<ArrayList<CatDetail>>() {
                }.getType();
                ArrayList<CatDetail> catDetailArrayList = gson.fromJson(response, arrayListType);

                if (catDetailArrayList.size() == 0) {
                    finish();
                } else {
                     cat = catDetailArrayList.get(0).getBreeds().get(0);
                    String url = catDetailArrayList.get(0).getUrl();
                    catNameTv.setText(cat.getName());
                    tvDes.setText("Description: " +cat.getDescription());
                    weight.setText("Weight: " +cat.getWeight().getMetric());
                    temperament.setText("Temperament" +cat.getTemperament());
                    origin.setText("Origin: " +cat.getOrigin());
                    lifeSpan.setText("Life Span: " +cat.getLife_span());
                    tvWikiLink.setText("Wikipedia url: " +cat.getWikipedia_url());
                    dogLevel.setText("Dog friendliness level: "+ cat.getDog_friendly());
                    Glide.with(getBaseContext()).load(url).into(image);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);


    }
}
