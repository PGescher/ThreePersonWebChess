package com.webapp.jchess.model.moves;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;

public class RemovePlayer extends AbstractMove{

    AbstractGame game;
    Player removedPlayer;
    int originalIdx;

    public RemovePlayer(AbstractGame _game, Player _player){
        this.game = _game;
        this.removedPlayer = _player;
    }

    @Override
    public void execute_move() {
        originalIdx = game.removePlayer(removedPlayer);
    }

    @Override
    public void undo_move() {
        game.reAddPlayer(removedPlayer,originalIdx);
    }

    @Override
    public String toString() {
        return "DEAD";
    }
    
}
