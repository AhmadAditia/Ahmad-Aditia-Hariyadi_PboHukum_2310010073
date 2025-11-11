/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hukum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private Connection conn;
    
    // ✅ Sesuaikan konfigurasi MySQL kamu
    private final String url = "jdbc:mysql://localhost:3306/hukum_2310010073?useSSL=false&serverTimezone=UTC";
    private final String user = "root";
    private final String pass = "";

    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Koneksi ke database berhasil!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "❌ Koneksi Gagal: " + e.getMessage());
            }
        }
        return conn;
    }
}
