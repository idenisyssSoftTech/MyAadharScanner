package com.abhiram.qrbarscanner.databases.dbtables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scanneddata")
public class ScannedHistory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid", index = true, defaultValue = "0")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "codetype")
    public String codetype;

    @ColumnInfo(name = "timedate")
    public String timedate;

    @ColumnInfo(name = "data")
    public String data;

    @ColumnInfo(name = "state")
    public String state;

    @ColumnInfo(name = "images")
    public byte[] image;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}