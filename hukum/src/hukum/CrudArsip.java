/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hukum;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CrudArsip {
    Connection conn;
    Statement st;
    ResultSet rs;

    public CrudArsip() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/hukum_2310010073", "root", "");
            System.out.println("Koneksi ke database hukum_2310010073 berhasil!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    // Tampil Data Arsip
    public void tampilDataArsip(JTable tabel, String sql) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Arsip");
            model.addColumn("Nama Arsip");
            model.addColumn("Kategori");
            model.addColumn("Petugas (ID)");
            model.addColumn("Tanggal Upload");
            model.addColumn("File Path");

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_arsip"),
                    rs.getString("nama_arsip"),
                    rs.getString("kategori"),
                    rs.getInt("petugas"),
                    rs.getString("tanggal_upload"),
                    rs.getString("file_path")
                });
            }

            tabel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    // Tambah Arsip
    public void tambahArsip(String nama, String kategori, int petugas, String tanggal, String file) {
        try {
            String sql = "INSERT INTO arsip (nama_arsip, kategori, petugas, tanggal_upload, file_path) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, kategori);
            ps.setInt(3, petugas);
            ps.setString(4, tanggal);
            ps.setString(5, file);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data arsip berhasil disimpan!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    // Ubah Arsip
    public void ubahArsip(int id, String nama, String kategori, int petugas, String tanggal, String file) {
        try {
            String sql = "UPDATE arsip SET nama_arsip=?, kategori=?, petugas=?, tanggal_upload=?, file_path=? WHERE id_arsip=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, kategori);
            ps.setInt(3, petugas);
            ps.setString(4, tanggal);
            ps.setString(5, file);
            ps.setInt(6, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data arsip berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
    }

    // Hapus Arsip
    public void hapusArsip(int id) {
        try {
            String sql = "DELETE FROM arsip WHERE id_arsip=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data arsip berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
        }
    }
}
