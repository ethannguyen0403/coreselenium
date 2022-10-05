package com.paltech.constant;

public class CoreConstants {
	public enum LocatorType { ID, NAME, LINK_TEXT, PARTIAL_LINK_TEXT, TAG_NAME, CLASS_NAME, CSS, XPATH }

	public enum Platform {
		WINDOWS {
			public String toString() {
				return "WINDOWS";
			}
		}, MAC, ANDROID, IOS, LINUX, OSX
	}

	public enum Browser { FIREFOX, INTERNET_EXPLORER, SAFARI, CHROME, BROWSER, EDGE}
}
