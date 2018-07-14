package ua.danit.dao;

import ua.danit.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessagesDAO {

    public void save(Message msg) {
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

    public void update(Message msg) {
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

    public ArrayList<Message> getByFromTo(Long whoID, Long whomID) {
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM yamnyk_messages " +
                "WHERE sender =? AND recipient=? UNION SELECT * FROM yamnyk_messages " +
                "WHERE sender=? AND recipient=? ORDER BY message_time";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

        	statement.setLong(1, whoID);
        	statement.setLong(2, whomID);
        	statement.setLong(3, whomID);
        	statement.setLong(4, whoID);

			ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                messages.add(getMessageFromResultSet(rSet));
            }

            return messages;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

	public void delete(Long messageID) {
        String sql = "DELETE FROM yamnyk_messages WHERE message_id=?";

        try(Connection connection = new ConnectionToDB().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, messageID);

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

	private Message getMessageFromResultSet(ResultSet rSet) throws SQLException {
		Message msg = new Message();
		msg.setMessageTime(rSet.getTimestamp("message_time"));
		msg.setSender(rSet.getLong("sender"));
		msg.setRecipient(rSet.getLong("recipient"));
		msg.setText(rSet.getString("text"));

		return msg;
	}
}
