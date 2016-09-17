package models.service;


import java.util.Optional;

import com.avaje.ebean.Model;


public interface ModelService<T extends Model> {

	Optional<T> findById(Long id);

	Optional<T> save(T entry);

	Optional<T> update(T entry);

	boolean delete(T entry);
}
