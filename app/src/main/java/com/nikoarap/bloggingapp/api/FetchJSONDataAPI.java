package com.nikoarap.bloggingapp.api;



import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchJSONDataAPI {

    //get all authors
    @GET("/authors")
    Call<List<Author>> getAuthorsApi();

    //get posts by authorId
    @GET("/posts")
    Call<List<Post>> getPostsApi(
            @Query("?") String query,
            @Query("authorId") String author_id
    );

    //get comments by postId
    @GET("/comments")
    Call<List<Comment>> getCommentsApi(
            @Query("?") String query,
            @Query("_sort") String sort,
            @Query("&") String and,
            @Query("postId") String post_id
    );

}
