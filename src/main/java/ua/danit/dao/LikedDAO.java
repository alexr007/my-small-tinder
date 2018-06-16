package ua.danit.dao;

import ua.danit.model.Yamnyk_liked;
import ua.danit.model.Yamnyk_users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikedDAO extends AbstractDAO<Yamnyk_liked> {

    @Override
    public void save(Yamnyk_liked like) {
        String sql = "INSERT INTO yamnyk_liked(like_id, who, whom, time) VALUES(?,?,?,?)";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, like.getLike_id());
            statement.setLong(2, like.getWho());
            statement.setLong(3, like.getWhom());
            statement.setTimestamp(4, like.getTime());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Yamnyk_liked like) {
        String sql = "UPDATE yamnyk_liked SET who=?, whom=?, time=? WHERE id=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, like.getWho());
            statement.setLong(2, like.getWhom());
            statement.setTimestamp(3, like.getTime());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Yamnyk_liked get(Long id) {
        Yamnyk_liked like = new Yamnyk_liked();

        String sql = "SELECT * FROM yamnyk_liked WHERE id='"+id+"'";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            like.setLike_id(rSet.getLong("like_ia"));
            like.setWho(rSet.getLong("who"));
            like.setWhom(rSet.getLong("whom"));
            like.setTime(rSet.getTimestamp("time"));

            return like;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long pk) {
        String sql = "DELETE FROM yamnyk_liked WHERE id=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, pk);

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
