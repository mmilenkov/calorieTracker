package org.selostudios.tracker_data.remote

import org.selostudios.tracker_data.remote.dto.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFoodAPI {

    companion object {
        const val BASE_URL = "https://us.openfoodfacts.org/" //change for language code. Using us for english
    }

    //Not paginating since 40 entries are more than enough for our usecase
    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,nutriments,image_front_thumb_url")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): SearchDTO
}