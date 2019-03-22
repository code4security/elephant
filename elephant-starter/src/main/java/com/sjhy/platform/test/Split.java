package com.sjhy.platform.test;

import java.util.HashMap;
import java.util.Map;

public class Split {
    public static void main(String[] args) {
        String initprop = "1#60";
        String[] prop = initprop.split("&");
        Map<String,String> propMap = new HashMap<>();
        System.out.println(prop.length);
        System.out.println(prop[0]);
            for (int k=0;k<prop.length;k++){
                String[] props = prop[k].split("#");
                System.out.println(props[0]);
                System.out.println(props[1]);
            }
    }
}
