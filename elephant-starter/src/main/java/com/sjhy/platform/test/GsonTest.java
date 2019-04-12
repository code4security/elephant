package com.sjhy.platform.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GsonTest {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"success\": true,\n" +
                "    \"result\": \"{\\\"receipt\\\":{\\\"original_purchase_date_pst\\\":\\\"2019-03-04 01:11:52 America/Los_Angeles\\\", \\\"purchase_date_ms\\\":\\\"1551690712522\\\", \\\"unique_identifier\\\":\\\"6918d74e0a972a059990ab5b62b1a910e94af7c8\\\", \\\"original_transaction_id\\\":\\\"1000000507296115\\\", \\\"bvrs\\\":\\\"1\\\", \\\"transaction_id\\\":\\\"1000000507296115\\\", \\\"quantity\\\":\\\"1\\\", \\\"unique_vendor_identifier\\\":\\\"C99C865F-5A44-4453-9945-59504A9F7B69\\\", \\\"item_id\\\":\\\"1454511821\\\", \\\"original_purchase_date\\\":\\\"2019-03-04 09:11:52 Etc/GMT\\\", \\\"is_in_intro_offer_period\\\":\\\"false\\\", \\\"product_id\\\":\\\"coin_1\\\", \\\"purchase_date\\\":\\\"2019-03-04 09:11:52 Etc/GMT\\\", \\\"is_trial_period\\\":\\\"false\\\", \\\"purchase_date_pst\\\":\\\"2019-03-04 01:11:52 America/Los_Angeles\\\", \\\"bid\\\":\\\"com.sj.sanguo\\\", \\\"original_purchase_date_ms\\\":\\\"1551690712522\\\"}, \\\"status\\\":0}\"\n" +
                "}";
        GsonTest gsonTest = new GsonTest();
        gsonTest.in();
    }

    public void in(){
        String j = "{\n" +
                "\t\"receipt\": {\n" +
                "\t\t\"original_transaction_id\": \"400000533527605\",\n" +
                "\t\t\"in_app\": [{\n" +
                "\t\t\t\t\"quantity\": \"1\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"quantity\": \"3\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"quantity\": \"2\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"status\": 255\n" +
                "}";
        JSONObject job = JSONObject.parseObject(j);
        System.out.println(job.get("receipt"));
        System.out.println(job.get("status"));
        JSONObject jobRece = job.getJSONObject("receipt");
        System.out.println(jobRece);
        System.out.println(jobRece.get("original_transaction_id"));
        System.out.println(jobRece.get("in_app"));
        String jobIn = String.valueOf(jobRece.get("in_apps"));
        List<Map<String, Object>> listKylinTable1 = JSONObject.parseObject(jobIn, List.class);
        System.out.println(listKylinTable1);
        if (listKylinTable1 != null){
            for (Map<String, Object> jobs:listKylinTable1){
                System.out.println(jobs.get("quantity"));
            }
        }
    }

}
