package com.github.thedrakonir.drakotil.logging;

public interface Logger {
    
    public void log(Object... obj);

    public void info(Object... obj);

    public void debug(Object... obj);

    public void warn(Object... obj);

    public void error(Object... obj);

}