package Controller;

import Entity.Kisi;
import dao.KisiDAO;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named(value = "kisiBean")
@SessionScoped
public class KisiBean implements Serializable {
    
    private Kisi entity;
    private KisiDAO dao;
    private List<Kisi> list;
    
    public void create() {
        
        this.getDao().Create(getEntity());
    }
    
    public void delete(int KisiID) {
        this.getDao().Delete(KisiID);
        this.list = this.getDao().GetList(); // Silme işleminden sonra listeyi güncelle
    }
    
public void sorgula() {
    Kisi kisi = new Kisi(); // Yeni bir Kisi nesnesi oluştur
    kisi.setKimlik_no(this.entity.getKimlik_no()); // Kimlik numarasını mevcut entity'den al
    
    this.getDao().Sorgula(kisi); // Kisi nesnesini Sorgula metoduna geçir
    
    this.entity = kisi; // Doldurulan Kisi nesnesini entity değişkenine ata
}

    
    public void edit(Kisi kisi) {
        this.entity = kisi;
    }
    
    public Kisi getEntity() {
        if (this.entity == null) {
            this.entity = new Kisi();
        }
        return this.entity;
    }
    
    public void setEntity(Kisi entity) {
        this.entity = entity;
    }
    
    public KisiDAO getDao() {
        if (this.dao == null) {
            this.dao = new KisiDAO();
        }
        return this.dao;
    }
    
    public void setDao(KisiDAO dao) {
        this.dao = dao;
    }
    
    public List<Kisi> getList() {
        if (this.list == null) {
            this.list = this.getDao().GetList();
        }
        return this.list;
    }
    
    public void setList(List<Kisi> list) {
        this.list = list;
    }
}
