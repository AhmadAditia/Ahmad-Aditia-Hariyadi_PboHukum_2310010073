/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hukum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;

// JasperReport
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class CrudArsip {

    Connection conn;
    Statement st;
    ResultSet rs;

    // Koneksi Database
    public CrudArsip() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hukum_2310010073",
                    "root",
                    ""
            );
            System.out.println("Koneksi database BERHASIL");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi GAGAL: " + e.getMessage());
        }
    }

    // Tampil Data Arsip
    public void tampilDataArsip(JTable tabel, String sql) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Arsip");
            model.addColumn("Nama Arsip");
            model.addColumn("Kategori");
            model.addColumn("Petugas");
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
            JOptionPane.showMessageDialog(null, "Gagal tampil data: " + e.getMessage());
        }
    }

    // Tambah Arsip
    public void tambahArsip(String nama, String kategori, int petugas, String tanggal, String file) {
        try {
            String sql = "INSERT INTO arsip VALUES (NULL,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, kategori);
            ps.setInt(3, petugas);
            ps.setString(4, tanggal);
            ps.setString(5, file);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan: " + e.getMessage());
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

            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ubah: " + e.getMessage());
        }
    }

    // Hapus Arsip
    public void hapusArsip(int id) {
        try {
            String sql = "DELETE FROM arsip WHERE id_arsip=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus: " + e.getMessage());
        }
    }

    // Laporan Jasper
    public void tampilLaporan(String laporanFile, String SQL) {
        try {
            File file = new File(laporanFile);
            JasperDesign jasDes = JRXmlLoader.load(file);

            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            jasDes.setQuery(query);

            JasperReport JR = JasperCompileManager.compileReport(jasDes);
            JasperPrint JP = JasperFillManager.fillReport(JR, null, conn);

            JasperViewer.viewReport(JP, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Laporan gagal: " + e.getMessage());
        }
    }
}
