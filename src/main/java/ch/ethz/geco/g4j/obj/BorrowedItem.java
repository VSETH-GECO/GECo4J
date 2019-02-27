package ch.ethz.geco.g4j.obj;

import reactor.core.publisher.Mono;

public interface BorrowedItem {
    /**
     * Gets the ID of the borrowed item.
     *
     * @return The ID.
     */
    Long getID();

    /**
     * Gets the name of the borrowed item.
     *
     * @return The name.
     */
    String getName();

    /**
     * Removes the item from the list of borrowed items.
     */
    Mono<Void> remove();
}
