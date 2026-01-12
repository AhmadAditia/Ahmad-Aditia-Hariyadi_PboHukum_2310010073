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

public class CrudPetugas {

    Connection conn;
    Statement st;
    ResultSet rs;

    // =============================
    // KONSTRUKTOR / KONEKSI DB
    // =============================
    public CrudPetugas() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hukum_2310010073",
                "root",
                ""
            );
            System.out.println("Koneksi database BERHASIL (Petugas)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    // =============================
    // TAMBAH PETUGAS
    // =============================
    public void tambahPetugas(String nama, String username, String password, String foto) {
        try {
            String sql = "INSERT INTO petugas (nama, username, password, foto) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, foto);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data petugas berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan: " + e.getMessage());
        }
    }

    // =============================
    // UBAH PETUGAS
    // =============================
    public void ubahPetugas(int id, String nama, String username, String password, String foto) {
        try {
            String sql = "UPDATE petugas SET nama=?, username=?, password=?, foto=? WHERE petugas_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, foto);
            ps.setInt(5, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data petugas berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ubah: " + e.getMessage());
        }
    }

    // =============================
    // HAPUS PETUGAS
    // =============================
    public void hapusPetugas(int id) {
        try {
            String sql = "DELETE FROM petugas WHERE petugas_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data petugas berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus: " + e.getMessage());
        }
    }

    // =============================
    // TAMPIL DATA KE JTABLE
    // =============================
    public void tampilPetugas(JTable tabel, String sql) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nama");
            model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Foto");

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("petugas_id"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("foto")
                });
            }

            tabel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal tampil data: " + e.getMessage());
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
