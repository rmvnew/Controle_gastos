package com.example.controle.dao

import androidx.room.*
import com.example.controle.model.Password

@Dao
interface PasswordDao {

    @Insert
    suspend fun addPassword(password: Password)

    @Query("SELECT * FROM Password ORDER BY id DESC")
    suspend fun getAllPassword():List<Password>

    @Insert
    suspend fun addMultplePassword(vararg password: Password)

    @Update
    suspend fun updatePassword(password: Password)

    @Delete
    suspend fun deletePassword(password: Password)

}