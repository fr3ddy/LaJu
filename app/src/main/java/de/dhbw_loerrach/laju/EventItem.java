package de.dhbw_loerrach.laju;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Frederik on 18.05.2015.
 */
public class EventItem implements Serializable {
    private String datum_von,datum_bis,titel,untertitel,beschreibung,url,bild;

    public EventItem(String beschreibung, String bild, String datum_bis, String datum_von, String titel, String untertitel, String url) {
        this.beschreibung = beschreibung;
        this.bild = bild;
        this.datum_bis = datum_bis;
        this.datum_von = datum_von;
        this.titel = titel;
        this.untertitel = untertitel;
        this.url = url;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public String getDatum_bis() {
        String datum = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        try {
            Date date = sdf.parse(datum_bis);
            sdf.applyLocalizedPattern("dd.MM.yyyy");
            datum = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datum;
    }

    public void setDatum_bis(String datum_bis) {
        this.datum_bis = datum_bis;
    }

    public String getDatum_von() {
        String datum = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        try {
            Date date = sdf.parse(datum_von);
            sdf.applyLocalizedPattern("dd.MM.yyyy");
            datum = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datum;
    }

    public void setDatum_von(String datum_von) {
        this.datum_von = datum_von;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public void setUntertitel(String untertitel) {
        this.untertitel = untertitel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
