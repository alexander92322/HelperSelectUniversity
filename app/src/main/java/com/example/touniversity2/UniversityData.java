package com.example.touniversity2;

import android.media.Image;

public class UniversityData {
    //class for BD_ROOM
    private String called_university;
    private String called_program;
    private int point_tofree;
    private int point_topaid;
    private String subjectonEGE;
    private String learning_form;
    private String city;
    private int price;
    private String image;
    private String link;
    private String desclink;
    private int subject_value;
    private String dvi;

    public UniversityData(String called_university, String called_program, int point_tofree, int point_topaid, String subjectonEGE, String learning_form, String city, int price, String image, String link, String desclink, int subject_value, String dvi) {
        this.called_university = called_university;
        this.called_program = called_program;
        this.point_tofree = point_tofree;
        this.point_topaid = point_topaid;
        this.subjectonEGE = subjectonEGE;
        this.learning_form = learning_form;
        this.city = city;
        this.price = price;
        this.image = image;
        this.link = link;
        this.desclink = desclink;
        this.subject_value = subject_value;
        this.dvi = dvi;
    }


}
