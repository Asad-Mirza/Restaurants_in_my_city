package com.example.restaurantsinmycity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import javabeans.LocationSuggestion;
import javabeans.REAL_restaurant;
import javabeans.Restaurant;
import javabeans.Restaurant2;
import javabeans.Restaurants;
import javabeans.Section;
import javabeans.UserRating;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import  com.example.restaurantsinmycity.adapters.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RestaurantsListActivity extends AppCompatActivity {
  EditText editText1,editText2;
  TextView textView;
    HashMap<String,Integer> sectionIndexHashMap;
    HashMap<String, ArrayList<String>> cuisinsHashMap;
    ArrayList<REAL_restaurant> restaurantArrayList;
    RecyclerView mainRecyclerView;
    ArrayList<Section> sectionArrayList;
    ProgressDialog progressBar;
    Button button;
    mainRecylerViewAdapter mainAdapter;
    //retro
    Retrofit retrofit;
    JsonPlaceHolderAPi jsonPlaceHolderAPi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
        editText1 = findViewById(R.id.cityname);
        editText2 = findViewById(R.id.edit_text_resaurant_name);
        textView = findViewById(R.id.textview);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
   button = findViewById(R.id.button);
        progressBar =new ProgressDialog(RestaurantsListActivity.this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         editText1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(RestaurantsListActivity.this,WelcomeActivity.class));
                 finish();
             }
         });
        Intent intent = getIntent();
        LocationSuggestion locationSuggestion =(LocationSuggestion) intent.getSerializableExtra("CityObject");
        cuisinsHashMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("CuisinsHashMap");
        editText1.setText(locationSuggestion.getName()+" "+locationSuggestion.country_name);
         restaurantArrayList = (ArrayList<REAL_restaurant>) intent.getSerializableExtra("RestaurantArrayList");
          sectionArrayList =(ArrayList<Section>)intent.getSerializableExtra("SectionsArrayList");
      /* Iterator hmIterator = cuisinsHashMap.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
          StringBuffer s = new StringBuffer(mapElement.getKey().toString()+"\n");
          s.append(textView.getText().toString());
          textView.setText(s);

                  }*/

       // textView.setText(sectionArrayList.toString());
      mainAdapter  = new mainRecylerViewAdapter(sectionArrayList);
        mainRecyclerView.setAdapter(mainAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);

   editText1.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         //  startActivity(new Intent(RestaurantsListActivity.this,WelcomeActivity.class));
           finish();
       }
   });
        button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          String s = editText2.getText().toString();
          if (s.equals("")){
              Toast.makeText(RestaurantsListActivity.this, "Please enter name of the restaurant", Toast.LENGTH_SHORT).show();
          }else {
              progressBar.show();
              Call<Restaurants> restaurantsCall =jsonPlaceHolderAPi.getRestaurantsByName(locationSuggestion.getId()+"","city",s);
restaurantsCall.enqueue(new Callback<Restaurants>() {
    @Override
    public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
        if (!response.isSuccessful()) {
            progressBar.dismiss();
            Log.e("NOT SUCCESS", response.message());
            Toast.makeText(RestaurantsListActivity.this, "Network Error, Try Again", Toast.LENGTH_SHORT).show();
        }else {
            Restaurants restaurants = response.body();

            List<Restaurant> restaurantList = restaurants.getRestaurants();

            restaurantArrayList =new ArrayList<REAL_restaurant>();
            for (Restaurant r:restaurantList){
                Restaurant2 r2 =r.getRestaurant();
                REAL_restaurant real_restaurant = new REAL_restaurant();
                real_restaurant.setName(r2.getName());
                UserRating userRating = r2.getUser_rating();
                real_restaurant.setRating(userRating.aggregate_rating);
                real_restaurant.setTiming(r2.getTimings());
                real_restaurant.setCuisin(r2.getCuisines());
                real_restaurant.setCityid(r2.getId());
                restaurantArrayList.add(real_restaurant);
                Log.i("DATA1",restaurantList.toString());
            }

            setSectionIndexHashMap();
        }

    }

    @Override
    public void onFailure(Call<Restaurants> call, Throwable t) {
        Log.e("FAiled2", t.getMessage());
        Toast.makeText(RestaurantsListActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();

    }
});


          }
      }
  });
    }


    void setSectionIndexHashMap(){
        sectionArrayList.clear();

        sectionIndexHashMap = new HashMap<String,Integer>();
        for (REAL_restaurant r: restaurantArrayList){
            String cuisins[] = r.getCuisin().split(",");
            for (String c:cuisins){
                if (sectionIndexHashMap.containsKey(c)){
                    int index = sectionIndexHashMap.get(c);
                    sectionArrayList.get(index).getRestaurantArrayList().add(r);
                }else {
                    ArrayList<REAL_restaurant> rList = new ArrayList<REAL_restaurant>();
                    rList.add(r);

                    Section section = new Section(c,rList);
                    int index = sectionArrayList.size();
                    sectionIndexHashMap.put(c,index);
                    sectionArrayList.add(section);
                }
            }

        }
      mainAdapter.notifyDataSetChanged();
progressBar.dismiss();

    }
}


