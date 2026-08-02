package com.latelier.tenisu.shared.error;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(long id) {
        super("Player with ID " + id + " was not found");
    }
}
