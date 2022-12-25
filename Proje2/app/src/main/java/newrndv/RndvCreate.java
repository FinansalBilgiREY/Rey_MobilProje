package newrndv;


public class RndvCreate {
    private String adSoyad;
    private String aracBilg;
    private String telefon;
    private String plaka;
    private String tarih;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public RndvCreate() {

    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getAracBilg() {
        return aracBilg;
    }

    public void setAracBilg(String aracBilg) {
        this.aracBilg = aracBilg;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

}