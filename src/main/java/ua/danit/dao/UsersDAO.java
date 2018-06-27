package ua.danit.dao;


import ua.danit.model.Yamnyk_users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

        String sql = "SELECT * FROM yamnyk_users WHERE id='"+pk+"'";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                Yamnyk_users user = new Yamnyk_users();
                user.setId(rSet.getLong("id"));
                user.setName(rSet.getString("name"));
                user.setImgURL(rSet.getString("imgURL"));
                user.setGender(rSet.getInt("gender"));

                return user;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Yamnyk_users getByEmailAndPass (String email, String pass) {

        String sql = "SELECT * FROM yamnyk_users WHERE email='"+email+"'";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                if(rSet.getString("email").equals(email)
                        && rSet.getString("password").equals(pass)) {

                    Yamnyk_users user = new Yamnyk_users();
                    user.setId(rSet.getLong("id"));
                    user.setName(rSet.getString("name"));
                    user.setImgURL(rSet.getString("imgURL"));
                    user.setEmail(email);
                    user.setPassword(pass);
                    return user;
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean existByEmailAndPass(String email, String pass) {

        String sql = "SELECT * FROM yamnyk_users WHERE email='"+email+"'";
        boolean answ = false;
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
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

    public ArrayList<Yamnyk_users> getAll() {
        ArrayList<Yamnyk_users> users = new ArrayList<>();

        String sql = "SELECT * FROM yamnyk_users";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                Yamnyk_users user = new Yamnyk_users();
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
