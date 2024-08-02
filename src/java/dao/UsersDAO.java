/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entity.User;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.math.BigInteger;
import util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eren
 */
public class UsersDAO extends DBConnection {

    private Connection db;

    private String kullanici_adi;
    private String sifre;
    
    private String mesaj;

    public void Create(User user) {
        try {
            String insertQuery = "INSERT INTO KULLANICI (kullanici_unvan, kullanici_durum_id, kullanici_kullanici_adi, kullanici_isim, kullanici_adres, kullanici_sicil_no, kullanici_telefon, kullanici_cinsiyet, sifre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            user.setKullanici_durum_id(Integer.parseInt(user.getKullanici_durum()));

            PreparedStatement preparedStatement = this.getDb().prepareStatement(insertQuery);
            preparedStatement.setString(1, user.getKullanici_unvan());
            preparedStatement.setInt(2, user.getKullanici_durum_id());
            preparedStatement.setString(3, user.getKullanici_kullanici_adi());
            preparedStatement.setString(4, user.getKullanici_isim());
            preparedStatement.setString(5, user.getKullanici_adres());
            preparedStatement.setObject(6, user.getKullanici_sicil_no());
            preparedStatement.setObject(7, user.getKullanici_telefon());
            preparedStatement.setString(8, String.valueOf(user.getKullanici_cinsiyet()));
            preparedStatement.setString(9, user.getSifre());

            int r = preparedStatement.executeUpdate();

            this.mesaj = "İşlemler başarıyla gerçekleşmiştir.";
            
        } catch (Exception ex) {
            DetectError(ex);

        }

    }

    public List<User> GetList() {

        List<User> UserList = new ArrayList<>();

        try {

            Statement statement = getDb().createStatement();

            String Selectquery = "SELECT kullanici_id, kullanici_kullanici_adi, kullanici_unvan, D.kullanici_durum, kullanici_isim, kullanici_adres, kullanici_sicil_no,kullanici_telefon,kullanici_cinsiyet,kullanici_kayit_tarih FROM KULLANICI K\n"
                    + "JOIN KULLANICI_DURUM D ON K.kullanici_durum_id = D.kullanici_durum_id\n";

            ResultSet rs = statement.executeQuery(Selectquery);

            while (rs.next()) {
                UserList.add(new User(
                        rs.getInt("kullanici_id"),
                        rs.getString("kullanici_isim"),
                        rs.getString("kullanici_adres"),
                        rs.getBigDecimal("kullanici_sicil_no").toBigInteger(),
                        rs.getBigDecimal("kullanici_telefon").toBigInteger(),
                        rs.getString("kullanici_cinsiyet").charAt(0),
                        rs.getDate("kullanici_kayit_tarih"),
                        rs.getString("kullanici_unvan"),
                        rs.getString("kullanici_durum"),
                        rs.getString("kullanici_kullanici_adi"))
                );
            }

        } catch (Exception ex) {
            DetectError(ex);
        }
        return UserList;
    }

    public void Delete(int kullaniciId) {
        String deleteQuery = "DELETE FROM KULLANICI WHERE kullanici_id = ?";

        try {
            Connection connection = this.getDb(); // assuming getDb() returns a Connection
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, kullaniciId);

            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " kullanıcı silindi.");

             this.mesaj = "İşlemler başarıyla gerçekleşmiştir.";
        } catch (SQLException ex) {
            DetectError(ex);
        }
    }

    public boolean KullaniciGiris(String kullaniciAdi, String sifre) {
        boolean Girisbasarili = false;
        try {
            PreparedStatement preparedStatement = getDb().prepareStatement("SELECT * FROM KULLANICI WHERE KULLANICI_KULLANICI_ADI = ? AND SIFRE = ?");
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, sifre);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Girisbasarili = true;
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            DetectError(ex);
        }
        return Girisbasarili;
    }

    private void DetectError(Exception ex) {
        //Hatayı yakalamak için
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder errorMessage = new StringBuilder(ex.getMessage());
        StackTraceElement[] stackTrace = ex.getStackTrace();

        //Hatanın hangi satırda olduğunu görmek için
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith("dao")) {
                errorMessage.append(" (at ").append(element.getFileName())
                        .append(":").append(element.getLineNumber()).append(")");
                break;
            }
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage.toString(), null));

    }

    public Connection getDb() {
        if (this.db == null) {
            this.db = this.connect();
        }
        return db;
    }

    public void setDb(Connection db) {
        this.db = db;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getMessage() {
        return mesaj;
    }

    public void setMessage(String message) {
        this.mesaj = message;
    }

}
