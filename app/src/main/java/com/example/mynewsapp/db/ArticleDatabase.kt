package com.example.mynewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynewsapp.models.Article

@Database(
    entities = [Article::class], version = 2 // Increment the version number here
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDAO(): ArticleDAO

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java, "article_db.db"
        )
            //code below is to clear old data on schema change after changing the columns of article table
            //this is not the best way to handle this but for the sake of simplicity we will use this
            //the best way is to use migrations in room database

            .fallbackToDestructiveMigration()
            .build()
    }
}
