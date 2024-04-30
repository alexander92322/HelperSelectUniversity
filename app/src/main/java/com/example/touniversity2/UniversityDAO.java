package com.example.touniversity2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UniversityDAO {
    @Insert
    public void addUniversity(University university);
    @Update
    public void updateUniversity(University university);
    @Delete
    public void deleteUniversity(University university);

    @Query("select * from University")
    public List<University> getAllUniversity();

    @Query("select * from University where university_id==:university_id")
    public University getUniversity(int university_id);
}
