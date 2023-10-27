package com.paltech.utils.slack;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class EyesSlack {
  //  @SuppressWarnings("unused")
//    public static void post(TestResults res, String slackWebhookURL){
//        String color = "";
//        String fallback = "Test Result";
//
//        if (res.isAborted()){
//            color = "A9A9A9";
//        }
//        switch (res.getStatus()){
//            case Failed:
//                color = "FF0000";
//                fallback = "Test Failed: " + res.getName();
//                break;
//            case Passed:
//                color = "36a64f";
//                fallback = "Test Successfully Completed: " + res.getName();
//                break;
//            case Unresolved:
//                color = "FFA500";
//                fallback = "Test Mismatches Found: " + res.getName();
//                break;
//        }
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        try {
//            HttpPost request = new HttpPost(slackWebhookURL);
//            StringEntity param = new StringEntity("{\n" +
//                    "\"attachments\" + [\n" +
//                    "               \"fallback\": \""+fallback+".\n" +
//                    "               \"color\": \"#"+color+"\",\n"+
//                    "               \"pretext\": \""+res.getStatus().toString() + "\",\n"+
//                    "               \"title\": \"See Results\", \n"+
//                    "               \"fields\": [\n" +
//                    "                   {\n" +
//                    "                           \"title\": \"Branch\",\n"+
//                    "                           \"value\": \""+res.getBranchName()+"\",\n" +
//                    "                           \"short\": true\n" +
//                    "                           }\n,"+
//                    "                           {\n"+
//                    "                           \"title\": \"Browser\",\n"+
//                    "                           \"value\": \""+res.getHostApp()+"\",\n" +
//                    "                           \"short\": true\n" +
//                    "                           }\n,"+
//                    "                           {\n"+
//                    "                           \"title\": \"Duration\",\n"+
//                    "                           \"value\": \""+res.getDuration()+" Seconds\",\n"+
//                    "                           \"short\": true\n" +
//                    "                           }\n,"+
//                    "                           {\n"+
//                    "                           \"title\": \"Steps\",\n"+
//                    "                           \"value\": \""+res.getSteps()+" Step\",\n"+
//                    "                           \"short\": true\n" +
//                    "                           }\n,"+
//                    "                   ],\n"+
//                    "                   \"footer\": \"Test Start Time\",\n"+
//                    "                   \"ts\": "+ res.StartedAt().getTimeInMillis()/1000 + "\n" +
//                    "           }\n"+
//                    "        ]\n"
//                    "}");
//            request.addHeader("content-type", "application/x-www-form-urlencoded");
//            request.setEntity(params);
//            HttpResponse response = httpClient.execute(request);
//
//        } catch (Exception ex){
//            //handle exception here
//        }
//    }
}

