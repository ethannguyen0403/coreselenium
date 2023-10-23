package com.paltech.utils.slack;

import org.openqa.selenium.chrome.ChromeDriver;

public class SlackIntegrationTest {
   public static void main(String[] args){
       Eyes eyes = new Eyes();
       eyes.setApiKey(System.getenv("API_KEY"));
       ChromeDriver driver = new ChromeDriver();
       TestResults res = eyes.close(false);
       EyesSlack.post(res, System.getenv("SLACK_WEBHOOK_URL"));
   }
}

