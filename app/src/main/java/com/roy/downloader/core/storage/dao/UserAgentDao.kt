package com.roy.downloader.core.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roy.downloader.core.model.data.entity.UserAgent

@Dao
interface UserAgentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(agent: UserAgent?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(agent: Array<UserAgent?>?)

    @Delete
    fun delete(agent: UserAgent?)

    @Query("SELECT * FROM UserAgent")
    fun observeAll(): LiveData<List<UserAgent?>?>?
}
