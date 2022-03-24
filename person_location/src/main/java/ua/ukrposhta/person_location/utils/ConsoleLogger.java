package ua.ukrposhta.person_location.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Properties;

public class ConsoleLogger  {

    private static volatile ConsoleLogger instance;
    private Logger logger = Logger.getLogger("consoleLogger");

    private ConsoleLogger() {configure();}

    public static ConsoleLogger getInstance() {
        if (instance == null) {
            synchronized (ConsoleLogger.class) {
                if (instance == null) {
                    instance = new ConsoleLogger();
                }
            }
        }
        return instance;
    }

    private void configure() {
        Properties logProperties = new Properties();
        try {
            logProperties.load(ConsoleLogger.class.getClassLoader().getResourceAsStream("properties/log4j.properties"));
            PropertyConfigurator.configure(logProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Logger getLogger() {
        return logger;
    }

    public void info(Object message) {
        getLogger().info(message);
    }

    public void warn(Object message) {
        getLogger().warn(message);
    }

    public void error(Object message) {
        getLogger().error(message);
    }

}