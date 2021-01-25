package com.example.restaurantsinmycity;

import androidx.appcompat.app.AppCompatActivity;
import javabeans.Cities;

import javabeans.LocationSuggestion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    List<LocationSuggestion> citiesList;
    ArrayList<String> cityNamesArrayList;
    ArrayAdapter<String> arrayAdapter;
    Call<Cities> citiesFromZomato;
    EditText editTextResName;
    Button button ;
    Cities Zcities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view_search_results);
        editTextResName = findViewById(R.id.edit_text_restaurant_name);
        button = findViewById(R.id.button_search);
        cityNamesArrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cityNamesArrayList);
        listView.setAdapter(arrayAdapter);
        //Retrofit code

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String s = editTextResName.getText().toString();
             listView.setVisibility(View.VISIBLE);
             citiesFromZomato= jsonPlaceHolderAPi.getCities(s);
             Log.w("after call","hello22");
             citiesFromZomato.enqueue(new Callback<Cities>() {
                 @Override
                 public void onResponse(Call<Cities> call, Response<Cities> response) {
                     if (!response.isSuccessful()) Log.e("NOT SUCCESS",response.message());
                          else {
                              Cities cities = response.body();


                              ArrayList<LocationSuggestion> list= (ArrayList<LocationSuggestion>) cities.getLocation_suggestions();

                         for (LocationSuggestion e: list){
                             cityNamesArrayList.add(e.getName());

                         }
                         arrayAdapter.getFilter().filter(s);
                              Log.i("Data from sever",list.toString());

                     }

                 }

                 @Override
                 public void onFailure(Call<Cities> call, Throwable t) {
                     Log.e("FAILURE",t.getMessage());
                 }
             });

         }
     });





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s==null || s.equals("")) {
                    listView.setVisibility(View.INVISIBLE);
                    Zcities = null;
                    citiesList.clear();
                    Log.w("empty","hello");
                }else {
                    listView.setVisibility(View.VISIBLE);
                    citiesFromZomato= jsonPlaceHolderAPi.getCities(s);
                    Log.w("after call","hello22");
                    citiesFromZomato.enqueue(new Callback<Cities>()
                    {
                        @Override
                        public void onResponse(Call<Cities> call, Response<Cities> response) {

                            if (!response.isSuccessful())
                            {
                                Log.e("NOT SUCCESS",response.message());
                                //Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                Log.i("SUCCESS","hello");
                                Zcities = response.body();
                                citiesList = new ArrayList<>();
                                citiesList.addAll(Zcities.getLocation_suggestions());
                                for (LocationSuggestion e: citiesList){
                                    cityNamesArrayList.add(e.getName());

                                }


                                arrayAdapter.getFilter().filter(s);
                            }
                        }

                        @Override
                        public void onFailure(Call<Cities> call, Throwable t) {
                            // Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("FAILURE",t.getMessage());
                        }
                    });

                }











                return false;
            }
        });
    }
}