package com.example.touniversity2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {University.class}, version = 1)
public abstract class UniversityDatabase extends RoomDatabase {
    public abstract UniversityDAO getUniversityDAO();
}
