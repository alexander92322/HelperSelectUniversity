package com.example.touniversity2;

import android.media.Image;

public class UniversityData {
    private String called_university;
    private String called_program;
    private int point_tofree;
    private int point_topaid;
    private String subjectonEGE;
    private int learning_form;
    private String city;
    private int price;
    private String image;
    private String link;
    private String desclink;
    private int subject_value;
    private String dvi;

    public UniversityData(String called_university, String called_program, int point_tofree, int point_topaid, String subjectonEGE, int learning_form, String city, int price, String image, String link, String desclink, int subject_value, String dvi) {
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

    public int getLearning_form() {
        return learning_form;
    }

    public void setLearning_form(int learning_form) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesclink() {
        return desclink;
    }

    public void setDesclink(String desclink) {
        this.desclink = desclink;
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
