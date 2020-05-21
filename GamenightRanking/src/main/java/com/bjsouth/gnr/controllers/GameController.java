/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.controllers;

import com.bjsouth.gnr.dto.*;
import com.bjsouth.gnr.services.GNRService;
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
public class GameController {
    @Autowired
    GNRService service;
    
    @GetMapping({"/games_all"})
    public String getAllGames(Model model){
        List<Game> gameList = service.getAllGames();
        for(Game game : gameList){
            service.calculateOverallRating(game);
        }
        model.addAttribute("games", gameList);
        return "games_all";
    }
    
    @GetMapping("/game")
    public String getGame(Integer id, Model model){
        Game game = service.getOneGame(id);
        List<GameSession> gameSessions = service.getGameSessionsByGame(game);
        for(GameSession gs : gameSessions){
            service.setSessionPlayersByGameSession(gs);
            service.calculateSessionRating(gs);
        }
        List<SessionPlayer> sessionPlayers = service.getAllSessionPlayers();
        List<Player> players = service.getAllPlayers();
        model.addAttribute("sessionPlayers", sessionPlayers);
        model.addAttribute("players", players);
        model.addAttribute("gameSessions", gameSessions);
        model.addAttribute("game", game);
        return "game";
    }
    
    @GetMapping("/game_add")
    public String getAddGame(Model model){
        return "game_add";
    }
    
    @PostMapping("add_game")
    public String postAddGame(Game game, HttpServletRequest request){
        String name = request.getParameter("name");
        String gameType = request.getParameter("gameType");
        String coopString = request.getParameter("coop");
        boolean coop = coopString.equalsIgnoreCase("true");
        
        game.setName(name);
        game.setGameType(gameType);
        game.setCoop(coop);
        game.setOverallRating(0.0);
        service.addGame(game);
        return "redirect:/games_all";
    }
    
    @GetMapping("game_edit")
    public String getEditGame(Integer id, Model model){
        Game game = service.getOneGame(id);
        model.addAttribute("game", game);
        return "game_edit";
    }
    
    @PostMapping("edit_game")
    public String postEditGame(Game game, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        game = service.getOneGame(id);
        String name = request.getParameter("name");
        String gameType = request.getParameter("gameType");
        String coopString = request.getParameter("coop");
        boolean coop = coopString.equalsIgnoreCase("true");
        
        game.setName(name);
        game.setGameType(gameType);
        game.setCoop(coop);
        service.editGame(game);
        return "redirect:/games_all";
    }
    
    @GetMapping("game_delete")
    public String getDeleteGame(Integer id){
        Game game = service.getOneGame(id);
        service.removeGame(game);
        return "redirect:/games_all";
    }
}
