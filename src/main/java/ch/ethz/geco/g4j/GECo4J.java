package ch.ethz.geco.g4j;

import ch.ethz.geco.g4j.util.LogMarkers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NOPLoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Properties;

public class GECo4J {
    /**
     * The name of the project.
     */
    private static final String NAME;

    /**
     * The version of the project.
     */
    private static final String VERSION;

    /**
     * The commit hash of the version of the project.
     */
    private static final String COMMIT;

    /**
     * The git branch of the project.
     */
    private static final String BRANCH;

    /**
     * The description of the project.
     */
    private static final String DESCRIPTION;

    /**
     * The github repo of the project.
     */
    private static final String URL;

    /**
     * SLF4J Instance.
     */
    public static final Logger LOGGER = initLogger();

    /**
     * When this class was loaded.
     */
    protected static final Instant launchTime = Instant.now();

    // Dynamically getting various information from maven
    static {
        InputStream stream = GECo4J.class.getClassLoader().getResourceAsStream("lib.properties");

        if (stream == null) {
            System.err.println("Could not load lib.properties.");
            System.exit(1);
        }

        Properties properties = new Properties();
        try {
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            GECo4J.LOGGER.error(LogMarkers.MAIN, "GECo4J Internal Exception", e);
        }

        NAME = properties.getProperty("library.name");
        VERSION = properties.getProperty("library.version");
        COMMIT = properties.getProperty("library.git.commit");
        BRANCH = properties.getProperty("library.git.branch");
        DESCRIPTION = properties.getProperty("library.description");
        URL = properties.getProperty("library.url");

        LOGGER.info(LogMarkers.MAIN, "{} v{} {} ({})", NAME, VERSION, COMMIT, URL);
        LOGGER.info(LogMarkers.MAIN, "{}", DESCRIPTION);
    }

    /**
     * Initializes the logger as either the {@link GECo4JLogger default implementation} or a found SLF4J implementation.
     *
     * @return The initialized logger.
     */
    private static Logger initLogger() {
        if (!isSLF4JImplementationPresent()) {
            System.err.println("GECo4J: ERROR INITIALIZING LOGGER!");
            System.err.println("GECo4J: No SLF4J implementation found, reverting to the internal implementation ("+GECo4JLogger.class.getName()+")");
            System.err.println("GECo4J: It is *highly* recommended to use a fully featured implementation like logback!");
            return new GECo4JLogger(GECo4J.class.getName());
        } else {
            return LoggerFactory.getLogger(GECo4J.class);
        }
    }

    /**
     * Gets whether an SLF4J implementation is present on the classpath.
     *
     * @return Whether an SLF4J implementation is present on the classpath.
     */
    private static boolean isSLF4JImplementationPresent() {
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder"); //First try to find the implementation
            return !(LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory); //Implementation found! Double check the logger factory
        } catch (ClassNotFoundException e) {
            return false; //No implementation found
        }
    }

    /**
     * A logger implementation used by GECo4J if no valid SLF4J implementation is found.
     */
    public static class GECo4JLogger extends MarkerIgnoringBase {

        private final String name;
        private volatile int level = Level.INFO.ordinal();
        private volatile PrintStream standard, error;

        GECo4JLogger(String name) {
            this.name = name;
            standard = System.out;
            error = System.err;
        }

        /**
         * Sets the level for the logger.
         *
         * @param level The level for the logger.
         */
        public void setLevel(Level level) {
            this.level = level.ordinal();
        }

        /**
         * Sets the stream for "standard" (any level below {@link Level#WARN}) messages to be printed to.
         *
         * @param stream The stream to use.
         */
        public void setStandardStream(PrintStream stream) {
            this.standard = stream;
        }

        /**
         * Sets the stream for "error" (any level above {@link Level#INFO}) messages to be printed to.
         *
         * @param stream The stream to use.
         */
        public void setErrorStream(PrintStream stream) {
            this.error = stream;
        }

        /**
         * Logs a message on the given level.
         *
         * @param level The level to log the message on.
         * @param message The message to log.
         * @param error The error to log.
         */
        private void log(Level level, String message, Throwable error) {
            if (level.ordinal() >= this.level) {
                PrintStream stream = level.ordinal() >= Level.WARN.ordinal() ? this.error : standard;

                stream.format("%s: [%s][%s][%s] - %s\n", LocalTime.now(), level, Thread.currentThread().getName(), name, message);

                if (error != null)
                    error.printStackTrace(stream);
            }
        }

        @Override
        public boolean isTraceEnabled() {
            return level == Level.TRACE.ordinal();
        }

        @Override
        public void trace(String msg) {
            log(Level.TRACE, msg, null);
        }

        @Override
        public void trace(String format, Object arg) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.TRACE, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void trace(String format, Object arg1, Object arg2) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.TRACE, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void trace(String format, Object... arguments) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, arguments);
            log(Level.TRACE, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void trace(String msg, Throwable t) {
            log(Level.TRACE, msg, t);
        }

        @Override
        public boolean isDebugEnabled() {
            return level <= Level.DEBUG.ordinal();
        }

        @Override
        public void debug(String msg) {
            log(Level.DEBUG, msg, null);
        }

        @Override
        public void debug(String format, Object arg) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void debug(String format, Object arg1, Object arg2) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void debug(String format, Object... arguments) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, arguments);
            log(Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void debug(String msg, Throwable t) {
            log(Level.DEBUG, msg, t);
        }

        @Override
        public boolean isInfoEnabled() {
            return level <= Level.INFO.ordinal();
        }

        @Override
        public void info(String msg) {
            log(Level.INFO, msg, null);
        }

        @Override
        public void info(String format, Object arg) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void info(String format, Object arg1, Object arg2) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void info(String format, Object... arguments) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, arguments);
            log(Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void info(String msg, Throwable t) {
            log(Level.INFO, msg, t);
        }

        @Override
        public boolean isWarnEnabled() {
            return level <= Level.WARN.ordinal();
        }

        @Override
        public void warn(String msg) {
            log(Level.WARN, msg, null);
        }

        @Override
        public void warn(String format, Object arg) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.WARN, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void warn(String format, Object arg1, Object arg2) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.WARN, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void warn(String format, Object... arguments) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, arguments);
            log(Level.WARN, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void warn(String msg, Throwable t) {
            log(Level.WARN, msg, t);
        }

        @Override
        public boolean isErrorEnabled() {
            return level <= Level.ERROR.ordinal();
        }

        @Override
        public void error(String msg) {
            log(Level.ERROR, msg, null);
        }

        @Override
        public void error(String format, Object arg) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.ERROR, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void error(String format, Object arg1, Object arg2) {
            FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.ERROR, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void error(String format, Object... arguments) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(format, arguments);
            log(Level.ERROR, tuple.getMessage(), tuple.getThrowable());
        }

        @Override
        public void error(String msg, Throwable t) {
            log(Level.ERROR, msg, t);
        }

        /**
         * Log levels.
         */
        public enum Level {
            TRACE, DEBUG, INFO, WARN, ERROR, NONE
        }
    }
}
