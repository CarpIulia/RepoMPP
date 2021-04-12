package service;

import domain.Bilet;
import domain.Casier;
import domain.Meci;
import domain.TipMeci;
import repository.*;

import services.IServices;
import services.IObserver;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CasaBileteService implements IServices {
    private IRepoCasieri repoCasieri;
    private IRepoMeciuri repoMeciuri;
    private IRepoBilete repoBilete;
    private Map<String, IObserver> casieriConectati;

    public CasaBileteService(IRepoCasieri repoCasieri, IRepoMeciuri repoMeciuri, IRepoBilete repoBilete) {
        this.repoCasieri = repoCasieri;
        this.repoMeciuri = repoMeciuri;
        this.repoBilete = repoBilete;
        casieriConectati = new ConcurrentHashMap<>();
    }

    public boolean checkUsernameAndPassword(String numeUtilizator, String parola, IObserver client) {
        Casier casier = new Casier(numeUtilizator, parola);
        //if(casieriConectati.containsKey(numeUtilizator))
            //return false;
        //else {
            boolean rez = repoCasieri.checkUsernameAndPassword(casier);
            if(rez == true)
                casieriConectati.put(numeUtilizator, client);
            return rez;
        //}
    }

    public Iterable<Meci> getAllMeciuri() {
        return repoMeciuri.getAll();
    }

    public void updateMeci(Integer id, Integer nrLocuri) {
        Meci meci = new Meci("","",TipMeci.Finala,0.0,nrLocuri);
        repoMeciuri.update(id, meci);
        notifyBiletCumparat(meci);
    }

    public void addBilet(Integer idMeci, String numeClient, Integer nrLocuriCumparate) {
        Bilet bilet = new Bilet(idMeci, numeClient, nrLocuriCumparate);
        repoBilete.save(bilet);
    }

    private final int defaultThreadsNo = 5;
    private void notifyBiletCumparat(Meci meci) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        Iterable<IObserver> observers = casieriConectati.values();
        for(IObserver obs: observers)
            executor.execute(() -> {
                try {
                    obs.actualizareLocuri(meci);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        executor.shutdown();
    }

    public void logOut(String numeUtilizator) {
        casieriConectati.remove(numeUtilizator);
    }
}
