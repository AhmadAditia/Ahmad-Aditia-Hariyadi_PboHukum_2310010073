/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hukum;

import form.FormPetugas;
import form.FormKategori;
import form.FormArsip;
import form.FormRiwayat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hukum extends JFrame {

    public Hukum() {
        setTitle("📁 Sistem Arsip Hukum");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Tengah layar
        setLayout(new GridLayout(5, 1, 10, 10)); // 5 baris, 1 kolom

        JLabel judul = new JLabel("MENU UTAMA", SwingConstants.CENTER);
        judul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(judul);

        JButton btnPetugas = new JButton("👮 Form Petugas");
        JButton btnKategori = new JButton("📂 Form Kategori");
        JButton btnArsip = new JButton("🗄️ Form Arsip");
        JButton btnRiwayat = new JButton("📜 Form Riwayat");
        JButton btnKeluar = new JButton("❌ Keluar");

        // Tambah tombol ke frame
        add(btnPetugas);
        add(btnKategori);
        add(btnArsip);
        add(btnRiwayat);
        add(btnKeluar);

        // Aksi tombol
        btnPetugas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FormPetugas().setVisible(true);
            }
        });

        btnKategori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FormKategori().setVisible(true);
            }
        });

        btnArsip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FormArsip().setVisible(true);
            }
        });

        btnRiwayat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FormRiwayat().setVisible(true);
            }
        });

        btnKeluar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Hukum().setVisible(true));
    }
}


