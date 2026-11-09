package kelas.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.model.Produk;

public class DataProduk {
    private final ObservableList<Produk> daftar = FXCollections.observableArrayList();

    public ObservableList<Produk> getDaftar() { return FXCollections.unmodifiableObservableList(daftar); }

    public void tambah(Produk produk) {
        for (Produk p : daftar) {
            if (p.getKodeProduk().equalsIgnoreCase(produk.getKodeProduk())) throw new IllegalArgumentException("Kode produk sudah digunakan.");
        }
        daftar.add(produk);
    }

    // Membuat kode berikutnya dengan format P001, P002, dan seterusnya.
    public String buatKodeBerikutnya() {
        int terbesar = 0;
        for (Produk p : daftar) {
            String kode = p.getKodeProduk().trim().toUpperCase();
            if (kode.matches("P\\d+")) {
                try { terbesar = Math.max(terbesar, Integer.parseInt(kode.substring(1))); }
                catch (NumberFormatException ignored) { }
            }
        }
        return String.format("P%03d", terbesar + 1);
    }
}
