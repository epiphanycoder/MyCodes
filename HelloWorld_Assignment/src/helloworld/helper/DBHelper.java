package helloworld.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DBHelper extends DataHelper {
    
    private String userName = "root";
    private  String password = "success";
    Connection connection = null;
    public DBHelper() {
       
    }

    @Override
    public void run(String filePath, HashMap<String, String> messages) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hello_world",userName,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from animal");
            
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                String value = resultSet.getString("description");
                System.out.println("(" + name +","+ value +")");
                if(!messages.containsKey(name)) {
                    messages.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        DBHelper dbHelper = new DBHelper();
        dbHelper.run(null, null);
    }
}
