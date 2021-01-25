package com.example.restaurantsinmycity;

import java.util.List;

import javabeans.Cities;

import javabeans.Restaurants;
import javabeans.cuisins;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Asad Mirza on 24-01-2021.
 */
//https://developers.zomato.com/api/v2.1/cities?q=DEWAS
interface JsonPlaceHolderAPi {
    @Headers({"Accept: application/json", "user-key: 108482ce1e7ec28f56e4be46dd9d549d"})
    @GET("api/v2.1/cities")
    Call<Cities> getCities(@Query("q") String query);

   // https://developers.zomato.com/api/v2.1/cuisines?city_id=11446
    @Headers({"Accept: application/json", "user-key: 108482ce1e7ec28f56e4be46dd9d549d"})
    @GET("api/v2.1/cuisines")
    Call<cuisins> getCuisins(@Query("city_id") String cityId);

   // https://developers.zomato.com/api/v2.1/search?entity_id=11446&entity_type=city
   @Headers({"Accept: application/json", "user-key: 108482ce1e7ec28f56e4be46dd9d549d"})
   @GET("api/v2.1/search")
   Call<Restaurants> getRestaurants(@Query("entity_id") String entity_id, @Query("entity_type") String entity_type);

   //https://developers.zomato.com/api/v2.1/search?entity_id=11446&entity_type=city&q=mukesh
   @Headers({"Accept: application/json", "user-key: 108482ce1e7ec28f56e4be46dd9d549d"})
   @GET("api/v2.1/search")
   Call<Restaurants> getRestaurantsByName(@Query("entity_id") String entity_id, @Query("entity_type") String entity_type,@Query("q") String q);

}
