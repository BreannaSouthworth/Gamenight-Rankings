/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Player;
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
public class PlayerDAOImpl implements PlayerDAO{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Player add(Player player) {
        String sql = "insert into player (name) values (?)";
        
        String name = player.getName();
        jdbc.update(sql, name);
        Integer newId = jdbc.queryForObject("select last_insert_id()", Integer.class);
        player.setId(newId);
        
        return player;
    }

    @Override
    public Player getOne(int id) {
        String sql = "select * from player where id = ?";
        return jdbc.queryForObject(sql, new PlayerMapper(), id);
    }

    @Override
    public List<Player> getAll() {
        String sql = "select * from player";
        return jdbc.query(sql, new PlayerMapper());
    }

    @Override
    public void edit(Player player) {
        String sql = "update player set name = ? where id = ?";
        jdbc.update(sql, player.getName(), player.getId());
    }

    @Override
    public void remove(Player player) {
        String sql = "delete from player where id = ?";
        jdbc.update(sql, player.getId());
    }
    
    public class PlayerMapper implements RowMapper<Player>{

        @Override
        public Player mapRow(ResultSet rs, int i) throws SQLException {
            Player result = new Player();
            
            result.setId(rs.getInt("id"));
            result.setName(rs.getString("name"));
            
            return result;
        }
        
    }
}
