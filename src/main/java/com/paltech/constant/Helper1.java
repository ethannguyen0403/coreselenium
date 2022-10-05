package com.paltech.constant;

import com.paltech.driver.DriverManager;
import com.google.common.net.HttpHeaders;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author isabella.huynh
 * created on Nov/9/2019.
 */
public class Helper1 {
    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * Bypassing captcha for BackOffice app
     *
     * @param sosURL       sos url
     * @param urlDashBoard url dashboard of BackOffice
//     * @param params       username logs in
     *                     List<BaseNameValuePair> lst = new ArrayList<>();
     *                     lst.add(new BaseNameValuePair("username", "liam001"))
     *                     lst.add(new BaseNameValuePair("password", "1234qwer"))
     *                     lst.add(new BaseNameValuePair("captcha", "1234"))
     * @throws Exception
     */
//    public static void loginBOIgnoreCaptcha(String sosURL, String urlDashBoard, List<NameValuePair> params, boolean isRaise) throws Exception {
//        String java_home = System.getenv("JAVA_HOME");
//        if (java_home.contains("Java")) {
//            String certificatesTrustStorePath = String.format("%s\\jre\\lib\\security\\cacerts", java_home);
//            System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
//            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//        } else {
//            throw new Exception("Error: JAVA_HOME isn't set");
//        }
//
//        try {
//            // 1. getting web-driver cookie
//            Set<Cookie> browserCookies = DriverManager.getDriver().getCookies();
//            Helper1 h = new Helper1();
//            boolean isSecure = sosURL.contains("https://");
//            // 2. transferring browser's cookies to session
//            CookieStore cookieStore = h.getBrowserCookies(browserCookies, isSecure);
//
//            HttpResponse responsePost = h.sendPostRequest(sosURL, params, cookieStore, isRaise);
//            List<Cookie> lstPOSTCookies = h.getCookies(responsePost);
//
//            // 3. passing POST's cookies to browser's cookies
//            if (isRaise && lstPOSTCookies.size() < 2) {
//                throw new Exception(String.format("Error: Can't get SLID and u cookies when using '%s' and '%s'", params.get(0), params.get(1)));
//            }
//            for (Cookie c : lstPOSTCookies) // SLID, u
//            {
//                DriverManager.getDriver().addCookie(c);
//            }
//            System.out.println(String.format("Debug: Completing logging in username '%s'", params.get(0)));
//            DriverManager.getDriver().get(urlDashBoard);
//
//        }
//        catch (Exception ex) {
//            System.err.println(String.format("ERROR: Exception occurs by '%s'", ex.getMessage()));
//            DriverManager.quitAll();
//            throw new Exception(ex.getMessage());
//        }
//    }

    public static void loginBOIgnoreCaptcha(String sosURL, String urlDashBoard, String username, String password, boolean isRaise) throws Exception {
        String java_home = System.getenv("JAVA_HOME");
        if (java_home.contains("Java")) {
            String certificatesTrustStorePath = String.format("%s\\jre\\lib\\security\\cacerts", java_home);
            System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        } else {
            throw new Exception("Error: JAVA_HOME isn't set");
        }
        //Decrypt password
        String passwordDecrypt = StringUtils.decrypt(password);
        try {
            // 1. getting web-driver cookie
            Set<Cookie> browserCookies = DriverManager.getDriver().getCookies();
            Helper1 h = new Helper1();

            boolean isSecure = sosURL.contains("https://");
            // 2. transferring browser's cookies to session
            CookieStore cookieStore = h.getBrowserCookies(browserCookies, isSecure);

            String api = String.format("%s?username=%s&password=%s&captcha=8888", sosURL, username, passwordDecrypt);
            System.out.println("sent login info with username "+username);
             HttpResponse responsePost = h.sendPostRequest(api, null, cookieStore,Configs.HEADER_JSON);
            System.out.println("after call login "+ responsePost.getStatusLine().toString());
            List<Cookie> lstPOSTCookies = h.getCookies(responsePost);

           // List<Cookie> lstPOSTCookies = h.getBrowserCookies(browserCookies,true);

            // 3. passing POST's cookies to browser's cookies
            if (isRaise && lstPOSTCookies.size() < 2) {
                throw new Exception(String.format("Error: Can't get SLID and u cookies when using '%s' and '%s'", username, password));
            }
            for (Cookie c : lstPOSTCookies) // SLID, u
            {
                DriverManager.getDriver().addCookie(c);
            }
            System.out.println(String.format("Debug: Completing logging in username '%s'", username));
            DriverManager.getDriver().getToAvoidTimeOut(urlDashBoard);

        }
        catch (Exception ex) {
            System.err.println(String.format("ERROR: Exception occurs by '%s'", ex.getMessage()));
//            DriverManager.quitAll();
//            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Bypassing captcha for FE
     * @param sosURL       login url
     * @param urlDashBoard url dashboard
     * @param userName     username logs in
     * @param password     password logs in
     * @param isRaise      True if you want to throw exception
     * @throws Exception
     */
    public static void loginFairExchange(String sosURL, String urlDashBoard, String userName, String password, boolean isRaise) throws Exception {
        String java_home = System.getenv("JAVA_HOME");
        if (java_home.contains("Java")) {
            String certificatesTrustStorePath = String.format("%s\\jre\\lib\\security\\cacerts", java_home);
            System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
           System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
           //QAT enviroment cannot not login => ignoreHttpsHost to login successfully
            System.setProperty("org.jboss.security.ignoreHttpsHost","true");

        } else {
            throw new Exception("Error: JAVA_HOME isn't set");
        }
        //Decrypt password
        String passwordDecrypt = StringUtils.decrypt(password);

        try {
            // 1. getting web-driver cookie
            Set<Cookie> browserCookies = DriverManager.getDriver().getCookies();
            Helper1 h = new Helper1();
            // 2. transferring browser's cookies to session
//            boolean isSecure = sosURL.contains("https://");
            CookieStore cookieStore = h.getBrowserCookies(browserCookies);

            // 2. passing web-driver's cookies to POST's session, send this POST request, and then get POST's cookies
            sosURL = String.format("%s?loginId=%s&password=%s", sosURL, userName, passwordDecrypt);
            HttpResponse responsePost = h.sendPostRequest(sosURL, Configs.HEADER_FORM_URLENCODED, cookieStore,Configs.HEADER_JSON);
            if (responsePost == null && isRaise){
                throw new Exception("Exception: Unauthorized occurs at Login page because responsePost is null");
            }
            List<Cookie> lstPOSTCookies = h.getCookies(responsePost);

            // 3. passing POST's cookies to browser's cookies
            if (isRaise && lstPOSTCookies.size() < 2) {
                throw new Exception(String.format("Exception: Can't get SID and MESESSION cookies when using '%s' and '%s'", userName, password));
            }
            for (Cookie c : lstPOSTCookies) // SLID, MESESSION
            {
                DriverManager.getDriver().addCookie(c);
            }
            DriverManager.getDriver().get(urlDashBoard);
            System.out.println(String.format("Info: Completing logging in username '%s'", userName));

        } catch (TimeoutException ex) {
            System.err.println("Exception: TimeoutException occurs at loginFairExchange");
        } catch (RuntimeException ex) {
            System.err.println("Exception: Exception occurs at loginFairExchange");
        } catch (Exception ex) {
            System.err.println(String.format("Exception: Exception occurs by '%s'", ex.getMessage()));
//            DriverManager.quitAll();
            throw new Exception("Exception: Exception occurs at Login page bypass captcha by " + ex.getMessage());
        }
    }


    public static boolean loginAgentIgnoreCaptchaTest(String sosURL, String sosValidationURL, String dashBoardURL, String userName, String password, String securityCode) throws Exception {
        String java_home = System.getenv("JAVA_HOME");
        if (java_home.contains("Java")) {
            String certificatesTrustStorePath = String.format("%s\\jre\\lib\\security\\cacerts", java_home);
            System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        } else {
            throw new Exception("Error: JAVA_HOME isn't set. Please help JAVA_HOME set");
        }
        //Decrypt password and security code
        String passwordDecrypt = StringUtils.decrypt(password);
        String securityDecrypt = StringUtils.decrypt(securityCode);
        try {
            // 1. getting web-driver cookie
            Set<Cookie> browserCookies = DriverManager.getDriver().getCookies();
            Helper1 h = new Helper1();
            // 2. transferring browser's cookies to session
            boolean isSecure = sosURL.contains("https://");
            CookieStore cookieStore = h.getBrowserCookies(browserCookies, isSecure);

            // 2. passing web-driver's cookies to POST's session, send this POST request, and then get POST's cookies
            String json = "{\"username\":\"" + userName + "\",\"password\":\"" + passwordDecrypt + "\"}";

            System.out.println("Debug: Starting step 1");
            HttpResponse responsePost = h.sendPostRequest(sosURL, "application/json", json, cookieStore,"application/json" );

            if (responsePost == null){
                System.err.println(String.format("Debug: Sending this POST request '%s' is null", sosURL));
                return false;
            }
            // 3. passing POST's cookies to browser's cookies
            if (responsePost.getStatusLine().getStatusCode() != 200) {
                throw new Exception(String.format("Exception: Can't get SLID and u cookies when using '%s' and '%s'", userName, password));
            }
            System.out.println("Debug: Ended step 1");


            // 4. bypassing security code
            String apiValidateGet = String.format("%s?securityCode=%s&t=%s", sosValidationURL, securityDecrypt,DateUtils.getMilliSeconds());
            HttpResponse responseValidationPost = h.sendGetRequest(apiValidateGet, null, cookieStore);
            if (responseValidationPost == null){
                System.err.println(String.format("Exception: Sending this POST request '%s' is null", sosValidationURL));
                return false;
            }

            List<Cookie> lstPOSTValidationCookies = h.getCookies(responseValidationPost);
            if (responseValidationPost.getStatusLine().getStatusCode() != 200 && lstPOSTValidationCookies.size() < 2) {
                throw new Exception(String.format("Exception: Can't get SLID and u cookies when using username '%s', password '%s', and '%s'", userName, password, securityCode));
            }
            List<Cookie> lstPOSTCookies = h.getCookies(responsePost);
            for (Cookie c : lstPOSTCookies) {
                DriverManager.getDriver().addCookie(c);
            }
            for (Cookie c : lstPOSTValidationCookies) // after sending Validation POST, we got SLID, u
            {
                DriverManager.getDriver().addCookie(c);
            }
            System.out.println(String.format("Info: Completing logging in username '%s'", userName));
            return DriverManager.getDriver().getToAvoidTimeOut(dashBoardURL);

        } catch (TimeoutException ex) {
            System.err.println("Exception: TimeoutException occurs at loginPS3838IgnoreCaptcha");
        } catch (RuntimeException ex) {
            System.err.println("Exception: Exception occurs at loginPS3838IgnoreCaptcha");
        }  catch (Exception ex) {
            System.err.println(String.format("Error: Exception occurs by '%s'", ex.getMessage()));
//            DriverManager.quitAll();
            System.err.println("Error: Exception occurs at Login page bypass captcha by " + ex.getMessage());
        }
        return false;
    }

    public static boolean loginAgentIgnoreCaptchaTest(String sosURL, String loginURL, String userName, String password) throws Exception {
        String java_home = System.getenv("JAVA_HOME");
        if (java_home.contains("Java")) {
            String certificatesTrustStorePath = String.format("%s\\jre\\lib\\security\\cacerts", java_home);
            System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        } else {
            throw new Exception("Error: JAVA_HOME isn't set. Please help JAVA_HOME set");
        }
        //Decrypt password and security code
        String passwordDecrypt = StringUtils.decrypt(password);
        try {
            // 1. getting web-driver cookie
            Set<Cookie> browserCookies = DriverManager.getDriver().getCookies();
            Helper1 h = new Helper1();
            // 2. transferring browser's cookies to session
            CookieStore cookieStore = h.getBrowserCookies(browserCookies);

            // 2. passing web-driver's cookies to POST's session, send this POST request, and then get POST's cookies
            String json = "{\"username\":\"" + userName + "\",\"password\":\"" + passwordDecrypt + "\"}";

            System.out.println("Debug: Starting step 1");
            HttpResponse responsePost = h.sendPostRequest(sosURL, "application/json", json, cookieStore, "application/json");
            if (responsePost == null){
                System.err.println(String.format("Debug: Sending this POST request '%s' is null", sosURL));
                return false;
            }
            System.out.println("Debug: Ended step 1");
            List<Cookie> lstPOSTCookies = h.getCookies(responsePost);

            // 3. passing POST's cookies to browser's cookies
            if (responsePost.getStatusLine().getStatusCode() != 200) {
                throw new Exception(String.format("Exception: Can't get SLID and u cookies when using '%s' and '%s'", userName, password));
            }

            for (Cookie c : lstPOSTCookies) {
                DriverManager.getDriver().addCookie(c);
            }
            System.out.println(String.format("Info: Completing logging in username '%s'", userName));
            return DriverManager.getDriver().getToAvoidTimeOut(loginURL);

        } catch (TimeoutException ex) {
            System.err.println("Exception: TimeoutException occurs at loginPS3838IgnoreCaptcha");
        } catch (RuntimeException ex) {
            System.err.println("Exception: Exception occurs at loginPS3838IgnoreCaptcha");
        }  catch (Exception ex) {
            System.err.println(String.format("Error: Exception occurs by '%s'", ex.getMessage()));
//            DriverManager.quitAll();
            System.err.println("Error: Exception occurs at Login page bypass captcha by " + ex.getMessage());
        }
        return false;
    }

    public static String updateSecurityCodeInAgent(String url, String headers) {
        String jSessionID = "";
        String allCookies = "";
        Helper1 Helper1 = new Helper1();
        boolean isSecure = url.contains("https://");
        try {
            CookieStore cookieStore = Helper1.getBrowserCookies(DriverManager.getDriver().getCookies(), isSecure);

            for (Cookie cookie : DriverManager.getDriver().getCookies()) {
                if (!allCookies.isEmpty()) {
                    allCookies += ";";
                }
                allCookies += (cookie.getName() + "=" + cookie.getValue());
            }

            // sending updateSecurityOption
            String jsn = String.format("{\"date\":%s}", DateUtils.getMilliSeconds());
            HttpResponse httpResponse = Helper1.sendPostRequest(url, headers, jsn, cookieStore, "application/json");

            List<Cookie> lstPOSTCookies = Helper1.getCookies(httpResponse);
            for (Cookie c : lstPOSTCookies) {
                if (c.getName().equalsIgnoreCase("jsessionid")) {
                    jSessionID = c.getValue();
                    System.err.println("JSESSIONID=" + jSessionID);
                }
            }

            if (!jSessionID.isEmpty()) {
                allCookies += ";JSESSIONID=" + jSessionID;
            }
            return allCookies;
        } catch (IOException ex) {
            System.out.println("ERROR: IOException at updateSecurityCodeInAgent by causing " + ex.getMessage());
            return "";
        }

    }

    //TODO: update
    public static void validateCodeOnAgent(String sosValidationURL, String securityCode) {
        try {
            // 1. getting web-driver cookie
            Set<Cookie> browserCookies = DriverManager.getDriver().getCookies();
            Helper1 h = new Helper1();
            // 2. transferring browser's cookies to session
            CookieStore cookieStore = h.getBrowserCookies(browserCookies);

            //3 decrypt security code
            String securityDecrypt = StringUtils.decrypt(securityCode);

            // 4. bypassing security code
            String apiValidateGet = String.format("%s/?securityCode=%s", sosValidationURL, securityDecrypt);
            HttpResponse responseValidationPost = h.sendGetRequest(apiValidateGet, null, cookieStore);
            if (responseValidationPost == null){
                System.err.println(String.format("Exception: Sending this POST request '%s' is null", sosValidationURL));
                return;
            }

            List<Cookie> lstPOSTValidationCookies = h.getCookies(responseValidationPost);
            for (Cookie c : lstPOSTValidationCookies) // after sending Validation POST, we got SLID, u
            {
                DriverManager.getDriver().addCookie(c);
            }
        } catch (IOException ex) {
            System.err.println("Error: IOException occurs validateCodeOnAgent by causing " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpResponse sendPostRequest(String url, String contentType, CookieStore cookies, String accept) {
        try {
            HttpClient client = createClient(cookies);//HttpClientBuilder.create().setDefaultCookieStore(cookies).build();
            HttpPost post = new HttpPost(url);
            // add header
            if (contentType != null) {
                post.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            if(accept !=null) {
               post.setHeader(HttpHeaders.ACCEPT, accept);
            }
            // send post request
            HttpResponse response = client.execute(post);
System.out.println("reponse" +response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200){
                System.err.println("Response Code : " + response.getStatusLine().getStatusCode());
                System.err.println("ERROR LOG : " + response.getStatusLine().getReasonPhrase());
                return null;
            }

            return response;
        } catch (UnsupportedEncodingException ex) {
            System.err.println(String.format("Error: UnsupportedEncodingException at sendPostRequest at request '%s' because exception message '%s'", url, ex.getMessage()) );
            return null;
        } catch (ClientProtocolException ex) {
            System.err.println(String.format("Error: ClientProtocolException at sendPostRequest at request '%s' because exception message '%s'", url, ex.getMessage()) );
            return null;
        } catch (IOException ex){
            System.err.println(String.format("Error: IOException at sendPostRequest at request '%s' because error '%s'", url, ex.getMessage()) );
            return null;
        } catch (TimeoutException ex){
            System.err.println(String.format("Error: TimeoutException at sendPostRequest at request '%s' because error '%s'", url, ex.getMessage()) );
            return null;
        }
    }

    /**
     * Using this method to get a new JSESSIONID from PS3838-Agent server to bypass security code.
     * On UI, we usually force to input security code but with using API we have to get this JSESSIONID and then add this JSESSIONID into cookie
     * @param url
     * @param contentType header content type
     * @param json
     * @param cookies that is string will be set into header
     * @param accepted set ACCEPT header
     * @return
     * @throws IOException
     */
    public HttpResponse sendPostRequestWithCookies(String url, String contentType, String json, String cookies, String accepted) throws IOException {
        try{
            HttpClient client = createClient();//HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);

            if (json != null) {
                StringEntity stringEntity = new StringEntity(json);
                post.setEntity(stringEntity);
            }
            // add header
            post.setHeader("Cookie", cookies);
            if (accepted !=null) {
                post.setHeader(HttpHeaders.ACCEPT, accepted);
            }
            if (contentType != null) {
                post.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            // send post request
            return client.execute(post);
        } catch (Exception ex) {
            System.err.println(String.format("ERROR: Exception occurs at sendPostRequestWithCookies page bypass '%s' security code by '%s'", url, ex.getMessage()));
            return null;
        }
    }
    public HttpResponse sendPostRequestWithCookies(String url, String contentType, String json, String cookies) throws IOException {
       return sendPostRequestWithCookies(url,contentType,json,cookies,null);
    }
    /**
     * Using this method to get a new JSESSIONID from Agent server to bypass security code.
     * On UI, we usually force to input security code but with using API we have to get this JSESSIONID and then add this JSESSIONID into cookie
     * @param url
     * @param contentType header content type
     * @param cookies that is string will be set into header
     * @return
     * @throws IOException
     */
    public HttpResponse sendGETRequestWithCookies(String url, String contentType, String cookies) throws IOException {
      return sendGETRequestWithCookies(url,contentType,cookies,null);
    }
    public HttpResponse sendGETRequestWithCookies(String url, String contentType, String cookies,String accept) throws IOException {
        try{
            HttpClient client =createClient();//HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);

            // add header
            get.setHeader("Cookie", cookies);
            if (contentType != null) {
                get.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            if(accept != null)
                get.setHeader(HttpHeaders.ACCEPT,accept);
            // send post request
            return client.execute(get);
        } catch (Exception ex) {
            System.err.println(String.format("ERROR: Exception occurs at sendGETRequestWithCookies page bypass '%s' security code by '%s'", url, ex.getMessage()));
            return null;
        }
    }

    private CloseableHttpClient createClient() {
        return createClient(null);
    }
    private CloseableHttpClient createClient(CookieStore cookieStore) {
        try {
            SSLConnectionSocketFactory sslFactory = createTrustAllFactory();
            HttpClientBuilder builder = HttpClientBuilder.create();
            if (cookieStore != null){
                builder.setDefaultCookieStore(cookieStore);
            }
            CloseableHttpClient client = builder
                    .setSSLSocketFactory(sslFactory)
                    .setConnectionManager(new PoolingHttpClientConnectionManager(createTrustAllFactoryRegistry(sslFactory))).build();
            HttpComponentsClientHttpRequestFactory clientFactory = new HttpComponentsClientHttpRequestFactory(client);
            clientFactory.setConnectTimeout(15_000);
            clientFactory.setReadTimeout(15_000);
            clientFactory.setConnectionRequestTimeout(15_000);
            return client;
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {

            throw new RuntimeException(ex);
        }
    }
    private Registry<ConnectionSocketFactory> createTrustAllFactoryRegistry(SSLConnectionSocketFactory sslFactory) {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslFactory).build();
    }
    private SSLConnectionSocketFactory createTrustAllFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return new SSLConnectionSocketFactory(SSLContextBuilder.create().loadTrustMaterial((chain, authType) -> true).build(), (host, ssl) -> true);
    }
    public HttpResponse sendPostRequest(String url, String contentType, String json, CookieStore cookieStore, String accepted) throws IOException {
        try{

            HttpClient client =  createClient(cookieStore);//HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost(url);

            if (json != null) {
                StringEntity stringEntity = new StringEntity(json);
                post.setEntity(stringEntity);
            }
            // add header
            if (accepted !=null) {
                post.setHeader(HttpHeaders.ACCEPT, accepted);
            }
            if (contentType != null) {
                post.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            // send post request
            return client.execute(post);
        } catch (Exception ex) {
            System.out.println(String.format("Error: Exception occurs at sendPostRequest page bypass URL '%s' captcha by '%s'", url, ex.getMessage()));
            return null;
        }
    }

    public HttpResponse sendPostRequest(String url, List<NameValuePair> params, CookieStore cookieStore) throws IOException {
        HttpClient client = createClient(cookieStore);//HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        HttpPost post = new HttpPost(url);

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        // send post request
        return client.execute(post);
    }

    public HttpResponse sendGetRequest(String url, String contentType, CookieStore cookies) throws IOException {
        return sendGetRequest( url,  contentType,  cookies,  null);
    }
    public HttpResponse sendGetRequest(String url, String contentType, CookieStore cookies, String accept) throws IOException {
        HttpClient client =createClient(cookies);//HttpClientBuilder.create().setDefaultCookieStore(cookies).build();
        HttpGet get = new HttpGet(url);

        // add request header
        get.addHeader("User-Agent", USER_AGENT);
        if (contentType != null) {
            get.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        }
        if(accept != null){
            get.setHeader(HttpHeaders.ACCEPT, accept);
        }
        return client.execute(get);
    }

    public HttpResponse sendDeleteRequest(String url, String contentType, CookieStore cookies) throws IOException {
        HttpClient client = createClient(cookies);//HttpClientBuilder.create().setDefaultCookieStore(cookies).build();
        HttpDelete get = new HttpDelete(url);

        // add request header
        get.addHeader("User-Agent", USER_AGENT);
        if (contentType != null) {
            get.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        }
        return client.execute(get);
    }

    public CookieStore getBrowserCookies(Set<Cookie> browserCookies) {
        CookieStore cookieStore = new BasicCookieStore();
        if (browserCookies == null) {
            System.out.print("browserCookies is null");
            return null;
        } else {
            Iterator iterator = browserCookies.iterator();
            while (iterator.hasNext()) {
                Cookie cookie = (Cookie) iterator.next();
                BasicClientCookie basicCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                basicCookie.setDomain(cookie.getDomain());
                if (cookie.getName().contains("JSESSIONID")) {
                    basicCookie.setPath("/pp-ui");
                } else {
                    basicCookie.setPath(cookie.getPath());
                }
                if (cookie.getName().contains("pu") || cookie.getName().contains("JSESSIONID") || cookie.getName().contains("LS") || cookie.getName().contains("pSLID")) {
                    basicCookie.setSecure(true);
                } else {
                    basicCookie.setSecure(false);
                }
                cookieStore.addCookie(basicCookie);
            }
        }
        return cookieStore;
    }

    public CookieStore getBrowserCookies(Set<Cookie> browserCookies, boolean isSecure) {
        CookieStore cookieStore = new BasicCookieStore();
        if (browserCookies == null) {
            System.out.print("browserCookies is null");
            return null;
        } else {
            Iterator iterator = browserCookies.iterator();
            while (iterator.hasNext()) {
                Cookie cookie = (Cookie) iterator.next();
                BasicClientCookie basicCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                basicCookie.setDomain(cookie.getDomain());
                if (cookie.getName().contains("JSESSIONID")) {
                    basicCookie.setPath("/pp-ui");
                } else {
                    basicCookie.setPath(cookie.getPath());
                }
                if (isSecure){
                    if (cookie.getName().contains("pu") || cookie.getName().contains("JSESSIONID") || cookie.getName().contains("LS") || cookie.getName().contains("pSLID")) {
                        basicCookie.setSecure(true);
                    } else {
                        basicCookie.setSecure(false);
                    }
                }
                cookieStore.addCookie(basicCookie);
            }
        }
        return cookieStore;
    }

    public static void main(String[] args) throws Exception {
//        DriverProperties driverProperties = new DriverProperties();
//        driverProperties.setBrowserVersion("CHROME");
//        driverProperties.setBrowserName(Constants.Browser.CHROME);
//        driverProperties.setRemoteURL("");
//        driverProperties.setPlatform(Constants.Platform.WINDOWS);
//        driverProperties.setPlatformVersion("10");
//        driverProperties.setExecutablePath("\\documents\\chromedriver.exe");
//        DriverManager.createWebDriver(driverProperties);


//        System.out.println(response.getStatusLine().getStatusCode());
//        DriverManager.quitAll();
    }


    /**************
     * PRIVATE METHODS
     *************/
    private Date getExpire() {
        Date expDate = new Date();
        expDate.setTime(expDate.getTime() + (3600 * 1000) + 3000000);
        DateFormat df = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zzz");
        df.setTimeZone(TimeZone.getTimeZone("GMT-04:00"));
        df.format(expDate);
        return expDate;
    }

    /**
     * getting selenium cookies
     *
     * @param response List<Cookie>
     * @return List<Cookie>
     */
    private List<Cookie> getCookies(HttpResponse response) {
        List<Cookie> lstCookies = new ArrayList<>();
        Header[] headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        for (Header header : headers) {
            List<HttpCookie> cookies = HttpCookie.parse(header.getValue());
            for (HttpCookie cookie : cookies) {
                lstCookies.add(new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), null));
            }
        }
        return lstCookies;
    }

    /**
     * getting CookieStore
     *
     * @param response CookieStore
     * @return CookieStore
     */
    private CookieStore getCookStore(HttpResponse response) {
        CookieStore cookieStore = new BasicCookieStore();
        Header[] headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        for (Header header : headers) {
            List<HttpCookie> cookies = HttpCookie.parse(header.getValue());
            for (HttpCookie cookie : cookies) {
                BasicClientCookie basicCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                basicCookie.setPath(cookie.getPath());
                basicCookie.setDomain(cookie.getDomain());
                cookieStore.addCookie(basicCookie);
            }
        }
        return cookieStore;
    }
}
