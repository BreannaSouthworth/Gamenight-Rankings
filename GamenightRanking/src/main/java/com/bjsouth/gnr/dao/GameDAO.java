/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Game;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface GameDAO {
    public Game add(Game game);
    public Game getOne(int id);
    public List<Game> getAll();
    public void edit(Game game);
    public void remove(Game game);
}
