package ua.danit.dao;

import ua.danit.model.Yamnyk_liked;
import ua.danit.model.Yamnyk_users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikedDAO {

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

    public ArrayList<Yamnyk_liked> getLiked(Long myID){
        ArrayList<Yamnyk_liked> liked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_liked WHERE who='"+myID+"'";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                liked.add(getLikeFromResultSet(rSet));
            }
            return liked;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Yamnyk_users> getUnliked(Long myID, int myGender){
        ArrayList<Yamnyk_users> unLiked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_users WHERE id IN ("+
                "SELECT id FROM yamnyk_users WHERE id NOT IN ("+
                "SELECT id FROM yamnyk_users RIGHT OUTER JOIN yamnyk_liked lkd ON  " +
                "yamnyk_users.id = lkd.whom WHERE who = ?) AND gender != ?)";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ){

            statement.setLong(1, myID);
            statement.setInt(2, myGender);

            ResultSet rSet = statement.executeQuery();
            while(rSet.next()) {
                if(!rSet.getString("id").equals(myID.toString())){
                    unLiked.add(new UsersDAO().getUserFromResultSet(rSet));
                }
            }
            return unLiked;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasBeenLiked(Long myID, Long whom) {
        boolean answ = false;
        for(Yamnyk_liked liked : getLiked(myID)){
            if(liked.getWhom().equals(whom)){
                answ = true;
            }
        }
        return answ;
    }

    public Yamnyk_liked getLikeFromResultSet(ResultSet rSet) throws SQLException{
        Yamnyk_liked like = new Yamnyk_liked();
        like.setLike_id(rSet.getLong("like_id"));
        like.setWho(rSet.getLong("who"));
        like.setWhom(rSet.getLong("whom"));
        like.setTime(rSet.getTimestamp("time"));
        return like;
    }
}
