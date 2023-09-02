package com.idenisyss.myaadharscanner.databases.dbtables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sanneddata")
public class ScannedHistory {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "codetype")
    public String codetype;

    @ColumnInfo(name = "timedate")
    public String timedate;

    @ColumnInfo(name = "data")
    public String data;
}

