package DataBase;

import java.sql.*;

public class DataBase {
    private Connection c;
    private Statement stmt;
    public DataBase(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:JocPAOO.db");
            stmt=c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS \"date\" (\n" +
                    "\t\"Name\"\tTEXT,\n" +
                    "\t\"HighScore\"\tINTEGER,\n" +
                    "\t\"Gender\"\tTEXT\n" +
                    ")";
            stmt.execute(sql);
            //c.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public void updateDB(String name,String gender,int score) throws SQLException {
        try {
            String sql = "INSERT INTO date (Name,HighScore,Gender) " + "VALUES ('" +name + "'," + score + ",'" + gender + "')";
            stmt=c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void getData() throws SQLException {
        stmt=c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM date;" );
        while ( rs.next() ) {
            String name = rs.getString("name");
            int highscore = rs.getInt("highscore");
            String gender = rs.getString("gender");
            System.out.println( "NAME = " + name );
            System.out.println( "HIGHSCORE = " + highscore );
            System.out.println( "GENDER = " + gender );
            System.out.println();
        }
        rs.close();
        stmt.close();
    }
    public String[] getColumnOne() throws SQLException {
        stmt=c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM date ORDER BY highscore DESC;" );
        String names[]=new String[10];
        int i=0;
        while ( rs.next() && i<10) {
            String name = rs.getString("name");
            names[i]=name;
            i++;
        }
        return  names;
    }
    public int[] getColumnTwo() throws SQLException{
        stmt=c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM date ORDER BY highscore DESC;" );
        int scores[]=new int[11];
        int i=0;
        while ( rs.next() && i<10) {
            int score = rs.getInt("highscore");
            scores[i]=score;
            i++;
        }
        return  scores;
    }
    public String[] getColumnThree() throws SQLException {
        stmt=c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM date ORDER BY highscore DESC;" );
        String names[]=new String[10];
        int i=0;
        while ( rs.next() && i<10) {
            String name = rs.getString("gender");
            names[i]=name;
            i++;
        }
        return  names;
    }
    public void closeDB() throws SQLException {
        c.close();
    }
}
