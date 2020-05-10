/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.services;

import com.bjsouth.gnr.dto.Game;
import com.bjsouth.gnr.dto.GameSession;
import com.bjsouth.gnr.dto.Player;
import com.bjsouth.gnr.dto.SessionPlayer;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface GNRService {
    //DAO CRRUD passthroughs with rules (if any)
    
    //Player
    public Player addPlayer(Player player);
    public Player getOnePlayer(int id);
    public List<Player> getAllPlayers();
    public void editPlayer(Player player);
    public void removePlayer(Player player);
    
    //Game
    public Game addGame(Game game);
    public Game getOneGame(int id);
    public List<Game> getAllGames();
    public void editGame(Game game);
    public void removeGame(Game game);
    
    //GameSession
    public GameSession addGameSession(GameSession gameSession);
    public GameSession getOneGameSession(int id);
    public List<GameSession> getAllGameSessions();
    public void editGameSession(GameSession gameSession);
    public void removeGameSession(GameSession gameSession);
    
    //SessionPlayer
    public SessionPlayer addSessionPlayer(SessionPlayer sessionPlayer);
    public SessionPlayer getOneSessionPlayer(int id);
    public List<SessionPlayer> getAllSessionPlayers();
    public void editSessionPlayer(SessionPlayer sessionPlayer);
    public void removeSessionPlayer(SessionPlayer sessionPlayer);
    
    //Other Functions
    public List<SessionPlayer> getSessionPlayersByPlayer(Player player);
    public List<GameSession> getGameSessionsByGame(Game game);
    public GameSession findLatestGameSession();
    
    //Rating Calucations
    public void calculateSessionRating(GameSession gameSession);
    public void calculateOverallRating(Game game);
}
