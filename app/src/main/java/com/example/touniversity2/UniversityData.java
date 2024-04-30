package com.example.touniversity2;

import android.media.Image;

public class UniversityData {
    //class for BD_ROOM
    private String called_university;
    private String called_program;
    private int point_tofree;
    private int point_topaid;
    private String subjectonEGE;
    private String city;
    private int price;
    private String image;
    private String link;
    private int subject_value;
    private String dvi;

    public UniversityData(String called_university, String called_program, int point_tofree, int point_topaid, String subjectonEGE, String city, int price, String image, String link, int subject_value, String dvi) {
        this.called_university = called_university;
        this.called_program = called_program;
        this.point_tofree = point_tofree;
        this.point_topaid = point_topaid;
        this.subjectonEGE = subjectonEGE;
        this.city = city;
        this.price = price;
        this.image = image;
        this.link = link;
        this.subject_value = subject_value;
        this.dvi = dvi;
    }

    public String getCalled_university() {
        return called_university;
    }

    public String getCalled_program() {
        return called_program;
    }

    public int getPoint_tofree() {
        return point_tofree;
    }

    public int getPoint_topaid() {
        return point_topaid;
    }

    public String getSubjectonEGE() {
        return subjectonEGE;
    }

    public String getCity() {
        return city;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public int getSubject_value() {
        return subject_value;
    }

    public String getDvi() {
        return dvi;
    }

    @Override
    public String toString() {
        return called_university + ' ' +
                called_program + ' ' +
                point_tofree + ' '+
                point_topaid +' '+
               subjectonEGE + ' ' +
                 city + ' ' +
               price + ' '  +
               image + ' ' +
                link + ' ' +
                subject_value + ' '+
                dvi;
    }
}
