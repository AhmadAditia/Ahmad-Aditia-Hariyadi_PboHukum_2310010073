/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hukum;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CrudKategori {
    Connection conn;
    Statement st;
    ResultSet rs;

    public CrudKategori() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/hukum_2310010073", "root", "");
            System.out.println("Koneksi ke database hukum_2310010073 berhasil!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    public void tampilDataKategori(JTable tabel, String sql) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nama Kategori");
            model.addColumn("Keterangan");

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("kategori_id"),
                    rs.getString("nama_kategori"),
                    rs.getString("keterangan")
                });
            }
            tabel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    public void tambahKategori(String nama, String ket) {
        try {
            String sql = "INSERT INTO kategori (nama_kategori, keterangan) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, ket);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data kategori berhasil disimpan!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    public void ubahKategori(int id, String nama, String ket) {
        try {
            String sql = "UPDATE kategori SET nama_kategori=?, keterangan=? WHERE kategori_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, ket);
            ps.setInt(3, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data kategori berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
    }

    public void hapusKategori(int id) {
        try {
            String sql = "DELETE FROM kategori WHERE kategori_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data kategori berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
        }
    }
}
