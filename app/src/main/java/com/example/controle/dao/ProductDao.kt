package com.example.controle.dao

import androidx.room.*
import com.example.controle.model.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(prod: Product)

    @Query("SELECT * FROM product ORDER BY id DESC")
    suspend fun getAllProducts():List<Product>

    @Insert
    suspend fun addMultpleProducts(vararg prod: Product)

    @Update
    suspend fun updateProduct(prod: Product)

    @Delete
    suspend fun deleteProduct(prod: Product)



}