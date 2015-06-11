package de.dhbw_loerrach.laju;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Frederik on 18.05.2015.
 */
public class InfoItem implements Serializable {
    private String titel, autor, text, erstelldatum, textpreview;

    public InfoItem(String titel, String autor, String erstelldatum, String text) {
        this.titel = titel;
        this.autor = autor;
        this.erstelldatum = erstelldatum;
        this.text = text;
        this.textpreview = getTextPreview(text);
    }

    private String getTextPreview(String text) {
        int end = 60;
        if(end > text.length()){
            end = text.length();
        }
        return text.substring(0, end).replace("\n", " ");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getErstelldatum() {
        String datum = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        try {
            Date date = sdf.parse(erstelldatum);
            sdf.applyLocalizedPattern("dd.MM.yyyy");
            datum = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datum;
    }

    public void setErstelldatum(String erstelldatum) {
        this.erstelldatum = erstelldatum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextpreview() {
        return textpreview;
    }

    public void setTextpreview(String textpreview) {
        this.textpreview = textpreview;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
