/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.controllers;

import com.bjsouth.gnr.dto.*;
import com.bjsouth.gnr.services.GNRService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Bree
 */
@Controller
public class GameSessionController {
    @Autowired
    GNRService service;
    
    @GetMapping("/game_sessions_all")
    public String getAllGameSessions(Model model){
        List<GameSession> gameSessions = service.getAllGameSessions();
        for(GameSession gs : gameSessions){
            service.setSessionPlayersByGameSession(gs);
            service.calculateSessionRating(gs);
        }
        List<SessionPlayer> sessionPlayers = service.getAllSessionPlayers();
        List<Player> players = service.getAllPlayers();
        List<Game> games = service.getAllGames();
        
        model.addAttribute("gameSessions", gameSessions);
        model.addAttribute("sessionPlayers", sessionPlayers);
        model.addAttribute("players", players);
        model.addAttribute("games", games);
        return "game_sessions_all";
    }
    
    @GetMapping("game_session")
    public String getGameSession(Integer id, Model model){
        GameSession gameSession = service.getOneGameSession(id);
        service.setSessionPlayersByGameSession(gameSession);
        model.addAttribute("gameSession", gameSession);
        return "game_session";
    }
    
    @GetMapping("/game_session_add")
    public String getAddGameSession(Model model){
        List<Game> games = service.getAllGames();
        List<Player> players = service.getAllPlayers();
        model.addAttribute("games", games);
        model.addAttribute("players", players);
        return "game_session_add";
    }
    
    @PostMapping("add_game_session")
    public String postAddGameSession(GameSession gameSession, HttpServletRequest request){
        //NEED: game, and startDateTime. sessionRating is 0.0
        String gameString = request.getParameter("gameId");
        int gameId = Integer.parseInt(gameString);
        Game game = service.getOneGame(gameId);
        
        String dateTimeString = request.getParameter("date")+"T"+request.getParameter("time");
        LocalDateTime startDateTime = LocalDateTime.parse(dateTimeString);
        
        //set varibles to gameSession
        gameSession.setGame(game);
        gameSession.setStartDateTime(startDateTime);
        gameSession.setSessionRating(0.0);
        
        //save gameSession with service
        service.addGameSession(gameSession);
        
        //now that a gameSession exists, create sessionPlayers using that gs and List of Players
        //List of Players
        String[] playerList = request.getParameterValues("sessionPlayersIds");
        List<Player> players = new ArrayList<>();
        for(String p : playerList){
            players.add(service.getOnePlayer(Integer.parseInt(p)));
        }
        
        //for each player, make a SessionPlayer and save it to service
        for(Player p : players){
            SessionPlayer sp = new SessionPlayer();
            sp.setPlayer(p);
            sp.setGameSession(gameSession);
            sp.setPlayerRating(0.0);
            sp.setWinner(false);
            
            service.addSessionPlayer(sp);
        }
        
        return "redirect:/game_sessions_all";
    }
    
    @GetMapping("/game_session_edit")
    public String getEditGameSession(Integer id, Model model){
        GameSession gameSession = service.getOneGameSession(id);
        service.setSessionPlayersByGameSession(gameSession);
        List<Player> players = service.getAllPlayers();
        List<Game> games = service.getAllGames();
        model.addAttribute("gameSession", gameSession);
        model.addAttribute("games", games);
        model.addAttribute("players", players);
        return "game_session_edit";
    }
    
    @PostMapping("edit_game_session")
    public String postEditGameSession(GameSession gameSession, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        gameSession = service.getOneGameSession(id);
        
        String dateTimeString = request.getParameter("date")+"T"+request.getParameter("time");
        LocalDateTime endDateTime = LocalDateTime.parse(dateTimeString);
        
        gameSession.setEndDateTime(endDateTime);
        
        service.editGameSession(gameSession);
        return "redirect:/game_sessions_all";
    }
    
    @GetMapping("/game_session_delete")
    public String getDeleteGameSession(Integer id){
        GameSession gameSession = service.getOneGameSession(id);
        service.removeGameSession(gameSession);
        return "redirect:/game_sessions_all";
    }
}
