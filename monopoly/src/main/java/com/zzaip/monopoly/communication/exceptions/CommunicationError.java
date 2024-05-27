package com.zzaip.monopoly.communication.exceptions;

import com.zzaip.monopoly.exceptions.FatalError;

public class CommunicationError extends FatalError {
    public CommunicationError(String message) {
        super("""
                Communication error.
                Exit the game and create/join new one
                """+message);
    }
}
