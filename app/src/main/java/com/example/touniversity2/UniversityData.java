package com.example.touniversity2;

import android.media.Image;

public class UniversityData {
    private Image image;
    private int point_tofree;
    private int point_topaid;
    private int price;
    private String called_university;
    private String called_program;
    private String learning_form;
    private String city;
    private String subjectonEGE;
    private String link;

    public UniversityData(Image image, int point_tofree, int point_topaid, int price, String called_university, String called_program, String learning_form, String city, String subjectonEGE, String link) {
        this.image = image;
        this.point_tofree = point_tofree;
        this.point_topaid = point_topaid;
        this.price = price;
        this.called_university = called_university;
        this.called_program = called_program;
        this.learning_form = learning_form;
        this.city = city;
        this.subjectonEGE = subjectonEGE;
        this.link = link;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getSubjectonEGE() {
        return subjectonEGE;
    }

    public void setSubjectonEGE(String subjectonEGE) {
        this.subjectonEGE = subjectonEGE;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
