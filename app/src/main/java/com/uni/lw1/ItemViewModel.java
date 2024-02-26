package com.uni.lw1;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<Note> selectedItem = new MutableLiveData<Note>();
    public void selectItem(Note note) {
        selectedItem.setValue(note);
    }
    public LiveData<Note> getSelectedItem() {
        return selectedItem;
    }
}