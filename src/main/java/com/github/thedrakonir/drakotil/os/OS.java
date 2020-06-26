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

    private static OS_Type type;
    private static String version;

    private static String javaClassVersion;

    private static String username;
    private static Path userHome;
    private static String userLanguage;
    
    static {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            type = OS_Type.WINDOWS;
        } else if (osName.toLowerCase().contains("mac")) {
            type = OS_Type.MAC_OS;
        } else if (osName.toLowerCase().contains("linux")) {
            type = OS_Type.LINUX;
        } else {
            type = OS_Type.UNKNOWN;
        }

        version = System.getProperty("os.version");

        javaClassVersion = System.getProperty("java.class.version");

        username = System.getProperty("user.name");
        userHome = Paths.get(System.getProperty("user.home"));
        userLanguage = System.getProperty("user.language");
    }

    public static OS_Type getOS() {
        return type;
    }

    public static String getVersion() {
        return version;
    }

    public static String getJavaClassVersion() {
        return javaClassVersion;
    }

    public static String getUsername() {
        return username;
    }

    public static Path getUserHome() {
        return userHome;
    }

    public static String getUserLanguage() {
        return userLanguage;
    }
}