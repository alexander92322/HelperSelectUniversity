package com.example.touniversity2;

import java.util.ArrayList;

public class AbiturientData {
    private static int point;
    private static boolean paid;
    private static boolean top;
    private static String educational_place;
    private static ArrayList<String> subject;

    public AbiturientData() {
    }

    public static int getPoint() {
        return point;
    }

    public static void setPoint(int point) {
        AbiturientData.point = point;
    }

    public static boolean isPaid() {
        return paid;
    }

    public static void setPaid(boolean paid) {
        AbiturientData.paid = paid;
    }

    public static boolean isTop() {
        return top;
    }

    public static void setTop(boolean top) {
        AbiturientData.top = top;
    }

    public static String getEducational_place() {
        return educational_place;
    }

    public static void setEducational_place(String educational_place) {
        AbiturientData.educational_place = educational_place;
    }

    public static ArrayList<String> getSubject() {
        return subject;
    }

    public static void setSubject(ArrayList<String> subject) {
        AbiturientData.subject = subject;
    }
}
