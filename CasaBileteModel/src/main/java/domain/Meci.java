package domain;

import java.io.Serializable;

public class Meci extends Entity<Integer> implements Serializable {
    private String echipa1;
    private String echipa2;
    private TipMeci tip;
    private Double pretBilet;
    private Integer nrLocuriDisponibile;

    public Meci(String echipa1, String echipa2, TipMeci tip, Double pretBilet, Integer nrLocuriDisponibile) {
        this.echipa1 = echipa1;
        this.echipa2 = echipa2;
        this.tip = tip;
        this.pretBilet = pretBilet;
        this.nrLocuriDisponibile = nrLocuriDisponibile;
    }

    public String getEchipa1() {
        return echipa1;
    }

    public void setEchipa1(String echipa1) {
        this.echipa1 = echipa1;
    }

    public String getEchipa2() {
        return echipa2;
    }

    public void setEchipa2(String echipa2) {
        this.echipa2 = echipa2;
    }

    public TipMeci getTip() {
        return tip;
    }

    public void setTip(TipMeci tip) {
        this.tip = tip;
    }

    public Double getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(Double pretBilet) {
        this.pretBilet = pretBilet;
    }

    public Integer getNrLocuriDisponibile() {
        return nrLocuriDisponibile;
    }

    public void setNrLocuriDisponibile(Integer nrLocuriDisponibile) {
        this.nrLocuriDisponibile = nrLocuriDisponibile;
    }
}
