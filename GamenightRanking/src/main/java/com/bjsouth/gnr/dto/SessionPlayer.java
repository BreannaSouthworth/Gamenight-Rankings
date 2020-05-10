/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dto;

/**
 *
 * @author Bree
 */
public class SessionPlayer {
    private int id;
    private Player player;
    private GameSession gameSession;
    private double playerRating;
    private boolean winner;
    
    public SessionPlayer(){}

    public SessionPlayer(int id, Player player, GameSession gameSession, double playerRating, boolean winner) {
        this.id = id;
        this.player = player;
        this.gameSession = gameSession;
        this.playerRating = playerRating;
        this.winner = winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public double getPlayerRating() {
        return playerRating;
    }

    public void setPlayerRating(double playerRating) {
        this.playerRating = playerRating;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
