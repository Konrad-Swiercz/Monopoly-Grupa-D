package com.zzaip.monopoly.game_logic.api;

import com.zzaip.monopoly.dto.GameMessage;
import com.zzaip.monopoly.exceptions.FatalError;
import com.zzaip.monopoly.game_logic.exceptions.GameLogicException;
import com.zzaip.monopoly.game_logic.exceptions.OutOfSynchError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameExceptionHandler {
    @ExceptionHandler(GameLogicException.class)
    public ResponseEntity<GameMessage> handleRuntimeException(GameLogicException exception) {
        String errorMessage = exception.getMessage();
        GameMessage message = new GameMessage(errorMessage);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ExceptionHandler(FatalError.class)
    public ResponseEntity<GameMessage> handleRuntimeException(OutOfSynchError exception) {
        String errorMessage = exception.getMessage();
        GameMessage message = new GameMessage(errorMessage);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GameMessage> handleRuntimeException(RuntimeException exception) {
        String errorMessage = exception.getMessage();
        GameMessage message = new GameMessage(errorMessage);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GameMessage> handleUnexpectedException() {
        String errorMessage = "Unexpected error";
        GameMessage message = new GameMessage(errorMessage);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
