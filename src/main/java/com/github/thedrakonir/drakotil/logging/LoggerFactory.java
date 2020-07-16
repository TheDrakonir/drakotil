package com.github.thedrakonir.drakotil.logging;

import java.io.PrintStream;
import java.nio.file.Path;

public class LoggerFactory {

    private static ConsoleLogger consoleLogger;
    private static FileLogger fileLogger;
    private static BiLogger biLogger;

    public static Logger getLogger() {
        if (biLogger != null) {
            return biLogger;
        }

        if (fileLogger != null) {
            return fileLogger;
        }

        if (consoleLogger != null) {
            return consoleLogger;
        }

        return getConsoleLogger();
    }

    public static void configureConsoleLogger(PrintStream outPrintStream) {
        consoleLogger = new ConsoleLogger(outPrintStream);
    }

    public static Logger getConsoleLogger() {
        if (consoleLogger == null) {
            consoleLogger = new ConsoleLogger();
        }

        return consoleLogger;
    }

    public static void configureFileLogger(Path logPath, String filenameFormat, boolean contineous) {
        fileLogger = new FileLogger(logPath, filenameFormat, contineous);
    }

    public static void configureFileLogger(Path logPath, String filenameFormat) {
        fileLogger = new FileLogger(logPath, filenameFormat);
    }

    public static void configureFileLogger(Path logPath, boolean contineous) {
        fileLogger = new FileLogger(logPath, contineous);
    }

    public static void configureFileLogger(Path logPath) {
        fileLogger = new FileLogger(logPath);
    }

    public static Logger getFileLogger() {
        if (fileLogger == null) {
            fileLogger = new FileLogger();
        }

        return fileLogger;
    }

    public static Logger getBiLogger() {
        if (biLogger == null) {
            biLogger = new BiLogger();
        }

        return biLogger;
    }
}