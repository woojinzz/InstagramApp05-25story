package com.koddev.instagramtest.FCM;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAzj9bqyg:APA91bGcu63Xg8SDjcsfRGAT5UBGLQ2FIjmrPcdnBP4QnOdyQciqfzbGwo5BnvfU5-6uJCBadm_HmO_NIDanaAA7BaWqzmWiVyQ75y7cpcxcrXn3EQL4vseXYA4kgR_SgazXqRrkh-od"  // FCM 서버 키
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}