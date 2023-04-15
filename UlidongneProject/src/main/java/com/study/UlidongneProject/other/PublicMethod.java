package com.study.UlidongneProject.other;

import java.time.LocalDate;
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
        String[] locationArr = locationBefore.replaceAll("]","").split(",");

        String location = "";
        for(String a : locationArr){
            if(!a.equals(" ") && !a.equals(" undefined")){
                location += a;
            }
        }
        return location.replace("[","");
    }

    public static String locationLastArray(String locationBefore) {
        String[] location = locationBefore.split(" ");
        return location[location.length - 1];
    }

    public static LocalDate convertStringToLocalDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(strDate, formatter);
    }
}
