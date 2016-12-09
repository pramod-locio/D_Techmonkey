package com.dtechmonkey.d_techmonkey;

import com.dtechmonkey.d_techmonkey.models.PostJSONData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostRetrieve {
    int OFFSET = 1;
    int PER_PAGE_INITIAL = 11;
    int PER_PAGE_AFTER = 10;

    /**
     * http://topyaps.com/wp-json/wp/v2/posts/?page=3
     */


    //All pages with all languages
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostList(@Query("page") int page, @Query("per_page") int perPageCount);

    //All pages with english language
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostListEng(@Query("filter[category_name]") String language, @Query("page") int page, @Query("per_page") int perPageCount);

    //All pages with hindi and bengali languages
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostListMix(@Query("filter[tag]") String language, @Query("page") int page, @Query("per_page") int perPageCount);

    /*@GET("wp-json/wp/v2/posts/?filter[posts_per_page]=31")
    Call<List<PostJSONData>> getPostList();*/

    /*@GET("wp-json/wp/v2/posts/?filter[posts_per_page]=21")
    Call<List<PostJSONData>> getPostList();*/
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

    //For Language selection
    /*http://topyaps.com/wp-json/wp/v2/posts?filter[category_name]=english_lang&page=2*/
    /*http://topyaps.com/wp-json/wp/v2/posts?filter[tag]=bangla&page=3*/
    /*http://topyaps.com/wp-json/wp/v2/posts?filter[tag]=hindi&page=3*/

    /**
     * http://topyaps.com/wp-json/wp/v2/posts/?filter[category_name]=news&page=3
     * @param category
     * @return
     */
    //all pages with language
    //@GET("wp-json/wp/v2/posts/")
    //Call<List<PostJSONData>> getPostList(@Query("page") int page, @Query("per_page") int perPageCount);

    //Pages with category with all languages
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostListCategory(@Query("filter[category_name]") String category, @Query("page") int page, @Query("per_page") int perPageCount);

    //pages with category and language (http://topyaps.com/wp-json/wp/v2/posts/?filter[category_name]=english_lang&filter[category_name]=videos&page=2)

    //Pages with category with english language
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostListCategoryWithLanguageEng(@Query("filter[category_name]") String category, @Query("filter[category_name]") String language, @Query("page") int page, @Query("per_page") int perPageCount);

    //All page with category with hindi or bengali language
    @GET("wp-json/wp/v2/posts/")
    Call<List<PostJSONData>> getPostListCategoryWithLanguageMix(@Query("filter[category_name]") String category, @Query("filter[tag]") String language, @Query("page") int page, @Query("per_page") int perPageCount);

    //Author info
    @GET("wp-json/wp/v2/users/89233")
    Call<List<PostJSONData>> getAuthorInfo();

}
