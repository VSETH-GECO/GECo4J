package ch.ethz.geco.g4j.util;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * The SLF4J {@link Marker log markers} used by GECo4J.
 */
public class LogMarkers {
    /**
     * The "parent" marker to all others.
     */
    public static final Marker MAIN = MarkerFactory.getMarker("MAIN");

    /**
     * The marker for all classes in the {@link ch.ethz.geco.g4j.util} package.
     * It is a child of {@link #MAIN}.
     */
    public static final Marker UTIL = MarkerFactory.getMarker("UTIL");

    /**
     * The marker for all API related classes.
     * It is a child of {@link #MAIN}.
     */
    public static final Marker API = MarkerFactory.getMarker("API");

    static {
        MAIN.add(UTIL);
        MAIN.add(API);
    }
}