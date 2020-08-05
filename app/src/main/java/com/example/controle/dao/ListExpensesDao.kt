package com.example.controle.dao

import androidx.room.*
import com.example.controle.model.ListExpenses


@Dao
interface ListExpensesDao {

    @Insert
    suspend fun addList(lista: ListExpenses)

    @Query("SELECT * FROM ListExpenses ORDER BY id DESC")
    suspend fun getAllList(): List<ListExpenses>

    @Insert
    suspend fun addMultpleList(vararg lista: ListExpenses)

    @Update
    suspend fun updateList(lista: ListExpenses)

    @Delete
    suspend fun deleteList(lista: ListExpenses)

}