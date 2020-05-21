/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Player;
import com.bjsouth.gnr.dto.GameSession;
import com.bjsouth.gnr.dto.SessionPlayer;
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
public class SessionPlayerDAOImpl implements SessionPlayerDAO{
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    PlayerDAO players;
    
    @Autowired
    GameSessionDAO sessions;

    @Override
    public SessionPlayer add(SessionPlayer sessionPlayer) {
        String sql = "insert into session_player (player_id, game_session_id, player_rating, winner) values (?, ?, ?, ?)";
        
        int playerId = sessionPlayer.getPlayer().getId();
        int gameSessionId = sessionPlayer.getGameSession().getId();
        double playerRating = sessionPlayer.getPlayerRating();
        boolean winner = sessionPlayer.isWinner();
        
        jdbc.update(sql, playerId, gameSessionId, playerRating, winner);
        Integer newId = jdbc.queryForObject("select last_insert_id()", Integer.class);
        sessionPlayer.setId(newId);
        
        return sessionPlayer;
    }

    @Override
    public SessionPlayer getOne(int id) {
        String sql = "select * from session_player where id = ?";
        return jdbc.queryForObject(sql, new SessionPlayerMapper(), id);
    }

    @Override
    public List<SessionPlayer> getAll() {
        String sql = "select * from session_player";
        return jdbc.query(sql, new SessionPlayerMapper());
    }

    @Override
    public void edit(SessionPlayer sessionPlayer) {
        String sql = "update session_player set player_id = ?, game_session_id = ?, player_rating = ?, winner = ? where id = ?";
        jdbc.update(sql, sessionPlayer.getPlayer().getId(), sessionPlayer.getGameSession().getId(), sessionPlayer.getPlayerRating(), sessionPlayer.isWinner(), sessionPlayer.getId());
    }

    @Override
    public void remove(SessionPlayer sessionPlayer) {
        String sql = "delete from session_player where id = ?";
        jdbc.update(sql, sessionPlayer.getId());
    }

    @Override
    public List<SessionPlayer> getSessionPlayersByPlayer(Player player) {
        String sql = "select * from session_player where player_id = ?";
        return jdbc.query(sql, new SessionPlayerMapper(), player.getId());
    }
    
    public class SessionPlayerMapper implements RowMapper<SessionPlayer>{

        @Override
        public SessionPlayer mapRow(ResultSet rs, int i) throws SQLException {
            SessionPlayer result = new SessionPlayer();
            
            result.setId(rs.getInt("id"));
            Player player = players.getOne(rs.getInt("player_id"));
            result.setPlayer(player);
            GameSession gameSession = sessions.getone(rs.getInt("game_session_id"));
            result.setGameSession(gameSession);
            result.setPlayerRating(rs.getDouble("player_rating"));
            result.setWinner(rs.getBoolean("winner"));
            
            return result;
        }
        
    }
}
