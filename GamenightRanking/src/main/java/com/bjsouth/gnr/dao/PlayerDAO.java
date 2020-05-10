/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Player;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface PlayerDAO {
    public Player add(Player player);
    public Player getOne(int id);
    public List<Player> getAll();
    public void edit(Player player);
    public void remove(Player player);
}
