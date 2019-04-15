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
        String j = "{\"receipt\":{\"receipt_type\":\"ProductionSandbox\",\"adam_id\":0,\"app_item_id\":0,\"bundle_id\":\"com.sj.sanguo\",\"application_version\":\"15\",\"download_id\":0,\"version_external_identifier\":0,\"receipt_creation_date\":\"2019-04-13 10:32:17 Etc/GMT\",\"receipt_creation_date_ms\":\"1555151537000\",\"receipt_creation_date_pst\":\"2019-04-13 03:32:17 America/Los_Angeles\",\"request_date\":\"2019-04-13 10:32:29 Etc/GMT\",\"request_date_ms\":\"1555151549671\",\"request_date_pst\":\"2019-04-13 03:32:29 America/Los_Angeles\",\"original_purchase_date\":\"2013-08-01 07:00:00 Etc/GMT\",\"original_purchase_date_ms\":\"1375340400000\",\"original_purchase_date_pst\":\"2013-08-01 00:00:00 America/Los_Angeles\",\"original_application_version\":\"1.0\",\"in_app\":[{\"quantity\":\"1\",\"product_id\":\"Money_180\",\"transaction_id\":\"1000000518977336\",\"original_transaction_id\":\"1000000518977336\",\"purchase_date\":\"2019-04-13 03:43:44 Etc/GMT\",\"purchase_date_ms\":\"1555127024000\",\"purchase_date_pst\":\"2019-04-12 20:43:44 America/Los_Angeles\",\"original_purchase_date\":\"2019-04-13 03:43:44 Etc/GMT\",\"original_purchase_date_ms\":\"1555127024000\",\"original_purchase_date_pst\":\"2019-04-12 20:43:44 America/Los_Angeles\",\"is_trial_period\":\"false\"},{\"quantity\":\"1\",\"product_id\":\"Money_700\",\"transaction_id\":\"1000000518932794\",\"original_transaction_id\":\"1000000518932794\",\"purchase_date\":\"2019-04-12 18:27:18 Etc/GMT\",\"purchase_date_ms\":\"1555093638000\",\"purchase_date_pst\":\"2019-04-12 11:27:18 America/Los_Angeles\",\"original_purchase_date\":\"2019-04-12 18:27:18 Etc/GMT\",\"original_purchase_date_ms\":\"1555093638000\",\"original_purchase_date_pst\":\"2019-04-12 11:27:18 America/Los_Angeles\",\"is_trial_period\":\"false\"},{\"quantity\":\"1\",\"product_id\":\"Remove_Ads\",\"transaction_id\":\"1000000518214077\",\"original_transaction_id\":\"1000000518214077\",\"purchase_date\":\"2019-04-11 02:55:25 Etc/GMT\",\"purchase_date_ms\":\"1554951325000\",\"purchase_date_pst\":\"2019-04-10 19:55:25 America/Los_Angeles\",\"original_purchase_date\":\"2019-04-11 02:55:25 Etc/GMT\",\"original_purchase_date_ms\":\"1554951325000\",\"original_purchase_date_pst\":\"2019-04-10 19:55:25 America/Los_Angeles\",\"is_trial_period\":\"false\"},{\"quantity\":\"1\",\"product_id\":\"monthly_Card\",\"transaction_id\":\"1000000518118588\",\"original_transaction_id\":\"1000000518118588\",\"purchase_date\":\"2019-04-10 16:59:40 Etc/GMT\",\"purchase_date_ms\":\"1554915580000\",\"purchase_date_pst\":\"2019-04-10 09:59:40 America/Los_Angeles\",\"original_purchase_date\":\"2019-04-10 16:59:40 Etc/GMT\",\"original_purchase_date_ms\":\"1554915580000\",\"original_purchase_date_pst\":\"2019-04-10 09:59:40 America/Los_Angeles\",\"is_trial_period\":\"false\"}]},\"status\":0,\"environment\":\"Sandbox\"}";
        JSONObject job = JSONObject.parseObject(j);
        System.out.println(job.get("receipt"));
        System.out.println(job.get("status"));
        JSONObject jobRece = job.getJSONObject("receipt");
        System.out.println(jobRece);
        System.out.println(jobRece.get("in_app"));
        System.out.println("in_app");
        String jobIn = String.valueOf(jobRece.get("in_app"));
        List<Map<String, Object>> listKylinTable1 = JSONObject.parseObject(jobIn, List.class);
        System.out.println(listKylinTable1);
        if (listKylinTable1 != null){
            for (Map<String, Object> jobs:listKylinTable1){
                System.out.println(jobs.get("transaction_id"));
            }
        }
    }

}
