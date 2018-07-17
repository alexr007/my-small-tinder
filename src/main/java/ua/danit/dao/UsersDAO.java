package ua.danit.dao;


import ua.danit.model.Like;
import ua.danit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO{
    public void save(User user) {
        String sql = "INSERT INTO yamnyk_users(id, name, imgURL, gender, password, email) VALUES(?,?,?,?,?,?)";

        try(PreparedStatement statement = ConnectionToDB.getConnection().prepareStatement(sql)){

            statement.setLong(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getImgURL());
            statement.setInt(4, user.getGender());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getEmail());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User get(Long userID) {
        String sql = "SELECT * FROM yamnyk_users WHERE id=?";

        try(PreparedStatement statement = ConnectionToDB.getConnection().prepareStatement(sql)){

        	statement.setLong(1, userID);

			ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {

                return getUserFromResultSet(rSet);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return new User();
    }

    public User getByEmailAndPass (String email, String pass) {
        String sql = "SELECT * FROM yamnyk_users WHERE email=? AND password=?";

        try(PreparedStatement statement = ConnectionToDB.getConnection().prepareStatement(sql)){

        	statement.setString(1, email);
        	statement.setString(2, pass);

			ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                if(rSet.getString("email").equals(email)
                        && rSet.getString("password").equals(pass)) {

                    return getUserFromResultSet(rSet);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return new User();
    }

    public boolean existByEmailAndPass(String email, String pass) {
        String sql = "SELECT * FROM yamnyk_users WHERE email='"+email+"'";
        boolean answ = false;

        try(PreparedStatement statement = ConnectionToDB.getConnection().prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                if(rSet.getString("email").equals(email)
                        && rSet.getString("password").equals(pass)){
                    answ = true;
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return answ;
    }

    public ArrayList<User> getUsersFromLikesList(ArrayList likes, Long myID, LikedDAO likedDAO){
		ArrayList<User> resUsers = new ArrayList<>();

    	for (Like like : likedDAO.getLikesByUserID(myID)) {
			resUsers.add(get(like.getWhom()));
		}

		return resUsers;
	}

    public User getUserFromResultSet(ResultSet rSet) throws SQLException {
        User user = new User();
        user.setId(rSet.getLong("id"));
        user.setName(rSet.getString("name"));
        user.setImgURL(rSet.getString("imgURL"));
        user.setPassword(rSet.getString("password"));
        user.setEmail(rSet.getString("email"));
        user.setGender(rSet.getInt("gender"));

        return user;
    }
}
