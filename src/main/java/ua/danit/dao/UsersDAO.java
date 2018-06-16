package ua.danit.dao;


import ua.danit.model.UserDemo;
import ua.danit.model.Yamnyk_users;
import ua.danit.utils.GeneratorID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends AbstractDAO<Yamnyk_users>{
    @Override
    public void save(Yamnyk_users user) {
        String sql = "INSERT INTO yamnyk_users(id, name, imgURL) VALUES(?,?,?)";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getImgURL());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Yamnyk_users user) {
        String sql = "UPDATE yamnyk_users SET name=?, imgURL=? WHERE id=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, user.getName());
            statement.setString(2, user.getImgURL());
            statement.setLong(3, user.getId());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Yamnyk_users get(Long pk) {
        Yamnyk_users user = new Yamnyk_users();

        String sql = "SELECT * FROM yamnyk_users WHERE id='"+pk+"'";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            user.setId(rSet.getLong("id"));
            user.setName(rSet.getString("name"));
            user.setImgURL(rSet.getString("imgURL"));

            return user;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Yamnyk_users> getAll() {
        ArrayList<Yamnyk_users> users = new ArrayList<>();
        Yamnyk_users user = new Yamnyk_users();

        String sql = "SELECT * FROM yamnyk_users";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                user.setId(rSet.getLong("id"));
                user.setName(rSet.getString("name"));
                user.setImgURL(rSet.getString("imgURL"));

                users.add(user);
            }

            return users;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long pk) {
        String sql = "DELETE FROM yamnyk_users WHERE id=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, pk);

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
