package com.example.week1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("select * from items")
    LiveData<List<item>> getAllItems();

    @Insert
    void insertItem(item item);

    @Delete
    void deleteItem(item item);

}
