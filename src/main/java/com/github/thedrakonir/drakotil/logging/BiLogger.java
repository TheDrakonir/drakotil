package com.github.thedrakonir.drakotil.logging;


public class BiLogger implements Logger {
    
    private Logger consoleLogger;
    private Logger fileLogger;

    protected BiLogger() {
        this.consoleLogger = LoggerFactory.getConsoleLogger();
        this.fileLogger = LoggerFactory.getFileLogger();
    }

    @Override
    public void log(Object... obj) {
        consoleLogger.log(obj);
        fileLogger.log(obj);
    }

    @Override
    public void info(Object... obj) {
        consoleLogger.info(obj);
        fileLogger.info(obj);
    }

    @Override
    public void debug(Object... obj) {
        consoleLogger.debug(obj);
        fileLogger.debug(obj);
    }

    @Override
    public void warn(Object... obj) {
        consoleLogger.warn(obj);
        fileLogger.warn(obj);
    }

    @Override
    public void error(Object... obj) {
        consoleLogger.error(obj);
        fileLogger.error(obj);
    }
}