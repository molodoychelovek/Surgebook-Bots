import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class UsersData {
    private Properties pr = new Properties();
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public UsersData(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            pr.setProperty("user", ""); // Логин phpmyadmin
            pr.setProperty("password", ""); // Пароль
            pr.setProperty("useUnicode","true");
            pr.setProperty("useJDBCCompliantTimezoneShift", "true");
            pr.setProperty("useLegacyDatetimeCode", "false");
            pr.setProperty("serverTimezone", "UTC");
            pr.setProperty("characterEncoding","cp1251");
            pr.setProperty("useSSL","false");
            pr.setProperty("autoReconnect","true");
            // Базовые опции для коректной работы с БД
            con = DriverManager.getConnection("jdbc:mysql://localhost/bot",pr);
            // Если вы используете сервер. Тогда вместо localhost - ip дроплета

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String chat_id, String login, String pass){
        //Добавляем нового пользователя
        String sql = "INSERT INTO users(chat_id, login, pass) VALUES(?,?,?)";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, chat_id);
            st.setString(2, login);
            st.setString(3, pass);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        finally {
            closeDB();
        }
    }

    public ArrayList<String> getUser(String chat_id){
        //Берем данные пользователя по чат ид
        ArrayList <String> arrayList = new ArrayList<String>();

        try {
            String query = "select * FROM users WHERE chat_id = ?";
            st = con.prepareStatement(query);
            st.setString(1, chat_id);
            rs = st.executeQuery();

            while (rs.next()){
                arrayList.add(rs.getString("login"));
                arrayList.add(rs.getString("pass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeDB();
        }
        return arrayList;
    }

    public void closeDB(){
        try {
            // Объязательно закрываем. После 8 часов работы если БД не закрыта - она упадет.
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) { }
    }
}
