package com.soundlab.exceptions;

public class NoMessagesInQueueException extends RuntimeException {
    public NoMessagesInQueueException() {
        super("Queue is empty");
    }
}
