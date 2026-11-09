package kelas;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kelas.data.DataProduk;
import kelas.model.Produk;
import kelas.controller.FormController;
import kelas.controller.DaftarController;
import kelas.controller.DetailController;

public class Navigator {
    private final Stage stage;
    private final DataProduk data;

    public Navigator(Stage stage, DataProduk data) {
        this.stage = stage;
        this.data = data;
    }

    private FXMLLoader loader(String nama) {
        return new FXMLLoader(getClass().getResource("/kelas/" + nama + ".fxml"));
    }

    private void tampil(Parent root, String judul) {
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(getClass().getResource("/kelas/styles/app.css").toExternalForm());
        stage.setTitle(judul);
        stage.setMinWidth(760);
        stage.setMinHeight(560);
        stage.setScene(scene);
        stage.show();
    }

    public void bukaForm() throws IOException {
        FXMLLoader l = loader("formProduk");
        Parent root = l.load();
        FormController c = l.getController();
        c.setKonteks(this, data);
        tampil(root, "Tambah Produk - Daftar Produk Toko");
    }

    public void bukaDaftar() throws IOException {
        FXMLLoader l = loader("daftar");
        Parent root = l.load();
        DaftarController c = l.getController();
        c.setKonteks(this, data);
        tampil(root, "Daftar Produk - Daftar Produk Toko");
    }

    public void bukaDetail(Produk produk) throws IOException {
        FXMLLoader l = loader("detail");
        Parent root = l.load();
        DetailController c = l.getController();
        c.setData(this, produk);
        tampil(root, "Detail Produk - Daftar Produk Toko");
    }
}
