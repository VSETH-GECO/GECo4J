package ch.ethz.geco.g4j.internal;

import ch.ethz.geco.g4j.impl.User;
import ch.ethz.geco.g4j.internal.json.UserObject;
import ch.ethz.geco.g4j.obj.IUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.jetbrains.annotations.NotNull;

public class GECoUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    // Use jackson afterburner
    static {
        MAPPER.registerModule(new AfterburnerModule());
    }

    /**
     * Converts a user JSON object to a user object.
     *
     * @param userObject The user JSON object to convert.
     * @return The converted user object.
     */
    public static IUser getUserFromJSON(@NotNull UserObject userObject) {
        return new User(userObject.id, userObject.name, userObject.usergroup, userObject.lol, userObject.steam, userObject.bnet, userObject.discord);
    }


}
