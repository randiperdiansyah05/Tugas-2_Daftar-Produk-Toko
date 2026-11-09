package kelas.controller;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kelas.Navigator;
import kelas.model.Produk;

public class DetailController {
    @FXML private Label kodeLabel, namaLabel, kategoriLabel, hargaLabel;
    @FXML private ImageView gambarView;
    private Navigator navigator;

    public void setData(Navigator navigator, Produk produk) {
        this.navigator = navigator;
        if (produk == null) throw new IllegalArgumentException("Data produk tidak ditemukan.");
        kodeLabel.setText(produk.getKodeProduk());
        namaLabel.setText(produk.getNamaProduk());
        kategoriLabel.setText(produk.getKategori());
        hargaLabel.setText(NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID")).format(produk.getHarga()));
        if (produk.getGambarPath() != null) {
            File file = new File(produk.getGambarPath());
            if (file.exists()) gambarView.setImage(new Image(file.toURI().toString(), 220, 160, true, true));
        } else {
            gambarView.setVisible(false);
            gambarView.setManaged(false);
        }
    }

    @FXML private void kembaliKeDaftar() {
        try { navigator.bukaDaftar(); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
