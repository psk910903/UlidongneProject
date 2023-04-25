package com.study.UlidongneProject.other;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PublicMethod {


    public static List<Long> stringToLongList(String str){
        str = str.replaceAll("\\{","").replaceAll("}","");
        String[] strArr = str.split(",");
        List<Long> list = new ArrayList<>();
        if(str.length()>0) {
            for (int i = 0; i < strArr.length; i++) {
                list.add(Long.valueOf(strArr[i]));
            }
        }
        return list;
    }

    public static String longListToString(List<Long> list){
        String str = list.toString();
        str = str.replaceAll("\\p{Z}", "");
        str = "{" + str.substring(1, str.length()-1) + "}";
        return str;
    }

    public static String location(String locationBefore) {
        String[] locationArr = locationBefore.replaceAll("]","").replaceAll("  "," ").split(" ");
        String location = "";
        for(String a : locationArr){
            System.out.println("a: " + a);
            if(!a.equals(" ") && !a.equals("undefined")){
                location += " " + a;
            }
        }
        return location.trim().replace("[","");
    }

    public static String locationLastArray(String locationBefore) {
        String[] location = locationBefore.split(" ");
        return location[location.length - 1];
    }

    public static LocalDate convertStringToLocalDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(strDate, formatter);
    }

    public static LocalTime timeComparison(String strTime) {
        System.out.println("strTime = " + strTime);
        String[] timeArr = strTime.split(" ");

        String amPm = timeArr[0];
        System.out.println("amPm = " + amPm);
        String time = timeArr[1].split(":")[0];
        System.out.println("time = " + time);
        String minute = timeArr[1].split(":")[1];
        System.out.println("minute = " + minute);
        String newTime ="";
        if (amPm.equals("오후") && Integer.parseInt(time) != 12) {
            newTime = String.valueOf(Integer.parseInt(time) + 12);
            System.out.println("newTime = " + newTime);
        } else {
            newTime = time;
            System.out.println("newTime = " + newTime);
        }
        return LocalTime.parse(String.format("%02d", Integer.parseInt(newTime)) + ":" + String.format("%02d", Integer.parseInt(minute)));

    }

    public static LocalTime timeComparisonTest(String strTime) {
        System.out.println("strTime = " + strTime);
        String[] timeArr = strTime.split(" ");
        String amPm = timeArr[0];
        String time = timeArr[1].split(":")[0];
        String minute = timeArr[1].split(":")[1];

        String newTime ="";
        if (amPm.equals("오후") && Integer.parseInt(time) != 12) {
            newTime = String.valueOf(Integer.parseInt(time) + 12);
        } else {
            newTime = time;
        }
//        return LocalTime.parse(newTime + ":" + minute);
        return LocalTime.parse(String.format("%02d", Integer.parseInt(newTime)) + ":" + String.format("%02d", Integer.parseInt(minute)));
    }
}
