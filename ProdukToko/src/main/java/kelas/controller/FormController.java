package kelas.controller;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;

public class FormController {
    @FXML private TextField kodeField, namaField, hargaField;
    @FXML private ComboBox<String> kategoriBox;
    @FXML private Label pesanLabel, namaFileLabel;
    @FXML private ImageView previewGambar;
    private Navigator navigator;
    private DataProduk data;
    private File fileGambar;

    @FXML private void initialize() {
        kategoriBox.setItems(FXCollections.observableArrayList("Makanan", "Minuman"));
        kategoriBox.setValue("Makanan");
        kodeField.setEditable(false);
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        kodeField.setText(data.buatKodeBerikutnya());
    }

    @FXML private void pilihGambar() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Pilih Gambar Produk");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Gambar", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File terpilih = chooser.showOpenDialog(previewGambar.getScene().getWindow());
        if (terpilih != null) {
            try {
                Image image = new Image(terpilih.toURI().toString(), 150, 120, true, true);
                if (image.isError()) throw new IllegalArgumentException("File gambar tidak dapat dibaca.");
                fileGambar = terpilih;
                previewGambar.setImage(image);
                namaFileLabel.setText(terpilih.getName());
                pesanLabel.setText("");
            } catch (RuntimeException e) {
                pesanLabel.setText("Gagal memuat gambar: " + e.getMessage());
            }
        }
    }

    @FXML private void simpanProduk() {
        try {
            if (hargaField.getText() == null || hargaField.getText().isBlank()) throw new NumberFormatException();
            double harga = Double.parseDouble(hargaField.getText().trim().replace(',', '.'));
            Produk produk = new Produk(kodeField.getText(), namaField.getText(), kategoriBox.getValue(), harga,
                    fileGambar == null ? null : fileGambar.getAbsolutePath());
            data.tambah(produk);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Berhasil");
            alert.setHeaderText("Produk berhasil ditambahkan");
            alert.setContentText("Produk " + produk.getNamaProduk() + " sudah tersimpan.");
            alert.showAndWait();
            navigator.bukaDaftar();
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus diisi dengan angka dan lebih dari nol.");
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
        } catch (IOException e) {
            pesanLabel.setText("Gagal membuka daftar produk: " + e.getMessage());
        }
    }

    @FXML private void keDaftar() {
        try { navigator.bukaDaftar(); }
        catch (IOException e) { pesanLabel.setText("Gagal membuka daftar: " + e.getMessage()); }
    }
}
