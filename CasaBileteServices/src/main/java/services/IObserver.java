package services;

import domain.Bilet;
import domain.Meci;
import domain.Casier;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void actualizareLocuri(Meci meci) throws RemoteException;
}
