package com.github.thedrakonir.drakotil.logging;

import java.io.PrintStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.thedrakonir.drakotil.serialization.Stringifier;

public class ConsoleLogger implements Logger {

    private final static String LOG_FORMAT = "[%1$tF %1$tT][%2$s]: %3$s";

    private final PrintStream outPrintStream;

    protected ConsoleLogger(PrintStream outPrintStream) {
        this.outPrintStream = outPrintStream;
    }

    protected ConsoleLogger() {
        this(System.out);
    }

    private void printLog(String logLevel, Object... obj) {
        String objString = Stream.of(obj).map(Stringifier::stringify).collect(Collectors.joining(", "));
        ZonedDateTime time = Instant.now().atZone(ZoneId.systemDefault());
        outPrintStream.println(String.format(LOG_FORMAT, time, logLevel, objString));
    }

    @Override
    public void log(Object... obj) {
        printLog("LOG", obj);
    }

    @Override
    public void info(Object... obj) {
        printLog("INFO", obj);
    }

    @Override
    public void debug(Object... obj) {
        printLog("DEBUG", obj);
    }

    @Override
    public void warn(Object... obj) {
        printLog("WARN", obj);
    }

    @Override
    public void error(Object... obj) {
        printLog("ERROR", obj);
    }

}