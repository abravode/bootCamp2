/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Vet</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VetRepository extends Repository<Vet, Integer> {

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
	@Transactional(readOnly = true)
	@Cacheable("vets")
	Collection<Vet> findAll() throws DataAccessException;
	
	/* TALLER 1
	//Obtener una lista de vets filtrando por last name
	
	@Query("SELECT vet FROM Vet vet WHERE vet.lastName LIKE:lastName")
	@Transactional(readOnly = true)
	List<Vet> findByLastName(@Param("lastName") String lastName);
	
	//Obtener una lista de vets filtrando por first name y last name
	
	@Query("SELECT vet FROM Vet vet WHERE vet.lastName LIKE:lastName AND vet.firstName LIKE:firstName")
	@Transactional(readOnly = true)
	List<Vet> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
	//Obtener una lista de vets filtrando por first name o last name
	
	@Query("SELECT vet FROM Vet vet WHERE vet.lastName LIKE:lastName OR vet.firstName LIKE:firstName")
	@Transactional(readOnly = true)
	List<Vet> findByFirstOrLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
		//Sobre VetRepository, crear una query usando @Query que nos devuelva la lista de veterinarios 
	//para la especialidad “radiology”.
	@Query("SELECT distinct vet FROM Vet vet JOIN FETCH vet.specialities s WHERE s.name =:name")
	@Transactional(readOnly = true)
	List<Vet> findBySpeciality(@Param("name") String name);
	
	*/
	

	@Transactional(readOnly = true)
    void save(Vet vet);
	
	//Obtener un veterinario por id
@Query("SELECT vet FROM Vet vet WHERE vet.id =:id")
@Transactional(readOnly = true)
Vet findOne(@Param("id") Integer id);
	

	

}
