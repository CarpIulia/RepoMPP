package repository;

import domain.Casier;

public interface IRepoCasieri extends IRepository<Integer, Casier>{
    Boolean checkUsernameAndPassword(Casier casier);
}
