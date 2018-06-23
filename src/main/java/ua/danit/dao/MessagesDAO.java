package ua.danit.dao;

import ua.danit.model.Yamnyk_liked;
import ua.danit.model.Yamnyk_messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessagesDAO extends AbstractDAO<Yamnyk_messages> {

    @Override
    public void save(Yamnyk_messages msg) {
        String sql = "INSERT INTO yamnyk_messages(sender, recipient, text, message_time) VALUES(?,?,?,?)";
        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, msg.getSender());
            statement.setLong(2, msg.getRecipient());
            statement.setString(3, msg.getText());
            statement.setTimestamp(4, msg.getMessageTime());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Yamnyk_messages msg) {
        String sql = "UPDATE yamnyk_messages SET text=? WHERE message_id = ?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, msg.getText());
            statement.setLong(2, msg.getMessageId());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Yamnyk_messages get(Long pk) {
        return null;
    }

    public ArrayList<Yamnyk_messages> getByFromTo(Long whoID, Long whomID) {
        ArrayList<Yamnyk_messages> messages = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_messages WHERE recipient IN("+whomID+","+whoID+") ORDER BY message_time";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rSet = statement.executeQuery()){

            while(rSet.next()) {
                Yamnyk_messages msg = new Yamnyk_messages();
                msg.setMessageTime(rSet.getTimestamp("message_time"));
                msg.setSender(rSet.getLong("sender"));
                msg.setRecipient(rSet.getLong("recipient"));
                msg.setText(rSet.getString("text"));

                messages.add(msg);
            }
            return messages;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long senderID) {
        String sql = "DELETE FROM yamnyk_messages WHERE sender=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, senderID);

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
