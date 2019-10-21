package com.nikoarap.bloggingapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nikoarap.bloggingapp.models.Author;
import com.nikoarap.bloggingapp.models.Comment;
import com.nikoarap.bloggingapp.models.Post;

import java.util.List;


@Dao
public interface AppDao {

    //authors
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAuthors(Author... authors); //... gets a list

    @Query("SELECT * FROM authors")
    LiveData<List<Author>> getAuthors();

    //posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(Post... posts);


    @Query("SELECT * FROM posts WHERE authorId = :authorId")
    LiveData<List<Post>> getPostsByAuthor(int authorId);

    //comments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(Comment... comments);


    @Query("SELECT * FROM comments WHERE postId = :postId")
    LiveData<List<Comment>> getCommentsByPost(int postId);

}
