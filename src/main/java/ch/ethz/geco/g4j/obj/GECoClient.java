package ch.ethz.geco.g4j.obj;

import ch.ethz.geco.g4j.util.APIException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GECoClient {
    /**
     * Gets the API token of the client used for authorized requests.
     *
     * @return The API token.
     */
    String getAPIToken();

    /**
     * Gets a user by it's ID.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the user was not found.
     *
     * @param id The ID of the user to get.
     * @return A Mono which emits the user with the given ID on success.
     */
    Mono<User> getUserByID(Long id);

    /**
     * Gets a user by it's Discord ID.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the user was not found.
     *
     * @param id The Discord ID of the user to get.
     * @return A Mono which emits the user with the given Discord ID on success.
     */
    Mono<User> getUserByDiscordID(Long id);

    /**
     * Gets all seats of the current LAN event.
     *
     * @return A Flux of all seats. Might be empty if there are no seats or an error occurred.
     */
    Flux<Seat> getSeats();

    /**
     * Gets a seat by its ID.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the seat was not found.
     *
     * @param id The ID of the seat to get.
     * @return A Mono which emits the seat with the given ID on success.
     */
    Mono<Seat> getSeatByID(Long id);

    /**
     * Gets a lan user by its ID.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the lan user was not found.
     *
     * @param id The ID of the lan user to get.
     * @return A Mono which emits the lan user with the given ID on success.
     */
    Mono<LanUser> getLanUserByID(Long id);

    /**
     * Gets a lan user by its name.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the lan user was not found.
     *
     * @param name The name of the lan user to get.
     * @return A Mono which emits the lan user with the given name on success.
     */
    Mono<LanUser> getLanUserByName(String name);

    /**
     * Gets a seat by its name. If there are duplicate seat names like with admin seats, this will just return
     * one seat with the given name.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the seat was not found.
     *
     * @param name The name of the seat to get.
     * @return A Mono which emits the seat with the given name on success.
     */
    Mono<Seat> getSeatByName(String name);

    /**
     * Gets a single news post by its ID.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the news post was not found.
     *
     * @param id The ID of the news post.
     * @return A Mono which emits the news post with the given ID on success.
     */
    Mono<News> getNewsByID(Long id);

    /**
     * Gets a page of news posts consisting of 10 news posts in descending order.
     * The index starts at 1 and the first page contains the newest posts.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the page does not contain news posts.
     *
     * @param page The page to get.
     * @return A Flux of the given page of news posts.
     */
    Flux<News> getNews(Integer page);

    /**
     * Gets a single event by its ID.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the event post was not found.
     *
     * @param id The ID of the event.
     * @return A Mono which emits the event post with the given ID on success.
     */
    Mono<Event> getEventByID(Long id);

    /**
     * Gets a page of events consisting of 10 events in descending order.
     * The index starts at 1 and the first page contains the newest events.<br>
     * <br>
     * Emits an {@link APIException} with the error {@link APIException.Error#NOT_FOUND}
     * if the page does not contain event posts.
     *
     * @param page The page to get.
     * @return A Flux of the given page of event posts.
     */
    Flux<Event> getEvents(Integer page);
}
