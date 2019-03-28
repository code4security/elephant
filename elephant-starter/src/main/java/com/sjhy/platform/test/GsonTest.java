package com.sjhy.platform.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;

public class GsonTest {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"success\": true,\n" +
                "    \"result\": \"{\\\"receipt\\\":{\\\"original_purchase_date_pst\\\":\\\"2019-03-04 01:11:52 America/Los_Angeles\\\", \\\"purchase_date_ms\\\":\\\"1551690712522\\\", \\\"unique_identifier\\\":\\\"6918d74e0a972a059990ab5b62b1a910e94af7c8\\\", \\\"original_transaction_id\\\":\\\"1000000507296115\\\", \\\"bvrs\\\":\\\"1\\\", \\\"transaction_id\\\":\\\"1000000507296115\\\", \\\"quantity\\\":\\\"1\\\", \\\"unique_vendor_identifier\\\":\\\"C99C865F-5A44-4453-9945-59504A9F7B69\\\", \\\"item_id\\\":\\\"1454511821\\\", \\\"original_purchase_date\\\":\\\"2019-03-04 09:11:52 Etc/GMT\\\", \\\"is_in_intro_offer_period\\\":\\\"false\\\", \\\"product_id\\\":\\\"coin_1\\\", \\\"purchase_date\\\":\\\"2019-03-04 09:11:52 Etc/GMT\\\", \\\"is_trial_period\\\":\\\"false\\\", \\\"purchase_date_pst\\\":\\\"2019-03-04 01:11:52 America/Los_Angeles\\\", \\\"bid\\\":\\\"com.sj.sanguo\\\", \\\"original_purchase_date_ms\\\":\\\"1551690712522\\\"}, \\\"status\\\":0}\"\n" +
                "}";
        
    }

    public void in(){
        String json = "{\n" +
                "    \"success\": true,\n" +
                "    \"result\": \"{\\\"receipt\\\":{\\\"original_purchase_date_pst\\\":\\\"2019-03-04 01:11:52 America/Los_Angeles\\\", \\\"purchase_date_ms\\\":\\\"1551690712522\\\", \\\"unique_identifier\\\":\\\"6918d74e0a972a059990ab5b62b1a910e94af7c8\\\", \\\"original_transaction_id\\\":\\\"1000000507296115\\\", \\\"bvrs\\\":\\\"1\\\", \\\"transaction_id\\\":\\\"1000000507296115\\\", \\\"quantity\\\":\\\"1\\\", \\\"unique_vendor_identifier\\\":\\\"C99C865F-5A44-4453-9945-59504A9F7B69\\\", \\\"item_id\\\":\\\"1454511821\\\", \\\"original_purchase_date\\\":\\\"2019-03-04 09:11:52 Etc/GMT\\\", \\\"is_in_intro_offer_period\\\":\\\"false\\\", \\\"product_id\\\":\\\"coin_1\\\", \\\"purchase_date\\\":\\\"2019-03-04 09:11:52 Etc/GMT\\\", \\\"is_trial_period\\\":\\\"false\\\", \\\"purchase_date_pst\\\":\\\"2019-03-04 01:11:52 America/Los_Angeles\\\", \\\"bid\\\":\\\"com.sj.sanguo\\\", \\\"original_purchase_date_ms\\\":\\\"1551690712522\\\"}, \\\"status\\\":0}\"\n" +
                "}";
        String j = "";
        JSONObject job = JSONObject.parseObject(j);
        System.out.println(job.get("success"));
        JSONObject jab = job.getJSONObject("result");
        System.out.println(jab.get("statusa"));
        JSONObject jib = jab.getJSONObject("receipt");
        System.out.println(jib.get("original_transaction_id"));

        System.out.println(jib.size());
    }

}
