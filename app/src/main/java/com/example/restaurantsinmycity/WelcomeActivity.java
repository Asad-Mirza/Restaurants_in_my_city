package com.example.restaurantsinmycity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import javabeans.Cities;
import javabeans.Cuisine;
import javabeans.Cuisine2;
import javabeans.LocationSuggestion;
import javabeans.REAL_restaurant;
import javabeans.Restaurant;
import javabeans.Restaurant2;
import javabeans.Restaurants;
import javabeans.Section;
import javabeans.UserRating;
import javabeans.cuisins;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {

    private TextView mTextView;
    EditText searchView;
    ListView listView;
    Button button;
    ProgressDialog progressBar;
    ArrayList<String> cityNamesArrayList;
    //lists
    Call<Cities> citiesCall;
    ArrayList<LocationSuggestion> list;
    Retrofit retrofit;
    JsonPlaceHolderAPi jsonPlaceHolderAPi;
    HashMap<String,ArrayList<String>> cuisinsHashMap;
    ArrayList<REAL_restaurant> restaurantArrayList;
    LocationSuggestion locationSuggestion;
    HashMap<String,Integer> sectionIndexHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mTextView = (TextView) findViewById(R.id.text);
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view_search_results);
        button = findViewById(R.id.button1);
        progressBar =new ProgressDialog(WelcomeActivity.this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //retrfit code
        retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressBar.show();
                    String s = searchView.getText().toString();
                citiesCall= jsonPlaceHolderAPi.getCities(s);

                citiesCall.enqueue(new Callback<Cities>() {
                    @Override
                    public void onResponse(Call<Cities> call, Response<Cities> response) {
                        if (!response.isSuccessful()) {
                            progressBar.dismiss();
                            Log.e("NOT SUCCESS", response.message());
                            Toast.makeText(WelcomeActivity.this, "Network Error, Try Again", Toast.LENGTH_SHORT).show();
                        }else {
                            Cities cities = response.body();


                            list= (ArrayList<LocationSuggestion>) cities.getLocation_suggestions();
                            progressBar.dismiss();
                            if (list.size()==0){Toast.makeText(WelcomeActivity.this, "No location found , try again", Toast.LENGTH_SHORT).show();
                            searchView.setText("");
                          }else openDialog();

                        }
                    }

                    @Override
                    public void onFailure(Call<Cities> call, Throwable t) {
                        Log.e("Failure", t.getMessage());
                        progressBar.dismiss();
                        Toast.makeText(WelcomeActivity.this, "Network Error, Try Again", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
    public void openDialog(){



        AlertDialog.Builder builderSingle = new AlertDialog.Builder(WelcomeActivity.this);
      //  builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select your location - ");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(WelcomeActivity.this, android.R.layout.select_dialog_singlechoice);
        for (LocationSuggestion e: list){
            arrayAdapter.add(e.getName()+" "+e.getCountry_name());

        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                locationSuggestion = list.get(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(WelcomeActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected location is");
                searchView.setText(strName);
                builderInner.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        getRestaurants(locationSuggestion.getId());
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

   /* void getCuisinListFromZomatoServer(int cityId){
   progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<cuisins> cuisinsCall = jsonPlaceHolderAPi.getCuisins(cityId+"");
       cuisinsCall.enqueue(new Callback<cuisins>() {
           @Override
           public void onResponse(Call<cuisins> call, Response<cuisins> response) {
               if (!response.isSuccessful()) {
                   progressBar.dismiss();
                   Log.e("NOT SUCCESS", response.message());
                   Toast.makeText(WelcomeActivity.this, "Network Error, Try Again", Toast.LENGTH_SHORT).show();
               }else {
                  cuisins cuisinsObject = response.body();
                   List<Cuisine> cuisineList  = cuisinsObject.getCuisines();
                   cuisinsHashMap = new HashMap<String,ArrayList<String>>();
                   for (Cuisine cuisine : cuisineList){
                       Cuisine2 cuisine2 = cuisine.getCuisine();
                       String c_name = cuisine2.getCuisine_name();

                       cuisinsHashMap.put(c_name,null); }
                   getRestaurants(cityId);




               }
           }

           @Override
           public void onFailure(Call<cuisins> call, Throwable t) {
               Log.e("FAiled", t.getMessage());
               Toast.makeText(WelcomeActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();

           }
       });

    }*/

    void getRestaurants(int cityId){
        Call<Restaurants> restaurantsCall =jsonPlaceHolderAPi.getRestaurants(cityId+"","city");
        restaurantsCall.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                if (!response.isSuccessful()) {
                    progressBar.dismiss();
                    Log.e("NOT SUCCESS", response.message());
                    Toast.makeText(WelcomeActivity.this, "Network Error, Try Again", Toast.LENGTH_SHORT).show();
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
                    }

                    setSectionIndexHashMap();

                }
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Log.e("FAiled2", t.getMessage());
                Toast.makeText(WelcomeActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();


            }
        });



    }

void setSectionIndexHashMap(){
        ArrayList<Section> sectionsArrayList = new ArrayList<Section>();
        sectionIndexHashMap = new HashMap<String,Integer>();
        for (REAL_restaurant r: restaurantArrayList){
            String cuisins[] = r.getCuisin().split(",");
            for (String c:cuisins){
                if (sectionIndexHashMap.containsKey(c)){
                    int index = sectionIndexHashMap.get(c);
                    sectionsArrayList.get(index).getRestaurantArrayList().add(r);
                }else {
                    ArrayList<REAL_restaurant> rList = new ArrayList<REAL_restaurant>();
                    rList.add(r);

                    Section section = new Section(c,rList);
                    int index = sectionsArrayList.size();
                    sectionIndexHashMap.put(c,index);
                    sectionsArrayList.add(section);
                }
            }

        }
    Intent intent = new Intent(WelcomeActivity.this,RestaurantsListActivity.class);
    intent.putExtra("CityObject",locationSuggestion);
    //intent.putExtra("CuisinsHashMap",cuisinsHashMap);
    intent.putExtra("RestaurantArrayList",restaurantArrayList);
    intent.putExtra("RestaurantHashMap",sectionIndexHashMap);
    intent.putExtra("SectionsArrayList",sectionsArrayList);

    startActivity(intent);



}
}