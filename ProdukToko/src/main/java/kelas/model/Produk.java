package kelas.model;

public class Produk {
    private final String kodeProduk;
    private final String namaProduk;
    private final String kategori;
    private final double harga;
    private final String gambarPath;

    public Produk(String kodeProduk, String namaProduk, String kategori, double harga) {
        this(kodeProduk, namaProduk, kategori, harga, null);
    }

    public Produk(String kodeProduk, String namaProduk, String kategori, double harga, String gambarPath) {
        if (kodeProduk == null || kodeProduk.isBlank()) throw new IllegalArgumentException("Kode produk wajib diisi.");
        if (namaProduk == null || namaProduk.isBlank()) throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
        if (!"Makanan".equals(kategori) && !"Minuman".equals(kategori)) throw new IllegalArgumentException("Pilih kategori Makanan atau Minuman.");
        if (harga <= 0 || Double.isNaN(harga) || Double.isInfinite(harga)) throw new IllegalArgumentException("Harga harus lebih dari nol.");
        this.kodeProduk = kodeProduk.trim();
        this.namaProduk = namaProduk.trim();
        this.kategori = kategori;
        this.harga = harga;
        this.gambarPath = (gambarPath == null || gambarPath.isBlank()) ? null : gambarPath;
    }

    public String getKodeProduk() { return kodeProduk; }
    public String getNamaProduk() { return namaProduk; }
    public String getKategori() { return kategori; }
    public double getHarga() { return harga; }
    public String getGambarPath() { return gambarPath; }
}
