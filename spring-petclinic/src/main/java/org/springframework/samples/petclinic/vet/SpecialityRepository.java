package org.springframework.samples.petclinic.vet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.transaction.annotation.Transactional;

public interface SpecialityRepository extends Repository<Specialty, Integer>{

	//Obtener una especialidad por id
	
	@Query("SELECT speciality FROM Specialty speciality WHERE speciality.id =:id")
	@Transactional(readOnly = true)
	Specialty findOne(@Param("id") Integer id);
}
