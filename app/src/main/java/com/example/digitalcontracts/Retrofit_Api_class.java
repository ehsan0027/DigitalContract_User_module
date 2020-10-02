package com.example.digitalcontracts;




import com.example.digitalcontracts.Models.Dashboard_api;
import com.example.digitalcontracts.Models.LOGINOTP;
import com.example.digitalcontracts.Models.Login_api;
import com.example.digitalcontracts.Models.PDF_API;
import com.example.digitalcontracts.Models.Status_percent;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface Retrofit_Api_class {

    @FormUrlEncoded
    @POST("applicant/get-docs")
    Call<PDF_API> pdfs(
            @Header("Authorization") String token,
                @Field("application_id") String application_id);
//    @GET("dashboard")
//    Call<Dashboard> Dashboard_without_header();
//
//    @GET("dashboard")
//    Call<Dashboard> Dashboard_with_header(
//            @Header("Authorization") String Token
//    );
//
//    @GET("my-adds")
//    Call<My_adds> my_adds(
//            @Header("Authorization") String Token
//    );
//
//    @GET("profile")
//    Call<Profile_Screen> profile(
//            @Header("Authorization") String Token
//    );
//
//    @GET("{url}")
//    Call<Single_Record> single_record(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//    @GET("{url}")
//    Call<Remove_add> delete_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//

//
//    @GET("purchase")
//    Call<Search_recycler> search_adds(
//            @Header("Authorization") String Token
//    );
//
//    @GET("sales/create-add")
//    Call<Create_form> create_form(
//            @Header("Authorization") String Token
//    );
//
//
//    @GET("feedback")
//    Call<GetFeedback> getfeedback_with_header(
//            @Header("Authorization") String Token
//    );
//
//    @GET("feedback")
//    Call<GetFeedback> getfeedback();
//
//
//    @FormUrlEncoded
//    @POST("change-password")
//    Call<Password_change> change_password(
//            @Header("Authorization") String Token,
//            @Field("current_password") String current_password,
//            @Field("password") String password,
//            @Field("confirm_password") String confirm_password);
//
//
//    @FormUrlEncoded
//    @POST("feedback/submit")
//    Call<GetFeedback> send_feedback_with_header(
//            @Header("Authorization") String Token,
//            @Field("feedback_status") String feedback_status,
//            @Field("feedback") String feedback);
//
//
//    @FormUrlEncoded
//    @POST("feedback/submit")
//    Call<GetFeedback> send_feedback(
//            @Field("email") String Token,
//            @Field("feedback_status") String feedback_status,
//            @Field("feedback") String feedback);
//
    @FormUrlEncoded
    @POST("applicant-login")
    Call<Login_api> login(
            @Field("cnic") String cnic);
    @FormUrlEncoded
    @POST("applicant/set-status")
    Call<Login_api> accept_record(
            @Header("Authorization") String Token,
            @Field("application_id") String application_id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("applicant-otp-verify")
    Call<LOGINOTP> otp_verify(
            @Field("cnic") String cnic,
            @Field("email_otp") String otp_email,
            @Field("phone_otp") String otp_mobile,
            @Field("device_id") String device_id
            );
//    @POST("forgot-password-phone")
//    Call<login> check_phone(
//            @Field("mobile_number") String email);
//
    @GET("applicant/dashboard")
    Call<Dashboard_api> dashboard(
            @Header("Authorization") String token);
//
//
//
//    @FormUrlEncoded
//    @POST("register")
//    Call<login> register(
//            @Field("email") String email,
//            @Field("password") String password,
//            @Field("cnic") String cnic,
//            @Field("mobile_number") String mobile_number,
//            @Field("dob") String dob,
//            @Field("name") String name
//    );
//
//    @FormUrlEncoded
//    @POST("otp-refresh")
//    Call<login> otp_refresh(
//            @Field("email") String email);
//
//    @FormUrlEncoded
//    @POST("home/edit-profile-submit")
//    Call<login> save_edit_profile(
//            @Header("Authorization") String Token,
//            @Field("email") String email,
//            @Field("cnic") String cnic,
//            @Field("mobile_number") String mobile_number,
//            @Field("dob") String dob,
//            @Field("name") String name
//    );
//
//
//
//    @POST("otp-verify")
//    Call<login> otp_verify(
//            @Field("otp") String otp
//    );
//
//    @GET("home/add-update-token/{url}")
//    Call<login> otp_verify_edit(
//            @Path(value = "url", encoded = true) String url,
//            @Header("Authorization") String Token
//    );
//
//
//    @FormUrlEncoded
//    @POST("seeker/get-food")
//    Call<Active_adds> seeker_dashboard(
//            @Header("Authorization") String Token,
//            @Field("long") String longg
//    );
//
@FormUrlEncoded
@POST("applicant/get-sig-result")
    Call<Status_percent> sig_percent(
            @Header("Authorization") String Token,
            @Field("application_id") String application_id
            );

    @FormUrlEncoded
    @POST("applicant/get-img-result")
    Call<Status_percent> face_percent(
            @Header("Authorization") String Token,
            @Field("application_id") String application_id
    );
//
//    @GET("home/edit-profile")
//    Call<Edit_profile_api> set_edit_profile_values(
//            @Header("Authorization") String Token
//    );
//
//    @GET("seeker/single-food/{url}")
//    Call<Seeker_full_add> history_full_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//
//    @GET("giver/record-delete/{url}")
//    Call<login> delete_active_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("giver/inactive/{url}")
//    Call<login> inactive_active_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("giver/active/{url}")
//    Call<login> active_active_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("home/profile")
//    Call<Profile_api> profile(
//            @Header("Authorization") String Token
//    );
//
//    @FormUrlEncoded
//    @POST("forgot-password-otp")
//    Call<login> otp_verify_forgot(
//            @Field("forget_password_token") String otp
//    );
//
//    @FormUrlEncoded
//    @POST("forgot-password-new")
//    Call<login> change_password(
//            @Field("id") String otp,
//            @Field("password") String password,
//            @Field("confirm_password") String confirm_password
//    );
//
//    @POST("home/logout")
//    Call<login> logout(
//            @Header("Authorization") String Token
//    );
//
//
//    @GET("giver/active-dash")
//    Call<Active_adds> get_active_adds(
//            @Header("Authorization") String Token
//    );
//
//    @GET("giver/inactive-dash")
//    Call<Active_adds> get_in_active_adds(
//            @Header("Authorization") String Token
//    );
//
//
//    @GET("home/donations")
//    Call<Donation_response> donations(
//            @Header("Authorization") String Token
//    );
//
//    @GET("giver/history")
//    Call<History_respinse> history(
//            @Header("Authorization") String Token
//    );
//
//    @GET("seeker/request-food-history")
//    Call<History_respinse> seeker_hisory(
//            @Header("Authorization") String Token
//    );
//
//
//    @GET("seeker/request-food-active-booking")
//    Call<History_respinse> seeker_active_bookings(
//            @Header("Authorization") String Token
//    );
//
//    @GET("seeker/request-food")
//    Call<History_respinse> request_for_food(
//            @Header("Authorization") String Token
//    );
//
//
//    @GET("seeker/single-food/{url}")
//    Call<Seeker_full_add> seeker_single_add_open(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("seeker/single-food-details/{url}")
//    Call<Seeker_full_add> seeker_single_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("seeker/food-picked/{url}")
//    Call<Seeker_full_add> food_picked(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//
//    );
//
//    @GET("seeker/on-way/{url}")
//    Call<Seeker_full_add> on_my_way(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//
//    );
//
//
//    @GET("seeker/request-food-timer/{url}")
//    Call<Seeker_full_add> timer_chalaoo(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//
//    );
//
//    @GET("seeker/request-food/{url1}/{url2}/{url3}")
//    Call<Seeker_full_add> request_add(
//            @Header("Authorization") String Token,
//            @Path(value = "url1", encoded = true) String ID,
//            @Path(value = "url2", encoded = true) String lat,
//            @Path(value = "url3", encoded = true) String longg
//
//    );
//
//
//
//    @GET("giver/single-food/{url}")
//    Call<Single_food_reponse> single_food(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("giver/record-requests/{url}")
//    Call<Check_if_request> food_request_wala(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url);
//    @GET("giver/single-food-details/{url}")
//    Call<Food_request> food_request(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//    @GET("giver/record-requests/accept/{url}")
//    Call<Food_request> accept_request(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//
//    @GET("giver/record-requests/reject/{url}")
//    Call<Food_request> rejecr_request(
//            @Header("Authorization") String Token,
//            @Path(value = "url", encoded = true) String url
//    );
//
//
//
//    @GET("giver/notifications")
//    Call<Number_notifications> number_of_notifications(
//            @Header("Authorization") String Token);
//
//    @GET("seeker/notifications")
//    Call<Number_notifications> number_of_notifications_seeker(
//            @Header("Authorization") String Token);
//
//    @GET("giver/notifications-list")
//    Call<Notification_list> Notification_list(
//            @Header("Authorization") String Token);
//
//    @GET("home/get-update-token")
//    Call<Notification_list> profile_update_token(
//            @Header("Authorization") String Token);
//
//
//    @GET("seeker/notifications-list")
//    Call<Notification_list> Notification_list_seeker(
//            @Header("Authorization") String Token);
//
//
//    @Multipart
//    @POST("giver/add")
//    Call<login> uploadPhoto(
//            @Part("name") RequestBody name,
//            @Part("description") RequestBody description,
//            @Part("servings") RequestBody servings,
//            @Part("lat") RequestBody lat,
//            @Part("long") RequestBody llong,
//            @Part("started_time") RequestBody started_time,
//            @Part("end_time") RequestBody end_time,
//            @Part MultipartBody.Part parts
//    );
//
//
//

    @Multipart
    @POST("applicant/image-verify")
    Call<RequestBody> face(
            @Header("Authorization") String token,
            @Part("application_id") RequestBody application_id,
            @Part MultipartBody.Part signature);


    @Multipart
    @POST("applicant/signature-verify")
    Call<RequestBody> sigantures(
            @Header("Authorization") String token,
            @Part("application_id") RequestBody application_id,
            @Part MultipartBody.Part signature);
    @Multipart
    @POST("applicant/add-cnic")
    Call<RequestBody> cnic_pic(
            @Header("Authorization") String token,
            @Part("application_id") RequestBody application_id,
            @Part MultipartBody.Part cnic_front,
            @Part MultipartBody.Part cnic_back
    );
}
