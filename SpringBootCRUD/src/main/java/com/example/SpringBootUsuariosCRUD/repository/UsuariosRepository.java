package com.example.SpringBootUsuariosCRUD.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootUsuariosCRUD.entity.Usuario;




public interface UsuariosRepository extends JpaRepository<Usuario, Long>{
	List<Usuario> findByFirstName(String firstName); 
	List<Usuario> findByLastName(String lastName); 
	


	
	
}
