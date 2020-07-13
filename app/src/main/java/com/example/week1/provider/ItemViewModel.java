package com.example.week1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mRepository;
    private LiveData<List<item>> mAllItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<item>> getAllItems() {
        return mAllItems;
    }

    public void insert(item item) {
        mRepository.insert(item);
    }

    public void delete(item item) {
        mRepository.delete(item);
    }

}
