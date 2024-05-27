package com.zzaip.monopoly.game_logic.exceptions;

import com.zzaip.monopoly.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameLogicException extends BaseException {
    public GameLogicException(String message) {
        super(message);
    }
}
