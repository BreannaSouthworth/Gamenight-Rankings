/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.gnr.dao;

import com.bjsouth.gnr.dto.Game;
import com.bjsouth.gnr.dto.GameSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
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
public class GameSessionDAOImpl implements GameSessionDAO{
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    GameDAO games;
    
    @Override
    public GameSession add(GameSession gameSession) {
        String sql = "insert into game_session (game_id, start_datetime, session_rating) values (?, ?, ?)";
        
        Game game = gameSession.getGame();
        int gameId = game.getId();
        LocalDateTime startDateTime = gameSession.getStartDateTime();
        double sessionRating = gameSession.getSessionRating();
        
        jdbc.update(sql, gameId, startDateTime, sessionRating);
        Integer newId = jdbc.queryForObject("select last_insert_id()", Integer.class);
        gameSession.setId(newId);
        
        return gameSession;
    }

    @Override
    public GameSession getone(int id) {
        String sql = "select * from game_session where id = ?";
        return jdbc.queryForObject(sql, new GameSessionMapper(), id);
    }

    @Override
    public List<GameSession> getAll() {
        String sql = "select * from game_session order by start_datetime";
        return jdbc.query(sql, new GameSessionMapper());
    }

    @Override
    public void edit(GameSession gameSession) {
        String sql = "update game_session set game_id = ?, start_datetime = ?, end_datetime = ?, session_rating = ? where id = ?";
        jdbc.update(sql, gameSession.getGame().getId(), gameSession.getStartDateTime(), gameSession.getEndDateTime(), gameSession.getSessionRating(), gameSession.getId());
    }

    @Override
    public void remove(GameSession gameSession) {
        String sql = "delete from game_session where id = ?";
        jdbc.update(sql, gameSession.getId());
    }

    @Override
    public List<GameSession> getGameSessionByGame(Game game) {
        String sql = "select * from game_session where game_id = ?";
        return jdbc.query(sql, new GameSessionMapper(), game.getId());
    }

    public class GameSessionMapper implements RowMapper<GameSession>{

        @Override
        public GameSession mapRow(ResultSet rs, int i) throws SQLException {
            GameSession result = new GameSession();
            
            result.setId(rs.getInt("id"));
            
            Game game = games.getOne(rs.getInt("game_id"));
            result.setGame(game);
            
            Timestamp ts1 = rs.getTimestamp("start_datetime");
            result.setStartDateTime(ts1.toLocalDateTime());
            
            Timestamp ts2 = rs.getTimestamp("end_datetime");
            if(ts2 == null){
                result.setEndDateTime(ts1.toLocalDateTime());
            }else{
                result.setEndDateTime(ts2.toLocalDateTime());
            }
            
            result.setMinuteDuration(Duration.between(result.getStartDateTime(), result.getEndDateTime()).toMinutes());
            
            result.setSessionRating(rs.getDouble("session_rating"));
            
            return result;
        }
        
    }
}
