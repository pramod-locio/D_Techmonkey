package com.dtechmonkey.d_techmonkey;

import com.dtechmonkey.d_techmonkey.models.PostJSONData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostRetrieve {

    @GET("wp-json/wp/v2/posts/?filter[posts_per_page]=21")
    Call<List<PostJSONData>> getPostList();
    /*@GET("wp-json/wp/v2/posts/?filter[category_name]=news")
    Call<List<PostJSONData>> getListNews();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=art-and-culture")
    Call<List<PostJSONData>> getListCulture();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=fun-and-entertainment")
    Call<List<PostJSONData>> getListEntertainment();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=military")
    Call<List<PostJSONData>> getListMilitary();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=women")
    Call<List<PostJSONData>> getListWomen();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=nature")
    Call<List<PostJSONData>> getListNature();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=food")
    Call<List<PostJSONData>> getListFood();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=history")
    Call<List<PostJSONData>> getListHistory();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=videos")
    Call<List<PostJSONData>> getListVideos();

    @GET("wp-json/wp/v2/posts/?filter[category_name]=opinion")
    Call<List<PostJSONData>> getListOpinion();
*/
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostListCategory(@Query("filter[category_name]") String category);

}
