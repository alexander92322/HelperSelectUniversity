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
    @Query("select * from UniversityDB where called_program==:calledprogram")
    public List<University> getUniversity(String calledprogram);

}
