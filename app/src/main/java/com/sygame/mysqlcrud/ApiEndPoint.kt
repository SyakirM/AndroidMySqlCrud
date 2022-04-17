package com.sygame.mysqlcrud

import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {
    @GET("data.php")
    fun getData(): Call<NotesModel>

    @FormUrlEncoded
    @POST("create.php")
    fun createData(
        @Field("note") note: String
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("update.php")
    fun updateData(
        @Field("id") id: String,
        @Field("note") note: String
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteData(
        @Field("id") id: String
    ): Call<SubmitModel>
}