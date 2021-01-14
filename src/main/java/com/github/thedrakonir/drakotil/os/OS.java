package com.github.thedrakonir.drakotil.os;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OS {

    public enum OS_Type {
        WINDOWS,
        MAC_OS,
        LINUX,
        UNKNOWN;
    }

    private static final OS_Type TYPE;
    private static final String VERSION;

    private static final String JAVA_CLASS_VERSION;

    private static final String USERNAME;
    private static final Path USER_HOME_PATH;
    private static final String USER_LANGUAGE;
    
    static {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            TYPE = OS_Type.WINDOWS;
        } else if (osName.toLowerCase().contains("mac")) {
            TYPE = OS_Type.MAC_OS;
        } else if (osName.toLowerCase().contains("linux")) {
            TYPE = OS_Type.LINUX;
        } else {
            TYPE = OS_Type.UNKNOWN;
        }

        VERSION = System.getProperty("os.version");

        JAVA_CLASS_VERSION = System.getProperty("java.class.version");

        USERNAME = System.getProperty("user.name");
        USER_HOME_PATH = Paths.get(System.getProperty("user.home"));
        USER_LANGUAGE = System.getProperty("user.language");
    }

    public static OS_Type getOS() {
        return TYPE;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getJavaClassVersion() {
        return JAVA_CLASS_VERSION;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static Path getUserHome() {
        return USER_HOME_PATH;
    }

    public static String getUserLanguage() {
        return USER_LANGUAGE;
    }
}