package ch.ethz.geco.g4j.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class GECoUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    // Use jackson afterburner
    static {
        MAPPER.registerModule(new AfterburnerModule());
    }


}
