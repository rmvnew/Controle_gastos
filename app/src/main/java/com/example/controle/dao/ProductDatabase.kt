package com.example.controle.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.controle.model.Password
import com.example.controle.model.Person
import com.example.controle.model.Product

@Database(
    entities = [Product::class,Person::class,Password::class],version = 6
)
abstract class ProductDatabase :RoomDatabase(){

    abstract fun getProductDao():ProductDao
    abstract fun getPersonDao():PersonDao
    abstract fun getPasswordDao():PasswordDao

    companion object{
       @Volatile private var instance: ProductDatabase? = null
        private val LOCK = Any()

        operator  fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance  = it
            }
        }

        private  fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ProductDatabase::class.java,
            "productDB.db"
        ).build()

    }

}