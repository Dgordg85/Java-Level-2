package Lesson_6.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getNickByLoginPass(String login, String pass){
        String sql = String.format("SELECT nickname FROM main WHERE login = '%s' and password = '%s'", login, pass);

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void addUserToBlacklist(ClientHandler user, String blockUser){
        user.getBlacklist().add(blockUser);

        String sql = String.format("INSERT INTO blacklist(user_id, user_block) VALUES (%d, %d)", getUserIdByNick(user.getNick()), getUserIdByNick(blockUser));

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void fillBlackList(ClientHandler user){
        String sql = String.format("SELECT distinct user_block FROM main LEFT JOIN blacklist ON main.id = blacklist.user_id WHERE main.id = '%d'", getUserIdByNick(user.getNick()));
        try {
            ResultSet rs = stmt.executeQuery(sql);
            List<Integer> list = new ArrayList<>();
            //Вот тут не сработала строчка - почему?
            // user.getBlacklist().add(getNickById(rs.getInt(1)));
            while (rs.next()){
                list.add(rs.getInt(1));
            }

            for (Integer i : list){
                user.getBlacklist().add(getNickById(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickById(int id){
        String sql = String.format("SELECT nickname FROM main WHERE id = '%d';", id);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getUserIdByNick(String nick){
        String sql = String.format("SELECT id FROM main WHERE nickname='%s'", nick);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }




    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
