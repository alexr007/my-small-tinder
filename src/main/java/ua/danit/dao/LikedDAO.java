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
        String sql = "INSERT INTO yamnyk_liked(who, whom, time) VALUES(?,?,?)";
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
    public void update(Yamnyk_liked like) {
        String sql = "UPDATE yamnyk_liked SET time=? WHERE whom=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setTimestamp(1, like.getTime());
            statement.setLong(2, like.getWhom());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Yamnyk_liked get(Long id) {
        return null;
    }

    public ArrayList<Yamnyk_liked> getLiked(){
        ArrayList<Yamnyk_liked> liked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_liked";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                Yamnyk_liked like = new Yamnyk_liked();
                like.setLike_id(rSet.getLong("like_id"));
                like.setWho(rSet.getLong("who"));
                like.setWhom(rSet.getLong("whom"));
                like.setTime(rSet.getTimestamp("time"));

                liked.add(like);
            }
            return liked;

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

    public boolean hasBeenLiked(Long whom) {
        boolean answ = false;
        for(Yamnyk_liked liked :getLiked()){
            if(liked.getWhom().equals(whom)){
                answ = true;
            }
        }
        return answ;
    }
}
