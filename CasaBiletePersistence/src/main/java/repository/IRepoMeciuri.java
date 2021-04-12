package repository;
import domain.Meci;

public interface IRepoMeciuri extends IRepository<Integer, Meci>{
    Iterable<Meci> findByEmptySeatsDesc();
}
