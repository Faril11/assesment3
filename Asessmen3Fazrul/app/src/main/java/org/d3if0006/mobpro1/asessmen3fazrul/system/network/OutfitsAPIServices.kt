package org.d3if0006.mobpro1.asessmen3fazrul.system.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.Outfits
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://fenris-api-host.000webhostapp.com/files/Faril/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

data class OutfitsResponse(
    val results: List<Outfits>
)

interface OutfitsServices {
    @GET("getAllOutfits.php") // Replace with the actual endpoint
    suspend fun getAllOutfits(): OutfitsResponse

    @Multipart
    @POST("addOutfits.php") // Replace with the actual endpoint
    suspend fun addOutfits(
        @Part("email") email: RequestBody,
        @Part("userName") userName: RequestBody,
        @Part("styleName") styleName: RequestBody,
        @Part photo: MultipartBody.Part?
    ): OutfitsResponse

    @FormUrlEncoded
    @POST("deleteOutfits.php") // Replace with the actual endpoint
    suspend fun deleteOutfits(
        @Field("id") id: String
    ): OutfitsResponse
}

object OutfitsAPI {
    val retrofitService: OutfitsServices by lazy {
        retrofit.create(OutfitsServices::class.java)
    }
    fun imgUrl(imageId: String): String {
        return "$BASE_URL$imageId"
    }
    fun tagMaker(tag: String): String {
        return "@$tag"
    }
}

enum class OutfitsStatus { LOADING, SUCCESS, FAILED }
