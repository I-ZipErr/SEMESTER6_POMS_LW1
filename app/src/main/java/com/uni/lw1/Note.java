package com.uni.lw1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Note implements Parcelable {
    private int id;
    private String name;
    private String text;
    private String group;
    private boolean is_favorite;
    private String creation_date;
    private String modification_date;

    public Note(int id, String name, String text, String group, boolean is_favorite, String creation_date,
                String modification_date) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.group = group;
        this.is_favorite = is_favorite;
        this.creation_date = creation_date;
        this.modification_date = modification_date;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }
    public void setModification_date(String modification_date) {this.modification_date = modification_date;}
    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int getId() { return id; }
    public String getName() {
        return name;
    }
    public String getText() {
        return text;
    }
    public String getGroup() {
        return group;
    }
    public boolean getIs_favorite() {
        return is_favorite;
    }
    public int getIs_favoriteInt() {
        if(this.getIs_favorite()) return 1;
        return 0;
    }
    public String getCreation_date() {
        return creation_date;
    }
    public String getModification_date() {
        return modification_date;
    }

    public void setCurrentMod_date(){
        this.modification_date =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source.readInt(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readBoolean(),
                    source.readString(),
                    source.readString());
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(text);
        dest.writeString(group);
        dest.writeBoolean(is_favorite);
        dest.writeString(creation_date);
        dest.writeString(modification_date);
    }
}
