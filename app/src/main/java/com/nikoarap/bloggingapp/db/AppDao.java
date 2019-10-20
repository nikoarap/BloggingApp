package com.nikoarap.bloggingapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;

import java.util.List;
import java.util.Observable;


@Dao
public interface AppDao {

    //authors
    @Insert
    void insertAuthors(Author author); //... gets a list

    @Query("SELECT * FROM authors")
    LiveData<List<Author>> getAuthors();

    //posts
    @Insert
    long[] insertPosts(Post... posts);

    @Query("SELECT * FROM posts WHERE authorId = :authorId")
    LiveData<List<Post>> getPostsByAuthor(int authorId);

    //comments
    @Insert
    long[] insertComments(Comment... comments);

    @Query("SELECT * FROM comments WHERE postId = :postId")
    LiveData<List<Comment>> getCommentsByPost(int postId);

}
