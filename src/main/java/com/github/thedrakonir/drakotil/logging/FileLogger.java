package com.github.thedrakonir.drakotil.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    private static final String DEFAULT_FILE_NAME_FORMAT = "%tF.log";
    private static final String DEFAULT_LOG_PATH_FOLDER = "logs";
    private static final boolean DEFAULT_CONTINEOUS_LOG_SETTING = false;
    private static final String LOG_FORMAT = "[%1$tF %1$tT][%2$s][%3$s]: %4$s";

    private final Path logPath;
    private final String filenameFormat;
    private final boolean contineous;

    private Optional<File> currentLogfile = Optional.empty();

    protected FileLogger(Path logPath, String filenameFormat, boolean contineous) {
        this.logPath = logPath;
        this.filenameFormat = filenameFormat;
        this.contineous = contineous;
    }

    protected FileLogger(Path logPath, String filenameFormat) {
        this(logPath, filenameFormat, DEFAULT_CONTINEOUS_LOG_SETTING);
    }

    protected FileLogger(Path logPath) {
        this(logPath, DEFAULT_FILE_NAME_FORMAT);
    }

    protected FileLogger(Path logPath, boolean contineous) {
        this(logPath, DEFAULT_FILE_NAME_FORMAT, contineous);
    }

    protected FileLogger(boolean contineous) {
        this(Paths.get("", DEFAULT_LOG_PATH_FOLDER).toAbsolutePath(), contineous);
    }

    protected FileLogger() {
        this(Paths.get("", DEFAULT_LOG_PATH_FOLDER).toAbsolutePath());
    }

    private void writeLog(String logLevel, Object... obj) {
        String objString = Stream.of(obj).map(Stringifier::stringify).collect(Collectors.joining(", "));
        ZonedDateTime time = Instant.now().atZone(ZoneId.systemDefault());
        String threadName = Thread.currentThread().getName().toUpperCase();
        String content = String.format(LOG_FORMAT, time, threadName, logLevel, objString);

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
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
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