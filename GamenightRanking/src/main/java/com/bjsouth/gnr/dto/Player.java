/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dto;

/**
 *
 * @author Bree
 */
public class Player {
    private int id;
    private String name;
    private int wins;
    
    public Player(){}
    
    public Player(int id, String name, int wins){
        this.id = id;
        this.name = name;
        this.wins = wins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getWins(){
        return wins;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
}
