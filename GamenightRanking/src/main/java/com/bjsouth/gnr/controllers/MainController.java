/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.controllers;

import com.bjsouth.gnr.dto.*;
import com.bjsouth.gnr.services.GNRService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Bree
 */
@Controller
public class MainController {
    @Autowired
    GNRService service;
    
    @GetMapping({"/home", "/"})
    public String getIndex(Model model){
        GameSession recentSession = service.findLatestGameSession();
        List<Game> topRatedGames = service.findTopRatedGames();
        Map<Game, Integer> leastPlayedGames = service.findLeastPlayedGames();
        
        //load all into model
        model.addAttribute("recentGameSession", recentSession);
        model.addAttribute("topRatedGames", topRatedGames);
        model.addAttribute("leastPlayedGames", leastPlayedGames);
        return "index";
    }
    
//    @GetMapping("/error")
//    public String getError(Model model){
//        return "index";
//    }
}
