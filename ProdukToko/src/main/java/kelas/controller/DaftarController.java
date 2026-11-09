package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;

public class DaftarController {
    @FXML private TextField cariField;
    @FXML private TableView<Produk> tabel;
    @FXML private TableColumn<Produk, String> kodeCol, namaCol, kategoriCol;
    @FXML private TableColumn<Produk, Double> hargaCol;
    @FXML private Label pesanLabel;
    private Navigator navigator;
    private DataProduk data;
    private final ObservableList<Produk> dataTampilan = FXCollections.observableArrayList();

    @FXML private void initialize() {
        tabel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        kodeCol.setCellValueFactory(new PropertyValueFactory<>("kodeProduk"));
        namaCol.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));
        hargaCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Double harga, boolean empty) {
                super.updateItem(harga, empty);
                setText(empty || harga == null ? null : NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID")).format(harga));
            }
        });
        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada produk. Silakan tambahkan produk terlebih dahulu."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML private void cari() {
        if (data == null) return;
        dataTampilan.clear();
        String kata = cariField.getText() == null ? "" : cariField.getText().trim().toLowerCase(Locale.ROOT);
        for (Produk p : data.getDaftar()) {
            if (p.getNamaProduk().toLowerCase(Locale.ROOT).contains(kata)) dataTampilan.add(p);
        }
        pesanLabel.setText("Jumlah produk yang ditampilkan: " + dataTampilan.size());
    }

    @FXML private void resetCari() { cariField.clear(); cari(); }

    @FXML private void tambahProduk() {
        try { navigator.bukaForm(); }
        catch (IOException e) { tampilError("Gagal membuka form tambah produk", e); }
    }

    @FXML private void detail() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();
        if (produk == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Produk belum dipilih");
            alert.setHeaderText("Pilih produk terlebih dahulu");
            alert.setContentText("Klik salah satu baris pada tabel, kemudian tekan Lihat Detail.");
            alert.showAndWait();
            return;
        }
        try { navigator.bukaDetail(produk); }
        catch (IOException | RuntimeException e) { tampilError("Halaman detail tidak dapat dibuka", e); }
    }

    private void tampilError(String judul, Exception e) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Terjadi Kesalahan");
        a.setHeaderText(judul);
        a.setContentText(e.getMessage());
        a.showAndWait();
    }
}
