package com.paltech.utils;

import com.paltech.constant.Helper;
import com.paltech.driver.DriverManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author isabella.huynh
 * created on Jun/5/2019.
 */

public class WSUtils {
    /**
     * Sending a API POST request to get HttpResponse
     * @param url API url
     * @param header "application/json;charset=UTF-8" or "application/json"
     * @param jsn {"name" : "value", "name1":"value1"}
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse sendPOSTRequest(String url, String header, String jsn,String accept) throws IOException {
        Helper helper = new Helper();
        boolean isSecure = url.contains("https://");
        CookieStore cookieStore = helper.getBrowserCookies(DriverManager.getDriver().getCookies(), isSecure);
        return helper.sendPostRequest(url, header, jsn, cookieStore, accept);
    }

    public static HttpResponse sendPOSTRequestWithCookies(String url, String header, String jsn, String cookies,String accept) throws IOException {
        Helper helper = new Helper();
        return helper.sendPostRequestWithCookies(url, header, jsn, cookies, accept);
    }

    public static HttpResponse sendPOSTRequestWithCookiesHasHeader(String url, String header, String jsn, String cookies,String accept,String headerParm, String headerValue) throws IOException {
        Helper helper = new Helper();
        return helper.sendPostRequestWithCookiesHasHeader(url, header, jsn, cookies, accept,headerParm,headerValue);
    }

    public static HttpResponse sendPOSTRequestDynamicHeaders(String url,  String jsn, Map<String,String> headers) throws IOException {
        Helper helper = new Helper();
        return helper.sendPostRequestWithCookiesHasDynamicHeaders(url,jsn, headers);
    }
    public static HttpResponse sendPUTRequestDynamicHeaders(String url,  String jsn, Map<String,String> headers) throws IOException {
        Helper helper = new Helper();
        return helper.sendPutRequest(url, jsn, headers);
    }

    public static int getPOSTResponseCodeWithCookies(String url, String header, String jsn, String cookies,String accept) throws IOException {
        HttpResponse response = WSUtils.sendPOSTRequestWithCookies(url, header, jsn, cookies, accept);
        return response.getStatusLine().getStatusCode();
    }

    public static String getPOSTResponsePhraseWithCookies(String url, String header, String jsn, String cookies,String accept) throws IOException {
        HttpResponse response = WSUtils.sendPOSTRequestWithCookies(url, header, jsn, cookies,accept);
        return response.getStatusLine().getReasonPhrase();
    }

    public static JSONObject getPOSTJSONObjectWithCookies(String url, String headers, String jsn, String cookies,String accept) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequestWithCookies(url, headers, jsn, cookies,accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }
    public static JSONObject getPOSTJSONObjectWithCookiesHasHeader(String url, String headers, String jsn, String cookies,String accept,String headerPram, String headerValue) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequestWithCookiesHasHeader(url, headers, jsn, cookies,accept,headerPram,headerValue);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }
    public static JSONObject getPOSTJSONObjectWithDynamicHeaders(String url, String jsn, Map<String, String> headers) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequestDynamicHeaders(url,jsn,headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }
    public static JSONObject getPUTJSONObjectWithDynamicHeaders(String url, String jsn, Map<String, String> headers) {
        try {
            HttpResponse response = WSUtils.sendPUTRequestDynamicHeaders(url,jsn,headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static HttpResponse sendGETRequestWithCookies(String url, String contentType, String cookies) throws IOException {
        Helper helper = new Helper();
        return helper.sendGETRequestWithCookies(url, contentType, cookies,null);
    }

    public static HttpResponse sendGETRequestWithCookies(String url, String contentType, String cookies,String  accept) throws IOException {
        Helper helper = new Helper();
        return helper.sendGETRequestWithCookies(url, contentType, cookies,accept);
    }
    public static HttpResponse sendGETRequestHasHeader(String url, String contentType, String cookies,String accept, String headerParam, String headerValue) throws IOException {
        Helper helper = new Helper();
        return helper.sendGETRequestHasHeader(url, contentType, cookies,accept,headerParam,headerValue);
    }
    public static JSONObject sendGETRequestWithCookiesHasHeader(String url, String contentType, String cookies,String accept,String headerParam, String headerValue) {
        try {
            HttpResponse response = WSUtils.sendGETRequestHasHeader(url, contentType, cookies, accept,headerParam,headerValue);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONObjectWithCookies");
            return null;
        }
    }

    public static JSONObject getGETJSONObjectWithCookies(String url, String contentType, String cookies) {
        return getGETJSONObjectWithCookies(url,contentType,cookies,null);
    }

    public static JSONObject getGETJSONObjectWithCookies(String url, String contentType, String cookies,String accept) {
        try {
            HttpResponse response = WSUtils.sendGETRequestWithCookies(url, contentType, cookies, accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONObjectWithCookies");
            return null;
        }
    }

    public static JSONArray getPOSTJSONArrayWithCookies(String url, String headers, String jsn, String cookies,String accept) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequestWithCookies(url, headers, jsn, cookies,accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            String output = strBuilder.toString();
            return new JSONArray(output);
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getPOSTJSONArrayWithCookies");
            return null;
        }
    }

    public static HttpResponse sendDELETERequest(String url) throws IOException {
        Helper helper = new Helper();
        boolean isSecure = url.contains("https://");
        CookieStore cookieStore = helper.getBrowserCookies(DriverManager.getDriver().getCookies(), isSecure);
        return helper.sendDeleteRequest(url, null, cookieStore);
    }

    public static int getDELETEResponseCode(String url) {
        try {
            HttpResponse response = WSUtils.sendDELETERequest(url);
            return response.getStatusLine().getStatusCode();
        } catch (IOException ex) {
            return 0;
        }
    }

    public static int getPOSTResponseCode(String url, String headers, String jsn,String accept) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequest(url, headers, jsn,accept);
            return response.getStatusLine().getStatusCode();
        } catch (IOException ex) {
            System.err.println("ERROR: IOException occurs at getPOSTResponseCode");
            return -1;
        }
    }

    public static JSONArray getPOSTJSONArrayResponse(String url, String headers, String jsn,String accept) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequest(url, headers, jsn,accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            String output = strBuilder.toString();
            return new JSONArray(output);
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static JSONObject getPOSTJSONObjectResponse(String url, String headers, String jsn,String accept) {
        try {
            HttpResponse response = WSUtils.sendPOSTRequest(url, headers, jsn,accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    /**
     * Getting GET response from URL
     * @param url is sent to the server
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse sendGETRequest(String url) throws IOException {
        return WSUtils.sendGETRequest(url, null);
    }

    /**
     * Getting HttpResponse by sending GET request
     * @param url is sent to the server
     * @param contentType "application/json;charset=UTF-8" or "application/json"
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse sendGETRequest(String url, String contentType) throws IOException {
        return sendGETRequest(url,contentType,null);
    }

    public static HttpResponse sendGETRequest(String url, String contentType,String accept) throws IOException {
        Helper helper = new Helper();
        boolean isSecure = url.contains("https://");
        CookieStore cookieStore = helper.getBrowserCookies(DriverManager.getDriver().getCookies(), isSecure);
        return helper.sendGetRequest(url, contentType, cookieStore,accept);
    }
    /**
     * Getting Response Code from URL
     * @param url is sent to the server
     * @return 200, 400, 401 etc.
     * @throws IOException
     */
    public static int getGETResponseCode(String url, String contentType) throws IOException{
        HttpResponse response = WSUtils.sendGETRequest(url, contentType);
        return response.getStatusLine().getStatusCode();
    }

    /**
     * Getting HttpResponse by sending GET request
     * @param url is sent to the server
     * @return HttpResponse
     * @throws IOException
     */
    public static int getGETResponseCode(String url) throws IOException{
        HttpResponse response = WSUtils.sendGETRequest(url, null);
        return response.getStatusLine().getStatusCode();
    }

    /**
     * Getting JSONObject when sending GET request
     * @param url to get data
     * @param contentType "application/json;charset=UTF-8" or "application/json" or null
     * @return JSONObject
     */
    public static JSONObject getGETJSONResponse(String url, String contentType) {
        return getGETJSONResponse(url,contentType,null);
    }
    public static JSONObject getGETJSONResponse(String url, String contentType,String accept) {
        try {
            HttpResponse response = WSUtils.sendGETRequest(url, contentType,accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    /**
     * Getting JSONObject when sending GET request
     * @param url to get data
     * @param contentType "application/json;charset=UTF-8" or "application/json" or null
     * @return JSONArray
     */
    public static JSONArray getGETJSONArrayResponse(String url, String contentType,String accept){
        try {
            HttpResponse response = WSUtils.sendGETRequest(url, contentType,accept);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONArray(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }
    /**
     * Getting JSONObject when sending GET request
     * @param url to get data
     * @param contentType "application/json;charset=UTF-8" or "application/json" or null
     * @return JSONArray
     */
    public static JSONArray getGETJSONArrayWithCookies(String url, String contentType,String cookies,String accept){
        try {
            //HttpResponse response = WSUtils.sendGETRequest(url, contentType);
            HttpResponse response = WSUtils.sendGETRequestWithCookies(url, contentType, cookies,accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONArray(strBuilder.toString());

        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }
}
