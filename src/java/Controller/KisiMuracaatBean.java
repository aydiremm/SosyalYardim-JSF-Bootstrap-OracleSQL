/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package Controller;

import static Various.ErrorFinder.DetectError;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;

/**
 *
 * @author Eren
 */
@Named(value = "kisiMuracaatBean")
@SessionScoped
public class KisiMuracaatBean implements Serializable {

    public KisiMuracaatBean() {
    }

    private String mesaj;

    @Inject
    private KisiBean kimlikBean;

    @Inject
    private KisiIletisimBean iletisimBean;

    @Inject
    private KisiAdresBean adresBean;

    @Inject
    private MuracaatBilgiBean muracaatBilgiBean;

    public void KisiMuracaatEkle() {
        try {
            kimlikBean.ekle();
            iletisimBean.ekle();
            adresBean.ekle();
            muracaatBilgiBean.ekle();

            mesaj = muracaatBilgiBean.getDao().getMesaj();

        } catch (Exception ex) {
            mesaj = DetectError(ex);
        }
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

}
