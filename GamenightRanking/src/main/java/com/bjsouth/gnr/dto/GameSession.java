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
    Game game;
    LocalDateTime startTime;
    LocalDateTime endTime;
    float sessionRating;
    List<SessionPlayer> sessionPlayers;
}
