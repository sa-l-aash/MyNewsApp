package com.example.mynewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import com.example.mynewsapp.models.Article
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

interface ArticleDAO {

    @Dao
    interface ArticleDAO{
        @Insert(onConflict=OnConflictStrategy.REPLACE)
        suspend fun upsert(article: Article): Long

        @Query("SELECT * FROM articles")
        fun getAllArticles():LiveData<List<Article>>

        @Delete
        suspend fun deleteArticle(article: Article)

    }
}