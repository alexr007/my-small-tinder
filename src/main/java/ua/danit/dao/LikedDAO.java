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

    public ArrayList<Yamnyk_liked> getLiked(Long myID){
        ArrayList<Yamnyk_liked> liked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_liked WHERE who='"+myID+"'";

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

    public ArrayList<Yamnyk_users> getUnliked(Long myID, int myGender){
        ArrayList<Yamnyk_users> unLiked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_users WHERE id IN ("+
                "SELECT id FROM yamnyk_users WHERE id NOT IN ("+
                "SELECT id FROM yamnyk_users RIGHT OUTER JOIN yamnyk_liked lkd ON  " +
                "yamnyk_users.id = lkd.whom WHERE who = '"+myID+"') AND gender != '"+myGender+"')";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                if(!rSet.getString("id").equals(myID.toString())){
                    Yamnyk_users user = new Yamnyk_users();
                    user.setId(rSet.getLong("id"));
                    user.setName(rSet.getString("name"));
                    user.setImgURL(rSet.getString("imgURL"));
                    user.setPassword(rSet.getString("password"));
                    user.setEmail(rSet.getString("email"));
                    user.setGender(rSet.getInt("gender"));
                    unLiked.add(user);
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
}
