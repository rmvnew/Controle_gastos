package com.example.controle.dao

import androidx.room.*
import com.example.controle.model.Person


@Dao
interface PersonDao {

    @Insert
    suspend fun addPerson(person: Person)

    @Query("SELECT * FROM Person ORDER BY id DESC")
    suspend fun getAllPersons(): List<Person>

    @Insert
    suspend fun addMultplePerson(vararg person: Person)

    @Update
    suspend fun updatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)


}