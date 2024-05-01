package com.example.touniversity2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "University")
public class University {
    @ColumnInfo(name = "university_id")
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "called_university")
    String called_university;
    @ColumnInfo(name = "called_program")
    String called_program;
    @ColumnInfo(name = "point_tofree")
    int point_tofree;
    @ColumnInfo(name = "point_topaid")
    int point_topaid;
    @ColumnInfo(name = "subjectonEGE")
    String subjectonEGE;
    @ColumnInfo(name = "learning_form")
    String learning_form;
    @ColumnInfo(name = "city")
    String city;
    @ColumnInfo(name = "price")
    int price;
    @ColumnInfo(name = "link")
    String link;
    @ColumnInfo(name = "subject_value")
    int subject_value;
    @ColumnInfo(name = "dvi")
    String dvi;
    @Ignore
    public University(){

    }

    public University(String called_university, String called_program, int point_tofree, int point_topaid, String subjectonEGE, String learning_form, String city, int price, String link, int subject_value, String dvi) {
        this.id = 0;
        this.called_university = called_university;
        this.called_program = called_program;
        this.point_tofree = point_tofree;
        this.point_topaid = point_topaid;
        this.subjectonEGE = subjectonEGE;
        this.learning_form = learning_form;
        this.city = city;
        this.price = price;
        this.link = link;
        this.subject_value = subject_value;
        this.dvi = dvi;
    }
//    public University(){
//
//    }


    public String getCalled_university() {
        return called_university;
    }

    public void setCalled_university(String called_university) {
        this.called_university = called_university;
    }

    public String getCalled_program() {
        return called_program;
    }

    public void setCalled_program(String called_program) {
        this.called_program = called_program;
    }

    public int getPoint_tofree() {
        return point_tofree;
    }

    public void setPoint_tofree(int point_tofree) {
        this.point_tofree = point_tofree;
    }

    public int getPoint_topaid() {
        return point_topaid;
    }

    public void setPoint_topaid(int point_topaid) {
        this.point_topaid = point_topaid;
    }

    public String getSubjectonEGE() {
        return subjectonEGE;
    }

    public void setSubjectonEGE(String subjectonEGE) {
        this.subjectonEGE = subjectonEGE;
    }

    public String getLearning_form() {
        return learning_form;
    }

    public void setLearning_form(String learning_form) {
        this.learning_form = learning_form;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSubject_value() {
        return subject_value;
    }

    public void setSubject_value(int subject_value) {
        this.subject_value = subject_value;
    }

    public String getDvi() {
        return dvi;
    }

    public void setDvi(String dvi) {
        this.dvi = dvi;
    }
}
