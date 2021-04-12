package repository;

public interface IRepository <ID,E>{
    void save(E e);
    void delete(ID id);
    void update(ID id, E otherE);
    Iterable<E> getAll();
    E getOne(ID id);
}
