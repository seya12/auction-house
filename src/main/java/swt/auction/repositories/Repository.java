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
    if(primaryKey == null){
      return null;
    }
    return (T) entityManager.find(type, primaryKey);
  }

  public List<T> findAll() {
    return entityManager.createQuery("from " + type.getName(), type).getResultList();
  }

  public T update(T entity){
    return entityManager.merge(entity);

  }

  public boolean delete(T entity) {
    if(entity == null){
      return false;
    }
    return deleteById(entity.getId());
  }

  public boolean deleteById(Long primaryKey) {
    var entity = find(primaryKey);

    if(entity == null) {
      return false;
    }

    entityManager.remove(entity);
    return true;
  }
}
