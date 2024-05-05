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
    @Query("select count(*) from University")
    public int getTableSize();
    @Query("delete from University where price==0 and subject_value==1")
    public void deleteItems();

    @Query("select * from University where point_topaid<=:point and subjectonEGE==:subjectonEGE and city==:city and point_tofree!=0")
    public List<University> chooseUniversityPaid(int point,String subjectonEGE, String city);
    @Query("select * from University where  point_tofree<=:point and subjectonEGE==:subjectonEGE and city== :city ")
    public List<University> chooseUniversity(int point, String subjectonEGE,  String city);

    @Query("select * from University where point_topaid<=:point and subjectonEGE==:subjectonEGE and point_tofree!=0")
    public List<University> chooseUniversityPaidRegion(int point, String subjectonEGE);
    @Query("select * from University where point_tofree<=:point and subjectonEGE==:subjectonEGE")
    public List<University> chooseUniversityRegion(int point, String subjectonEGE);
}
