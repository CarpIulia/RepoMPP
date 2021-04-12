package domain;

import java.io.Serializable;

public class Casier extends Entity<Integer> implements Serializable {
    private String numeUtilizator;
    private String parola;

    public Casier(String numeUtilizator, String parola) {
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Casier{" +
                "numeUtilizator='" + numeUtilizator + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
