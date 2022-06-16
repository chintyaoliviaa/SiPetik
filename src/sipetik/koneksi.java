/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipetik;

/**
 *
 * @author Chynthia
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class koneksi {
    Connection koneksi;
    PreparedStatement ps;
    ResultSet rs;
    Statement s;
    String query;
    
    void setConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            System.out.println("Gagal Koneksi "+ ex);
        }
        
        //Koneksi ke DB
        try{
            String url = "jdbc:mysql://localhost:3306/sipetik";
            koneksi = (Connection)DriverManager.getConnection(url, "root","");
        }catch(SQLException exsql){
            System.out.println("Gagal koneksi ke database "+exsql);
        }
    }
    
    public void getData(){
        try{
            setConnect();
            s = koneksi.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = s.executeQuery(query);
        }catch(SQLException s){
            System.out.println("Error: "+s);
        }
    }
    
    public void setData(){
        try{
            setConnect();
            ps = koneksi.prepareStatement(query);
        }catch(SQLException ss){
            System.out.println("Set data error: "+ss);
        }
    }
    
    public Connection conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            System.out.println("Gagal Koneksi "+ ex);
        }
        
        //Koneksi ke DB
        try{
            String url = "jdbc:mysql://localhost:3306/sipetik";
            koneksi = (Connection)DriverManager.getConnection(url, "root","");
        }catch(SQLException exsql){
            System.out.println("Gagal koneksi ke database "+exsql);
        }
        return koneksi;
    }

    Connection getConnection() {
        return null;
    }
}
