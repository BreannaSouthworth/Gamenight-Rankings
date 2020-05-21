/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.controllers;

import com.bjsouth.gnr.dto.*;
import com.bjsouth.gnr.services.GNRService;
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
public class SessionPlayerController {
    @Autowired
    GNRService service;
    
    @GetMapping("session_player_edit")
    public String getSessionPlayerEdit(Integer id, Model model){
        SessionPlayer sessionPlayer = service.getOneSessionPlayer(id);
        model.addAttribute("sessionPlayer", sessionPlayer);
        return "session_player_edit";
    }
    
    @PostMapping("edit_session_player")
    public String postSessionPlayerEdit(SessionPlayer sessionPlayer, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        sessionPlayer = service.getOneSessionPlayer(id);
        
        double playerRating = Double.valueOf(request.getParameter("playerRating"));
        String winnerString = request.getParameter("winner");
        boolean winner = winnerString.equalsIgnoreCase("true");
        
        sessionPlayer.setPlayerRating(playerRating);
        sessionPlayer.setWinner(winner);
        service.editSessionPlayer(sessionPlayer);
        return "redirect:/game_session_edit?id="+sessionPlayer.getGameSession().getId();
    }
}
