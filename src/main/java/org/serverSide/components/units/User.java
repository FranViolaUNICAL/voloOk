package org.serverSide.components.units;

import org.serverSide.components.utils.RandomStringGenerator;

import java.util.Objects;

public class User implements Unit{

    private String name;
    private String surname;
    private String email;
    private String password;
    private int fidelityPoints = 0;
    private String luogoDiNascita;
    private String regioneDiNascita;
    private String dataDiNascita;
    private String userId;

    private String lastPurchaseDate;

    public User(){
        super();
    }

    public User(String name, String surname, String email, String password, String luogoDiNascita, String regioneDiNascita, String dataDiNascita) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.luogoDiNascita = luogoDiNascita;
        this.regioneDiNascita = regioneDiNascita;
        this.dataDiNascita = dataDiNascita;
        int IDLENGTH = 7;
        userId = RandomStringGenerator.generateRandomString(IDLENGTH);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }
    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }
    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }
    public String getRegioneDiNascita() {
        return regioneDiNascita;
    }
    public void setRegioneDiNascita(String regioneDiNascita) {
        this.regioneDiNascita = regioneDiNascita;
    }
    public String getDataDiNascita() {
        return dataDiNascita;
    }
    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getLastPurchaseDate() {
        return lastPurchaseDate;
    }
    public void setLastPurchaseDate(String lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public String toString() {
        return email + " " + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    public String getUserId(){
        return userId;
    }


}
