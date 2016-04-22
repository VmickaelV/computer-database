package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.persistence.Crudable;

/**
 * Classe générique de Bean de DAO
 *
 * Created by excilys on 14/04/16.
 */
@org.springframework.transaction.annotation.Transactional
public abstract class AbstractGenericCrudServiceBean<T> implements Crudable<T> {
}
