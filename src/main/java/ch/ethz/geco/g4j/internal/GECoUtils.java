package ch.ethz.geco.g4j.internal;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.impl.*;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.*;
import ch.ethz.geco.g4j.util.LogMarkers;
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

    /**
     * Converts a seat JSON object to a seat object.
     *
     * @param seatObject The seat object to convert.
     * @return The converted seat object.
     */
    public static ISeat getSeatFromJSON(@NotNull SeatObject seatObject) {
        ISeat.Status status;
        switch (seatObject.status) {
            case 0:
                status = ISeat.Status.FREE;
                break;
            case 1:
                status = ISeat.Status.RESERVED;
                break;
            case 2:
                status = ISeat.Status.OCCUPIED;
                break;
            default:
                GECo4J.LOGGER.error(LogMarkers.API, "Unknown seat status: {}", seatObject.status);
                status = null;
        }

        return new Seat(seatObject.id, seatObject.lan_user_id, seatObject.web_user_id, status, seatObject.username, seatObject.seat_number);
    }

    /**
     * Converts a lan user JSON object to a lan user object.
     *
     * @param client        The client associated with the lan user.
     * @param lanUserObject The lan user object to convert.
     * @return The converted lan user object.
     */
    public static ILanUser getLanUserFromJSON(@NotNull IGECoClient client, @NotNull LanUserObject lanUserObject) {
        ILanUser.Status status;
        switch (lanUserObject.status_code) {
            case 0:
                status = ILanUser.Status.AWAITING_PAYMENT;
                break;
            case 1:
                status = ILanUser.Status.PAID_NO_SEAT;
                break;
            case 2:
                status = ILanUser.Status.PAID_WITH_SEAT;
                break;
            case 3:
                status = ILanUser.Status.CHECKED_IN;
            default:
                GECo4J.LOGGER.error(LogMarkers.API, "Unknown seat status: {}", lanUserObject.status);
                status = null;
        }

        return new LanUser(client, lanUserObject.id, lanUserObject.status, status, lanUserObject.username, lanUserObject.first_name, lanUserObject.last_name, lanUserObject.seat, lanUserObject.birthday, lanUserObject.sa_verified, lanUserObject.legi_number, lanUserObject.package_name, lanUserObject.student_association);
    }

    /**
     * Converts a borrowed item JSON object to a borrowed item object.
     *
     * @param client             The client associated with the borrowed item.
     * @param borrowedItemObject The borrowed item object to convert.
     * @param userID             The ID of the user borrowing the item.
     * @return The converted borrowed item object.
     */
    public static IBorrowedItem getBorrowedItemFromJSON(@NotNull IGECoClient client, @NotNull BorrowedItemObject borrowedItemObject, @NotNull Long userID) {
        return new BorrowedItem(client, borrowedItemObject.id, userID, borrowedItemObject.name);
    }

    /**
     * Converts a news JSON object to a news object.
     *
     * @param newsObject The news object to convert.
     * @return The converted news object.
     */
    public static INews getNewsFromJSON(@NotNull NewsObject newsObject) {
        return new News(newsObject.title,newsObject.description,newsObject.url,newsObject.is_draft,newsObject.author.name, newsObject.author.url, newsObject.author.icon_url, newsObject.footer.text);
    }

    /**
     * Converts an event JSON object to a event object.
     *
     * @param eventObject The event object to convert.
     * @return The converted event object.
     */
    public static IEvent getEventFromJSON(@NotNull EventObject eventObject) {
        return new Event(eventObject.title, eventObject.description, eventObject.url);
    }
}
