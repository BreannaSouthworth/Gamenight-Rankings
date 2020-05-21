/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.controllers;

import com.bjsouth.gnr.dto.*;
import com.bjsouth.gnr.services.GNRService;
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
public class PlayerController {
    @Autowired
    GNRService service;
    
    @GetMapping("/players_all")
    public String getAllPlayers(Model model){
        service.calculatePlayerWins();
        model.addAttribute("players", service.getAllPlayers());
        return "players_all";
    }
    
    @GetMapping("/player")
    public String getPlayer(Integer id, Model model){
        Player player = service.getOnePlayer(id);
        List<SessionPlayer> sessionPlayers = service.getSessionPlayersByPlayer(player);
        List<GameSession> gameSessions = new ArrayList<>();
        for(SessionPlayer sp : sessionPlayers){
            GameSession gs = sp.getGameSession();
            gameSessions.add(gs);
        }
        for(GameSession gs : gameSessions){
            service.setSessionPlayersByGameSession(gs);
            service.calculateSessionRating(gs);
        }
        
        model.addAttribute("player", player);
        model.addAttribute("gameSessions", gameSessions);
        return "player";
    }
    
    @GetMapping("/player_add")
    public String getAddPlayer(Model model){
        return "player_add";
    }
    
    @PostMapping("add_player")
    public String postAddPlayer(Player player, HttpServletRequest request){
        String name = request.getParameter("playerName");
        player.setName(name);
        service.addPlayer(player);
        return "redirect:/players_all";
    }
    
    @GetMapping("player_edit")
    public String getEditPlayer(Integer id, Model model){
        Player player = service.getOnePlayer(id);
        model.addAttribute("player", player);
        return "player_edit";
    }
    
    @PostMapping("edit_player")
    public String postEditPlayer(Player player, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        player = service.getOnePlayer(id);
        String name = request.getParameter("name");
        player.setName(name);
        service.editPlayer(player);
        return "redirect:/players_all";
    }
    
    @GetMapping("player_delete")
    public String getDeletePlayer(Integer id){
        Player player = service.getOnePlayer(id);
        service.removePlayer(player);
        return "redirect:/players_all";
    }
}
