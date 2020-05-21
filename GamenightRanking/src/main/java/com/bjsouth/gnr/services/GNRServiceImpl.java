/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.services;

import com.bjsouth.gnr.dao.*;
import com.bjsouth.gnr.dto.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bree
 */
@Service
public class GNRServiceImpl implements GNRService {
    @Autowired
    PlayerDAO players;
    
    @Autowired
    GameDAO games;
    
    @Autowired
    GameSessionDAO gameSessions;
    
    @Autowired
    SessionPlayerDAO sessionPlayers;

    @Override
    public Player addPlayer(Player player) {
        return players.add(player);
    }

    @Override
    public Player getOnePlayer(int id) {
        return players.getOne(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return players.getAll();
    }

    @Override
    public void editPlayer(Player player) {
        players.edit(player);
    }

    @Override
    public void removePlayer(Player player) {
        //check that the player is not a sessionPlayer
        players.remove(player);
    }

    @Override
    public Game addGame(Game game) {
        return games.add(game);
    }

    @Override
    public Game getOneGame(int id) {
        return games.getOne(id);
    }

    @Override
    public List<Game> getAllGames() {
        return games.getAll();
    }

    @Override
    public void editGame(Game game) {
        games.edit(game);
    }

    @Override
    public void removeGame(Game game) {
        //check the game is not in a gameSession
        games.remove(game);
    }

    @Override
    public GameSession addGameSession(GameSession gameSession) {
        return gameSessions.add(gameSession);
    }

    @Override
    public GameSession getOneGameSession(int id) {
        return gameSessions.getone(id);
    }

    @Override
    public List<GameSession> getAllGameSessions() {
        return gameSessions.getAll();
    }

    @Override
    public void editGameSession(GameSession gameSession) {
        gameSessions.edit(gameSession);
    }

    @Override
    public void removeGameSession(GameSession gameSession) {
        //check the gameSession is not in a sessionPlayer
        //if it is, delete if the sessionPlayer doesn't have a rating or isn't the winner
        //make sure to also delete those sessionPlayers
        gameSessions.remove(gameSession);
    }

    @Override
    public SessionPlayer addSessionPlayer(SessionPlayer sessionPlayer) {
        GameSession gameSession = sessionPlayer.getGameSession();
        List<SessionPlayer> playerList = gameSession.getSessionPlayers();
        playerList.add(sessionPlayer);
        
        return sessionPlayers.add(sessionPlayer);
    }

    @Override
    public SessionPlayer getOneSessionPlayer(int id) {
        return sessionPlayers.getOne(id);
    }

    @Override
    public List<SessionPlayer> getAllSessionPlayers() {
        return sessionPlayers.getAll();
    }

    @Override
    public void editSessionPlayer(SessionPlayer sessionPlayer) {
        //update this sessionPlayer in the playerList of its gameSession
        sessionPlayers.edit(sessionPlayer);
    }

    @Override
    public void removeSessionPlayer(SessionPlayer sessionPlayer) {
        //remove this sessionPLayer in the playerList of its gameSession as well
        sessionPlayers.remove(sessionPlayer);
    }

    @Override
    public List<SessionPlayer> getSessionPlayersByPlayer(Player player) {
        //check if player exists in database first??
        return sessionPlayers.getSessionPlayersByPlayer(player);
    }

    @Override
    public List<GameSession> getGameSessionsByGame(Game game) {
        //check if game exists in database first??
        return gameSessions.getGameSessionByGame(game);
    }

    @Override
    public GameSession findLatestGameSession() {
        return gameSessions.getAll().get(0);
    }
    
    @Override
    public List<Game> findTopRatedGames(){
        Map<Game, Double> unsortedGames = new HashMap<>();
        List<Game> allGames = this.getAllGames();
        for(Game game : allGames){
            this.calculateOverallRating(game);
            Double rating = game.getOverallRating();
            unsortedGames.put(game, rating);
        }
        LinkedHashMap<Game, Double> topRatedMap = new LinkedHashMap<>();
        unsortedGames.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> topRatedMap.put(x.getKey(), x.getValue()));
        
        List<Game> topRatedGames = new ArrayList<>(topRatedMap.keySet());
        return topRatedGames;
    }
    
    @Override
    public Map<Game, Integer> findLeastPlayedGames(){
        Map<Game, Integer> unsortedGames = new HashMap<>();
        List<Game> allGames = this.getAllGames();
        //find out how often a game is played by the amount of GameSession that have it
        for(Game game : allGames){
            List<GameSession> sessionsOfGame = this.getGameSessionsByGame(game);
            Integer occurence = sessionsOfGame.size();
            //save in a map <Game, Integer> where integer is times it's played
            unsortedGames.put(game, occurence);
        }
        //sort by decreasing Integer value using found solution
        LinkedHashMap<Game, Integer> leastPlayedMap = new LinkedHashMap<>();
        
        unsortedGames.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> leastPlayedMap.put(x.getKey(), x.getValue()));
        
        return leastPlayedMap;
    }
    
    @Override
    public void calculatePlayerWins(){
        List<Player> playerList = this.getAllPlayers();
        for(Player p : playerList){
            List<SessionPlayer> spList = this.getSessionPlayersByPlayer(p);
            for(SessionPlayer sp : spList){
                if(sp.isWinner()){
                    int wins = p.getWins();
                    wins += 1;
                    p.setWins(wins);
                }
            }
        }
    }
    
    @Override
    public void setSessionPlayersByGameSession(GameSession gameSession){
        List<SessionPlayer> SPByGSList = new ArrayList<>();
        for(SessionPlayer sp : this.getAllSessionPlayers()){
            if(sp.getGameSession().getId() == gameSession.getId()){
                SPByGSList.add(sp);
            }
        }
        gameSession.setSessionPlayers(SPByGSList);
    }

    @Override
    public void calculateSessionRating(GameSession gameSession) {
        List<SessionPlayer> spList = gameSession.getSessionPlayers();
        double num = 0.0;
        double denum = spList.size();
        
        for(SessionPlayer player : spList){
            num += player.getPlayerRating();
        }
        
        if(num <= 0.0){
           gameSession.setSessionRating(0.0);
        } else {
            gameSession.setSessionRating(num/denum);
        }
        
        this.editGameSession(gameSession);
    }

    @Override
    public void calculateOverallRating(Game game) {
        List<GameSession> gsList = this.getGameSessionsByGame(game);
        double num = 0.0;
        double denum = gsList.size();
        
        for(GameSession gs : gsList){
            num += gs.getSessionRating();
        }
        
        if(num <= 0.0){
            game.setOverallRating(0.0);
        } else {
            game.setOverallRating(num/denum);
        }
        
        this.editGame(game);
    }
    
}
