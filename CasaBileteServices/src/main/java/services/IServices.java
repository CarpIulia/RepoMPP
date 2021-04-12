package services;

import domain.Meci;

import java.io.Serializable;

public interface IServices {
    boolean checkUsernameAndPassword(String numeUtilizator, String parola, IObserver iObserver);

    Iterable<Meci> getAllMeciuri();

    void updateMeci(Integer id, Integer nrLocuri);

    void addBilet(Integer idMeci, String numeClient, Integer nrLocuriCumparate);

    void logOut(String numeUtilizator);
}
