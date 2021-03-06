package com.prototype.service;

import com.prototype.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hjm
 * @Time 2016/5/1 19:03.
 */
@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


  /*  public User findByUserName(String userName){

        User user = new User();
        String sql = "SELECT id,user_name FROM p_user";
        return (User) jdbcTemplate.query(sql, new RowMapper<User>(){

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User stu = new User();
                stu.setId(rs.getLong("id"));
                stu.setUserName(rs.getString("user_name"));
                return stu;
            }

        });
        return user;
    }*/

    public List<User> getList(){

        String sql = "SELECT id,user_name FROM p_user";
        return (List<User>) jdbcTemplate.query(sql, new RowMapper<User>(){

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User stu = new User();
                stu.setId(rs.getLong("id"));
                stu.setUserName(rs.getString("user_name"));
                return stu;
            }

        });
    }

}
