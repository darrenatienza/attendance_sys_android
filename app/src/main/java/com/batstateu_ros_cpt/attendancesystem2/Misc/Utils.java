package com.batstateu_ros_cpt.attendancesystem2.Misc;

import java.util.List;

public class Utils {
    public static int convertToInt(String s){
        int val = 0;
        if(s != null){
            val = Integer.parseInt(s);
        }
        return val;
    }
    // Function to find the index of an element in a primitive array in Java
    public static int find(List<Integer> list , Integer target) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i) == target)
                return i;

        return -1;
    }

}
