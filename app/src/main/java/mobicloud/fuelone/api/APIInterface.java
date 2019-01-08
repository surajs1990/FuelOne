package mobicloud.fuelone.api;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 *  Created by Suraj Shakya on 01/06/2018.
 */

public interface APIInterface {


    @FormUrlEncoded
    @POST("token")
    Call<ResponseBody> GetToken(@Field("grant_type") String grant_type,
                                @Field("username") String username,
                                @Field("password") String password);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/Account/LogOut")
    Call<ResponseBody> LogOut(@Header("Authorization") String token);



    @FormUrlEncoded
    @POST("A")
    Call<ResponseBody> ForgotPassword(@FieldMap HashMap<String , String> map);


    @FormUrlEncoded
    @POST("api")
    Call<ResponseBody> UpdateOrderProductStatus(@Header("Authorization") String Authorization,
                                                @FieldMap HashMap<String , String> map);

    @Multipart
    @POST("api")
    Call<ResponseBody> UploadProductImage (@Header("Authorization") String Authorization,
                                       @Part MultipartBody.Part image ,
                                       @Part("Barcode") RequestBody name);

    @Headers("Content-Type: application/json")
    @GET
    Call<ResponseBody> GetProfile(@Url String url,
                                  @Header("Authorization") String Authorization);


    @Multipart
    @POST("api")
    Call<ResponseBody> UploadProfileImage (@Header("Authorization") String Authorization,
                                           @Part MultipartBody.Part image ,
                                           @Part("UserId") RequestBody userId);


    @FormUrlEncoded
    @POST("api")
    Call<ResponseBody> DriverPicking(@Header("Authorization") String Authorization,
                                     @FieldMap HashMap<String , String> map);

    @Multipart
    @POST("api")
    Call<ResponseBody> UploadDamagePickingAttachment (@Header("Authorization") String Authorization,
                                           @Part MultipartBody.Part image ,
                                           @Part("OrderId") RequestBody OrderId,
                                           @Part("ProductId") RequestBody ProductId);
 ;


}
