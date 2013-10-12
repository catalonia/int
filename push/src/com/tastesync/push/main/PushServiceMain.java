package com.tastesync.push.main;

import java.io.IOException;

import com.tastesync.push.services.PushService;

public class PushServiceMain {
    public static void main(String[] args) {
    	PushService pushService = new PushService();
    	String msg = "test msg";
    	String deviceToken = "devicetoken";
    	try {
			pushService.testPushMsg(msg, deviceToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
