package org.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.components.utils.RandomStringGenerator;

import java.util.Objects;

public class User {
    private final int IDLENGTH = 7;

    private String name;
    private String surname;
    private String email;
    private String password;
    private int fidelityPoints = 0;
    private String luogoDiNascita;
    private String regioneDiNascita;
    private String dataDiNascita;
    private String userId;

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
        userId = RandomStringGenerator.generateRandomString(IDLENGTH);
    }
    @JsonProperty
    public String getName() {
        return name;
    }
    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty
    public String getSurname() {
        return surname;
    }
    @JsonProperty
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public int getFidelityPoints() {
        return fidelityPoints;
    }

    @JsonProperty
    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }
    @JsonProperty
    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }
    @JsonProperty
    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }
    @JsonProperty
    public String getRegioneDiNascita() {
        return regioneDiNascita;
    }
    @JsonProperty
    public void setRegioneDiNascita(String regioneDiNascita) {
        this.regioneDiNascita = regioneDiNascita;
    }
    @JsonProperty
    public String getDataDiNascita() {
        return dataDiNascita;
    }
    @JsonProperty
    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
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
