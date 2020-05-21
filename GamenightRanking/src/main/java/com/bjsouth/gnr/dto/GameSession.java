/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Bree
 */
public class GameSession {
    private int id;
    private Game game;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double sessionRating;
    private List<SessionPlayer> sessionPlayers;
    private long minuteDuration;
    
    public GameSession(){}

    public GameSession(int id, Game game, LocalDateTime startDateTime, LocalDateTime endDateTime, double sessionRating, List<SessionPlayer> sessionPlayers, long minuteDuration) {
        this.id = id;
        this.game = game;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.sessionRating = sessionRating;
        this.sessionPlayers = sessionPlayers;
        this.minuteDuration = minuteDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getSessionRating() {
        return sessionRating;
    }

    public void setSessionRating(double sessionRating) {
        this.sessionRating = sessionRating;
    }

    public List<SessionPlayer> getSessionPlayers() {
        return sessionPlayers;
    }

    public void setSessionPlayers(List<SessionPlayer> sessionPlayers) {
        this.sessionPlayers = sessionPlayers;
    }

    public long getMinuteDuration() {
        return minuteDuration;
    }

    public void setMinuteDuration(long minuteDuration) {
        this.minuteDuration = minuteDuration;
    }
}
