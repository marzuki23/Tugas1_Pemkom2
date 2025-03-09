/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package perpustakaan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Perpustakaan extends JFrame {

    private final JTextField txtJudul;
    private final JTextField txtPenulis;
    private final JTextField txtTahun;
    private final JTextField txtJumlah;
    private JTextArea txtArea;
    private final JButton btnTambah;
    private final JButton btnPinjam;
    private final JButton btnHapus;

    private static final String[] KATEGORI = {"Fiksi", "Non-Fiksi", "Teknologi", "Sejarah"};
    private final ArrayList<String> daftarBuku = new ArrayList<>();
    private final ArrayList<Boolean> statusPinjam = new ArrayList<>();

    public Perpustakaan() {
        setTitle("Aplikasi Perpustakaan Modern");
        setSize(650, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 245, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHeader = new JLabel("📘 Sistem Informasi Perpustakaan");
        lblHeader.setFont(new Font("Serif", Font.BOLD, 28));
        lblHeader.setForeground(new Color(50, 90, 160));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblHeader, gbc);

        gbc.gridwidth = 1; // Kembali ke default

        addLabel("Judul Buku:", 1, gbc);
        txtJudul = addTextField(20, 1, gbc);

        addLabel("Penulis:", 2, gbc);
        txtPenulis = addTextField(20, 2, gbc);

        addLabel("Tahun Terbit:", 3, gbc);
        txtTahun = addTextField(5, 3, gbc);

        addLabel("Jumlah Buku:", 4, gbc);
        txtJumlah = addTextField(5, 4, gbc);

        btnTambah = addButton("➕ Tambah Buku", new Color(34, 139, 34), 5, 0, gbc);
        btnPinjam = addButton("📖 Pinjam Buku", new Color(70, 130, 180), 5, 1, gbc);
        btnHapus = addButton("🗑️ Hapus Data", new Color(255, 69, 0), 6, 0, gbc);

        gbc.gridwidth = 2; // Area Output memanjang penuh
        gbc.gridy = 7;
        txtArea = new JTextArea(20, 50);
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtArea.setEditable(false);
        add(new JScrollPane(txtArea), gbc);

        btnTambah.addActionListener(e -> tambahBuku());
        btnPinjam.addActionListener(e -> pinjamBuku());
        btnHapus.addActionListener(e -> txtArea.setText(""));

        pack(); // Menyesuaikan ukuran frame agar sesuai isi
    }

    private void addLabel(String text, int y, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = y;
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(new Color(40, 60, 90));
        add(label, gbc);
    }

    private JTextField addTextField(int size, int y, GridBagConstraints gbc) {
        gbc.gridx = 1; gbc.gridy = y;
        JTextField textField = new JTextField(size);
        add(textField, gbc);
        return textField;
    }

    private JButton addButton(String text, Color color, int y, int x, GridBagConstraints gbc) {
        gbc.gridx = x; gbc.gridy = y;
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(button, gbc);
        return button;
    }

    private void tambahBuku() {
    try {
        String judul = txtJudul.getText().trim();
        String penulis = txtPenulis.getText().trim();
        int tahun = Integer.parseInt(txtTahun.getText().trim());
        int jumlah = Integer.parseInt(txtJumlah.getText().trim());

        // Validasi input kosong
        if (judul.isEmpty() || penulis.isEmpty() || txtTahun.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua kolom!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tentukan kategori buku
        String kategori = tentukanKategori(tahun);
        daftarBuku.add(judul);
        statusPinjam.add(false);

        // Tambahkan output ke txtArea
        String output = String.format("""
                                      Buku Berhasil Ditambahkan:
                                      Judul    : %s
                                      Penulis  : %s
                                      Tahun    : %d
                                      Kategori : %s
                                      Jumlah   : %d eksemplar
                                      \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
                                      """,
            judul, penulis, tahun, kategori, jumlah
        );

        txtArea.append(output);
        System.out.println(output); // Log di konsol (debugging)

        // Bersihkan input setelah sukses
        txtJudul.setText("");
        txtPenulis.setText("");
        txtTahun.setText("");
        txtJumlah.setText("");

        JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Input tidak valid! Pastikan angka diisi dengan benar.", "Error", JOptionPane.ERROR_MESSAGE);
        // Log error di konsol (debugging)
    }
}


    private void pinjamBuku() {
        if (daftarBuku.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada buku tersedia untuk dipinjam.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String judulPinjam = JOptionPane.showInputDialog(this, "Masukkan judul buku yang ingin dipinjam:");
        if (judulPinjam == null || judulPinjam.trim().isEmpty()) return;

        for (int i = 0; i < daftarBuku.size(); i++) {
            if (daftarBuku.get(i).equalsIgnoreCase(judulPinjam) && !statusPinjam.get(i)) {
                statusPinjam.set(i, true);
                txtArea.append("✅ Buku '" + judulPinjam + "' berhasil dipinjam!\n");
                JOptionPane.showMessageDialog(this, "Buku berhasil dipinjam!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Buku tidak tersedia atau sudah dipinjam.", "Gagal", JOptionPane.ERROR_MESSAGE);
    }

    private String tentukanKategori(int tahun) {
        if (tahun >= 2020) return KATEGORI[2];
        if (tahun >= 2000) return KATEGORI[1];
        if (tahun >= 1900) return KATEGORI[3];
        return KATEGORI[0];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Perpustakaan().setVisible(true));
    }
}
