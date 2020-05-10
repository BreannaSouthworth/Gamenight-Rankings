/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.controllers;

import com.bjsouth.gnr.services.GNRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Bree
 */
@Controller
public class GameSessionController {
    @Autowired
    GNRService service;
    
    @GetMapping({"/game_sessions"})
    public String getAllGameSessions(Model model){
        return "all_game_sessions";
    }
}