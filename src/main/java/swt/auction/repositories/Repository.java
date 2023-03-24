package swt.auction.repositories;

import jakarta.persistence.*;

public abstract class Repository<T, ID> {

  protected EntityManager entityManager;

  private Class<T> type;

  public Repository(Class<T> clazz, EntityManager entityManager) {
    this.type = clazz;
    this.entityManager = entityManager;
  }


  public void save(T entity) {
    entityManager.persist(entity);
  }

  public T find(T entity) {
    return (T) entityManager.find(type, entity);
  }

  public T findById(ID primaryKey) {
    return (T) entityManager.find(type, primaryKey);
  }

  public Iterable<T> findAll() {
    return entityManager.createQuery("from " + type.getName()).getResultList();
  }

  public void delete(T entity) {
    entityManager.remove(entity);
  }

  public boolean deleteById(ID primaryKey) {
    var entity = findById(primaryKey);
    entityManager.remove(entity);
    return true;
  }
}
