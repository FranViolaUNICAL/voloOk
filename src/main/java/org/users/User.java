package org.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String email;
    private String password;
    private int fidelityPoints = 0;
    private String luogoDiNascita;
    private String regioneDiNascita;
    private String dataDiNascita;

    public User(){
        super();
    }

    public User(String email, String password, String luogoDiNascita, String regioneDiNascita, String dataDiNascita) {
        this.email = email;
        this.password = password;
        this.luogoDiNascita = luogoDiNascita;
        this.regioneDiNascita = regioneDiNascita;
        this.dataDiNascita = dataDiNascita;
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


}
