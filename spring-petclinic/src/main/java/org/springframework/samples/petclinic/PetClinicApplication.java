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

package org.springframework.samples.petclinic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.vet.SpecialityRepository;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 *
 */
@SpringBootApplication(proxyBeanMethods = false)
//@ComponentScan(basePackages ={""}) 
public class PetClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demoVetRepository(VetRepository vetRepository, SpecialityRepository specialityRepository) {
		return (args) -> {
		//Crear un objeto Vet sin Speciality 
		Vet vet = new Vet();
		vet.setFirstName("Carmen");
		vet.setLastName("Alonso");
		//Persistir el objeto Vet en BBDD
		vetRepository.save(vet);
		
		//Consultar por ID y comprobar que se ha creado correctamente
		Vet vetAUX = vetRepository.findOne(vet.getId());
		//Editar el elemento recién creado para añadir una Speciality concreta
		Specialty s = specialityRepository.findOne(1);
		vet.addSpecialty(s);
		vetRepository.save(vet);
		//Listar todos los veterinarios existentes
		for (Vet v: vetRepository.findAll()) {
			System.out.println("Vet: " + v);
		}
	};
	}

}
