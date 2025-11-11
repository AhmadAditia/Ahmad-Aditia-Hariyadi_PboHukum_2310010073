/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hukum;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CrudRiwayat {
    Connection conn;
    Statement st;
    ResultSet rs;

    public CrudRiwayat() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/hukum_2310010073", "root", "");
            System.out.println("Koneksi ke database hukum_2310010073 berhasil!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    public void tampilDataRiwayat(JTable tabel, String sql) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Riwayat");
            model.addColumn("Waktu");
            model.addColumn("Arsip");
            model.addColumn("User");

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("riwayat_id"),
                    rs.getTimestamp("waktu"),
                    rs.getInt("arsip"),
                    rs.getString("user")
                });
            }
            tabel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    public void tambahRiwayat(String waktu, int arsip, String user) {
        try {
            String sql = "INSERT INTO riwayat (waktu, arsip, user) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, waktu);
            ps.setInt(2, arsip);
            ps.setString(3, user);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data riwayat berhasil disimpan!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    public void ubahRiwayat(int id, String waktu, int arsip, String user) {
        try {
            String sql = "UPDATE riwayat SET waktu=?, arsip=?, user=? WHERE riwayat_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, waktu);
            ps.setInt(2, arsip);
            ps.setString(3, user);
            ps.setInt(4, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data riwayat berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
    }

    public void hapusRiwayat(int id) {
        try {
            String sql = "DELETE FROM riwayat WHERE riwayat_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data riwayat berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
        }
    }
}

