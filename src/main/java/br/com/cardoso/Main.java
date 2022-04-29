package br.com.cardoso;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.Map;

public class Main {

    private static final Logger LOGGER_MAIN = LogManager.getLogger(Main.class);
    private static final Logger LOGGER_TEST = LogManager.getLogger("Logger_Test");

    private static final Logger LOGGER_TEST_2 = LogManager.getLogger("Logger_Test");
    public static void main(String[] args) {
        String loggerName = "Logger_Test";

        addLogger(loggerName);
        addLogger(loggerName);

        LOGGER_MAIN.info("Hello World Main!");
        LOGGER_TEST.info("Hello World Logger_Test!");
        LOGGER_TEST_2.info("Hello World Logger_Test 2!");
    }

    private static void addLogger(String loggerName) {
        String consoleAppenderName = "ConsoleAppenderAnotherPattern";

        Level levelLog = Level.valueOf(System.getProperty("LOG_LEVEL", "info"));

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        Map<String, LoggerConfig> loggers = config.getLoggers();
        if(!loggers.containsKey(loggerName)) {
            System.out.println("Logger " + loggerName + " adicionado.");
            Appender appender = config.getAppender(consoleAppenderName);
            AppenderRef ref = AppenderRef.createAppenderRef(consoleAppenderName, levelLog, null);
            AppenderRef[] refs = new AppenderRef[]{ref};
            LoggerConfig loggerConfig = LoggerConfig.newBuilder().withAdditivity(false).withLevel(levelLog).withLoggerName(loggerName).withRefs(refs).withConfig(config).build();
            loggerConfig.addAppender(appender, levelLog, null);
            config.addLogger(loggerName, loggerConfig);
            ctx.updateLoggers();
        } else {
            System.out.println("Logger já existente, não será adicionado novamente: " + loggerName);
        }
    }
}