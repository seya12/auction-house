package swt.auction.repositories;

import jakarta.persistence.*;
import swt.auction.entities.*;

import java.util.*;

public abstract class Repository<T extends BaseEntity> {

  protected EntityManager entityManager;

  private Class<T> type;

  public Repository(Class<T> clazz, EntityManager entityManager) {
    this.type = clazz;
    this.entityManager = entityManager;
  }

  public void save(T entity) {
    entityManager.persist(entity);
  }

  public T find(Long primaryKey) {
    return (T) entityManager.find(type, primaryKey);
  }

  public List<T> findAll() {
    return entityManager.createQuery("from " + type.getName(), type).getResultList();
  }

  public T update(T entity){
    return entityManager.merge(entity);

  }

  public void delete(T entity) {
    entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
  }

  public boolean deleteById(Long primaryKey) {
    var entity = find(primaryKey);
    entityManager.remove(entity);
    return true;
  }
}
