package hukum;

// ===== IMPORT JDBC =====
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// ===== IMPORT SWING =====
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// ===== IMPORT FILE =====
import java.io.File;

// ===== IMPORT JASPERREPORT =====
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class CrudRiwayat {

    Connection conn;
    Statement st;
    ResultSet rs;

    // =============================
    // KONSTRUKTOR / KONEKSI DB
    // =============================
    public CrudRiwayat() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hukum_2310010073",
                "root",
                ""
            );
            System.out.println("Koneksi database BERHASIL (Riwayat)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    // =============================
    // TAMPIL DATA RIWAYAT
    // =============================
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

    // =============================
    // TAMBAH RIWAYAT
    // =============================
    public void tambahRiwayat(String waktu, int arsip, String user) {
        try {
            String sql = "INSERT INTO riwayat (waktu, arsip, user) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, waktu);
            ps.setInt(2, arsip);
            ps.setString(3, user);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data riwayat berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan: " + e.getMessage());
        }
    }

    // =============================
    // UBAH RIWAYAT
    // =============================
    public void ubahRiwayat(int id, String waktu, int arsip, String user) {
        try {
            String sql = "UPDATE riwayat SET waktu=?, arsip=?, user=? WHERE riwayat_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, waktu);
            ps.setInt(2, arsip);
            ps.setString(3, user);
            ps.setInt(4, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data riwayat berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ubah: " + e.getMessage());
        }
    }

    // =============================
    // HAPUS RIWAYAT
    // =============================
    public void hapusRiwayat(int id) {
        try {
            String sql = "DELETE FROM riwayat WHERE riwayat_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data riwayat berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus: " + e.getMessage());
        }
    }

    // =============================
    // TAMPIL LAPORAN JASPER
    // =============================
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
