package com.latelier.tenisu.shared.error;

public class DuplicatePlayerException extends RuntimeException {
    public DuplicatePlayerException(long id) {
        super("Player with ID " + id + " already exists");
    }
}
