package com.study.UlidongneProject.other;

import java.util.ArrayList;
import java.util.List;

public class PublicMethod {
    public static List<Long> stringToLongList(String str){
        str = str.replaceAll("\\{","").replaceAll("}","");
        String[] strArr = str.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            list.add(Long.valueOf(strArr[i]));
        }
        return list;
    }

    public static String LongArrToString(List<Long> list){
        String str = list.toString();
        return str;
    }
}
