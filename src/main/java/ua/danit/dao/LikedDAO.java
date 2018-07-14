package ua.danit.dao;

import ua.danit.model.Like;
import ua.danit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikedDAO {

    public void save(Like like) {
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

    public void update(Like like) {
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

    public ArrayList<Like> getLikesByUserID(Long myID){
        ArrayList<Like> liked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_liked WHERE who=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){

        	statement.setLong(1, myID);

			ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                liked.add(getLikeFromResultSet(rSet));
            }

            return liked;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public ArrayList<User> getUnliked(Long myID, int myGender){
        ArrayList<User> unLiked = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_users WHERE id IN ("+
                "SELECT id FROM yamnyk_users WHERE id NOT IN ("+
                "SELECT id FROM yamnyk_users RIGHT OUTER JOIN yamnyk_liked lkd ON  " +
                "yamnyk_users.id = lkd.whom WHERE who = ?) AND gender != ?)";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){

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

        return new ArrayList<>();
    }

    public boolean hasBeenLiked(Long myID, Long whom) {
        return getLikesByUserID(myID).stream()
				.anyMatch(l -> l.getWhom()
				.equals(whom));
    }

    public Like getLikeFromResultSet(ResultSet rSet) throws SQLException{
        Like like = new Like();
        like.setLike_id(rSet.getLong("like_id"));
        like.setWho(rSet.getLong("who"));
        like.setWhom(rSet.getLong("whom"));
        like.setTime(rSet.getTimestamp("time"));

        return like;
    }
}
