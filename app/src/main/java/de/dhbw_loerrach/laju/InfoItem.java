package de.dhbw_loerrach.laju;

import java.io.Serializable;

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
        int end = 20;
        if(end > text.length()){
            end = text.length();
        }
        return text.substring(0, end);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getErstelldatum() {
        return erstelldatum;
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
