package hukum;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CrudPetugas {
    Connection conn;

    public CrudPetugas() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hukum_2310010073?useSSL=false&serverTimezone=UTC",
                "root", ""
            );
            System.out.println("✅ Koneksi ke database berhasil");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }

    public void tambahPetugas(String nama, String username, String password, String foto) {
        try {
            String sql = "INSERT INTO petugas(nama, username, password, foto) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nama);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, foto);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data petugas berhasil disimpan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan data: " + e.getMessage());
        }
    }

    public void ubahPetugas(int id, String nama, String username, String password, String foto) {
        try {
            String sql = "UPDATE petugas SET nama=?, username=?, password=?, foto=? WHERE petugas_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nama);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, foto);
            pst.setInt(5, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal ubah data: " + e.getMessage());
        }
    }

    public void hapusPetugas(int id) {
        try {
            String sql = "DELETE FROM petugas WHERE petugas_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus data: " + e.getMessage());
        }
    }

    public void tampilPetugas(JTable table, String sql) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nama");
            model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Foto");

            while (rs.next()) {
                Object[] data = {
                    rs.getInt("petugas_id"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("foto")
                };
                model.addRow(data);
            }
            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal tampil data: " + e.getMessage());
        }
    }
}
