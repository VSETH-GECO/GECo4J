package ch.ethz.geco.g4j.obj;

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
     * Gets a user by its ID.
     *
     * @param id The ID of the user to get.
     * @return The user with the provided ID or null if none was found.
     */
    Mono<User> getUserByID(Long id);

    /**
     * Gets a user by its Discord ID.
     *
     * @param id The Discord ID of the user to get.
     * @return The user with the provided Discord ID or null if none was found.
     */
    Mono<User> getUserByDiscordID(Long id);

    /**
     * Gets a list of all seats of the current LAN event.
     *
     * @return A list of all seats.
     */
    Flux<Seat> getSeats();

    /**
     * Gets a seat by its ID.
     *
     * @param id The ID of the seat to get.
     * @return The seat with the provided ID or null if none was found.
     */
    Mono<Seat> getSeatByID(Long id);

    /**
     * Gets a lan user by its ID.
     *
     * @param id The ID of the lan user to get.
     * @return The lan user with the provided ID or null if none was found.
     */
    Mono<LanUser> getLanUserByID(Long id);

    /**
     * Gets a lan user by its name.
     *
     * @param name The name of the lan user to get.
     * @return The lan user with the provided name or null if none was found.
     */
    Mono<LanUser> getLanUserByName(String name);

    /**
     * Gets a seat by its name. If there are duplicate seat names like with admin seats, this will just return
     * one seat with the given name.
     *
     * @param name The name of the seat to get.
     * @return A seat with the provided name or null if none was found.
     */
    Mono<Seat> getSeatByName(String name);

    /**
     * Gets a single news post by its ID.
     *
     * @param id The ID of the news post.
     * @return The news post with the provided ID or null if none was found.
     */
    Mono<News> getNewsByID(Long id);

    /**
     * Gets a page of news posts consisting of 10 news posts in descending order.
     * The index starts at 1 and the first page contains the newest posts.
     *
     * @param page The page to get.
     * @return A list of news posts.
     */
    Flux<News> getNews(Integer page);

    /**
     * Gets a single event by its ID.
     *
     * @param id The ID of the event.
     * @return The event with the provided ID or null if none was found.
     */
    Mono<Event> getEventByID(Long id);

    /**
     * Gets a page of events consisting of 10 events in descending order.
     * The index starts at 1 and the first page contains the newest events.
     *
     * @param page The page to get.
     * @return A list of events.
     */
    Flux<Event> getEvents(Integer page);
}
