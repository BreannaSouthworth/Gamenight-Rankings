/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Player;
import com.bjsouth.gnr.dto.SessionPlayer;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface SessionPlayerDAO {
    public SessionPlayer add(SessionPlayer sessionPlayer);
    public SessionPlayer getOne(int id);
    public List<SessionPlayer> getAll();
    public void edit(SessionPlayer sessionPlayer);
    public void remove(SessionPlayer sessionPlayer);
    public List<SessionPlayer> getSessionPlayersByPlayer(Player player);
}
