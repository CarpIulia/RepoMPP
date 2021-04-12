package domain;

import java.io.Serializable;

public class Bilet extends Entity<Integer> implements Serializable {
    private Integer meci;
    private String numeClient;
    private Integer nrLocuriCumparate;

    public Bilet(Integer meci, String numeClient, Integer nrLocuriCumparate) {
        this.meci = meci;
        this.numeClient = numeClient;
        this.nrLocuriCumparate = nrLocuriCumparate;
    }

    public Integer getMeci() {
        return meci;
    }

    public void setMeci(Integer meci) {
        this.meci = meci;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public Integer getNrLocuriCumparate() {
        return nrLocuriCumparate;
    }

    public void setNrLocuriCumparate(Integer nrLocuriCumparate) {
        this.nrLocuriCumparate = nrLocuriCumparate;
    }
}
