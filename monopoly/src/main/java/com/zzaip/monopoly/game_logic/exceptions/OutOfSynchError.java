package com.zzaip.monopoly.game_logic.exceptions;

import com.zzaip.monopoly.exceptions.FatalError;

public class OutOfSynchError extends FatalError {
    public OutOfSynchError(String message) {
        super("""
            GAME IS NOT SYNCHRONISED!
            exit this game and create a new one
            reason:
            """ + message);
    }
}
