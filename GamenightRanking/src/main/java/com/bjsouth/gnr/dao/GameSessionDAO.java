/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Game;
import com.bjsouth.gnr.dto.GameSession;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface GameSessionDAO {
    public GameSession add(GameSession gameSession);
    public GameSession getone(int id);
    public List<GameSession> getAll();
    public void edit(GameSession gameSession);
    public void remove(GameSession gameSession);
    public List<GameSession> getGameSessionByGame(Game game);
}
