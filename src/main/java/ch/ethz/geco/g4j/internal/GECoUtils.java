package ch.ethz.geco.g4j.internal;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.impl.*;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.*;
import ch.ethz.geco.g4j.util.LogMarkers;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GECoUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Converts a user JSON object to a user object.
     *
     * @param userObject The user JSON object to convert.
     * @return The converted user object.
     */
    public static User getUserFromJSON(UserObject userObject) {
        return new WebUser(userObject.id, userObject.name, userObject.usergroup, userObject.lol, userObject.steam, userObject.bnet, userObject.discord);
    }

    /**
     * Converts a seat JSON object to a seat object.
     *
     * @param seatObject The seat object to convert.
     * @return The converted seat object.
     */
    public static Seat getSeatFromJSON(GECoClient client, SeatObject seatObject) {
        Seat.Status status;
        switch (seatObject.status) {
            case 0:
                status = Seat.Status.FREE;
                break;
            case 1:
                status = Seat.Status.RESERVED;
                break;
            case 2:
                status = Seat.Status.OCCUPIED;
                break;
            default:
                GECo4J.LOGGER.error(LogMarkers.API, "Unknown seat status: {}", seatObject.status);
                status = null;
        }

        return new WebSeat(client, seatObject.id, seatObject.lan_user_id, seatObject.web_user_id, status, seatObject.username, seatObject.seat_number);
    }

    /**
     * Converts a lan user JSON object to a lan user object.
     *
     * @param client        The client associated with the lan user.
     * @param lanUserObject The lan user object to convert.
     * @return The converted lan user object.
     */
    public static LanUser getLanUserFromJSON(GECoClient client, LanUserObject lanUserObject) {
        LanUser.Status status;

        if (lanUserObject.status_code == null) {
            status = LanUser.Status.AWAITING_REGISTRATION;
        } else {
            switch (lanUserObject.status_code) {
                case 0:
                    status = LanUser.Status.AWAITING_PAYMENT;
                    break;
                case 1:
                    status = LanUser.Status.PAID_NO_SEAT;
                    break;
                case 2:
                    status = LanUser.Status.PAID_WITH_SEAT;
                    break;
                case 3:
                    status = LanUser.Status.CHECKED_IN;
                    break;
                default:
                    GECo4J.LOGGER.error(LogMarkers.API, "Unknown seat status: {}", lanUserObject.status);
                    status = null;
            }
        }

        return new WebLanUser(client, lanUserObject.id, lanUserObject.status, status, lanUserObject.username, lanUserObject.first_name, lanUserObject.last_name, lanUserObject.seat, lanUserObject.birthday, lanUserObject.sa_verified, lanUserObject.legi_number, lanUserObject.package_name, lanUserObject.student_association);
    }

    /**
     * Converts a borrowed item JSON object to a borrowed item object.
     *
     * @param client             The client associated with the borrowed item.
     * @param borrowedItemObject The borrowed item object to convert.
     * @param userID             The ID of the user borrowing the item.
     * @return The converted borrowed item object.
     */
    public static BorrowedItem getBorrowedItemFromJSON(GECoClient client, BorrowedItemObject borrowedItemObject, Long userID) {
        return new WebBorrowedItem(client, borrowedItemObject.id, userID, borrowedItemObject.name);
    }

    /**
     * Converts a news JSON object to a news object.
     *
     * @param newsObject The news object to convert.
     * @return The converted news object.
     */
    public static News getNewsFromJSON(NewsObject newsObject) {
        return new WebNews(newsObject.title, newsObject.description, newsObject.url, newsObject.is_draft, newsObject.published_at, newsObject.author.name, newsObject.author.url, newsObject.author.icon_url, newsObject.footer.text);
    }

    /**
     * Converts an event JSON object to a event object.
     *
     * @param eventObject The event object to convert.
     * @return The converted event object.
     */
    public static Event getEventFromJSON(EventObject eventObject) {
        return new WebEvent(eventObject.title, eventObject.description, eventObject.url, eventObject.is_draft);
    }
}
