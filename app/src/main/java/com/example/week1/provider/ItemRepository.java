package com.example.week1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private ItemDao myItemDao;
    private LiveData<List<item>> mAllItems;

    ItemRepository(Application application) {
        ItemDatabase db = ItemDatabase.getDatabase(application);
        myItemDao = db.itemDao();
        mAllItems = myItemDao.getAllItems();
    }

    LiveData<List<item>> getAllItems() {
        return myItemDao.getAllItems();
    }

    void insert(item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> myItemDao.insertItem(item));
    }

    void delete(item item){
        myItemDao.deleteItem(item);
    }

    }

