package com.zzaip.monopoly.exceptions;

public class FatalError extends BaseException {
    public FatalError(String message) {
        super("FATAL ERROR!\n" + message);
    }
}
