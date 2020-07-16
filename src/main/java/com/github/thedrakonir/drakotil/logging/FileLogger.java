package com.github.thedrakonir.drakotil.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.thedrakonir.drakotil.serialization.Stringifier;

public class FileLogger implements Logger {

    private final static String LOG_FORMAT = "[%1$tF %1$tT][%2$s]: %3$s";

    private Path logPath;
    private String filenameFormat;
    private boolean contineous;

    private Optional<File> currentLogfile = Optional.empty();

    protected FileLogger(Path logPath, String filenameFormat, boolean contineous) {
        this.logPath = logPath;
        this.filenameFormat = filenameFormat;
    }

    protected FileLogger(Path logPath, String filenameFormat) {
        this(logPath, filenameFormat, false);
    }

    protected FileLogger(Path logPath) {
        this(logPath, "%tF.log");
    }

    protected FileLogger(Path logPath, boolean contineous) {
        this(logPath, "%tF.log", contineous);
    }

    protected FileLogger(boolean contineous) {
        this(Paths.get("", "logs").toAbsolutePath(), contineous);
    }

    protected FileLogger() {
        this(Paths.get("", "logs").toAbsolutePath());
    }

    private void writeLog(String logLevel, Object... obj) {
        String objString = Stream.of(obj).map(Stringifier::stringify).collect(Collectors.joining(", "));
        ZonedDateTime time = Instant.now().atZone(ZoneId.systemDefault());
        String content = String.format(LOG_FORMAT, time, logLevel, objString);

        if (contineous && currentLogfile.isPresent()) {
            logToFile(currentLogfile.get(), content);
            return;
        }

        if (contineous) {
            File targetFile = logPath.resolve(String.format("%s", String.format(filenameFormat, time))).toFile();
            targetFile.getParentFile().mkdirs();
            try {
                targetFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            currentLogfile = Optional.of(targetFile);
            logToFile(targetFile, content);
            return;
        }

        if (currentLogfile.isPresent() && currentLogfile.get().getName().equals(String.format(filenameFormat, time))) {
            logToFile(currentLogfile.get(), content);
            return;
        }

        File targetFile = logPath.resolve(String.format("%s", String.format(filenameFormat, time))).toFile();
        targetFile.getParentFile().mkdirs();
        try {
            targetFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        currentLogfile = Optional.of(targetFile);
        logToFile(targetFile, content);
    }

    private void logToFile(File file, String content) {
        try (FileWriter fileWriter = new FileWriter(file, Charset.forName("UTF-8"), true)) {
            fileWriter.append(content + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void log(Object... obj) {
        writeLog("LOG", obj);
    }

    @Override
    public void info(Object... obj) {
        writeLog("INFO", obj);
    }

    @Override
    public void debug(Object... obj) {
        writeLog("DEBUG", obj);
    }

    @Override
    public void warn(Object... obj) {
        writeLog("WARN", obj);
    }

    @Override
    public void error(Object... obj) {
        writeLog("ERROR", obj);
    }
}