/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bree
 */
@Repository
public class GameDAOImpl implements GameDAO{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Game add(Game game) {
        String sql = "inset into game (name, game_type, co_op, overall_rating)";
        
        String name = game.getName();
        String gameType = game.getGameType();
        boolean coop = game.isCoop();
        double overallRating = game.getOverallRating();
        
        jdbc.update(sql, name, gameType, coop, overallRating);
        Integer newId = jdbc.queryForObject("select last_insert_id()", Integer.class);
        game.setId(newId);
        
        return game;
    }

    @Override
    public Game getOne(int id) {
        String sql = "select * from game where id = ?";
        return jdbc.queryForObject(sql, new GameMapper(), id);
    }

    @Override
    public List<Game> getAll() {
        String sql = "select * from game";
        return jdbc.query(sql, new GameMapper());
    }

    @Override
    public void edit(Game game) {
        String sql = "update game set name = ?, game_type = ?, co_op = ?, overall_rating = ? where id = ?";
        jdbc.update(sql, game.getName(), game.getGameType(), game.isCoop(), game.getOverallRating(), game.getId());
    }

    @Override
    public void remove(Game game) {
        String sql = "delete from game where id = ?";
        jdbc.update(sql, game.getId());
    }
    
    public class GameMapper implements RowMapper<Game>{

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game result = new Game();
            
            result.setId(rs.getInt("id"));
            result.setName(rs.getString("name"));
            result.setGameType(rs.getString("game_type"));
            result.setCoop(rs.getBoolean("co_op"));
            result.setOverallRating(rs.getDouble("overall_rating"));
            
            return result;
        }
        
    }
}
