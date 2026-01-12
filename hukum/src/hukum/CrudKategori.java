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

public class CrudKategori {

    Connection conn;
    Statement st;
    ResultSet rs;

    // Koneksi Database
    public CrudKategori() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hukum_2310010073",
                    "root",
                    ""
            );
            System.out.println("Koneksi database BERHASIL (Kategori)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    // Tampil Data ke JTable
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

    // Tambah Kategori
    public void tambahKategori(String nama, String ket) {
        try {
            String sql = "INSERT INTO kategori (nama_kategori, keterangan) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, ket);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data kategori berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan: " + e.getMessage());
        }
    }

    // Ubah Kategori
    public void ubahKategori(int id, String nama, String ket) {
        try {
            String sql = "UPDATE kategori SET nama_kategori=?, keterangan=? WHERE kategori_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, ket);
            ps.setInt(3, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data kategori berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ubah: " + e.getMessage());
        }
    }

    // Hapus Kategori
    public void hapusKategori(int id) {
        try {
            String sql = "DELETE FROM kategori WHERE kategori_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data kategori berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus: " + e.getMessage());
        }
    }

    // TAMPIL LAPORAN JASPER

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
