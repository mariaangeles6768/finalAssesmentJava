package com.example.SpringBootUsuariosCRUD.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootUsuariosCRUD.entity.Usuario;
import com.example.SpringBootUsuariosCRUD.repository.UsuariosRepository;

@CrossOrigin(origins="http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UsuariosController {
	@Autowired
	UsuariosRepository usuariosRepository; 
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getAllUsuarios(@RequestParam(required = false) String firstName){
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>(); 
			if(firstName == null)
				usuariosRepository.findAll().forEach(usuarios::add); 
			else
				usuariosRepository.findByFirstName(firstName).forEach(usuarios::add); 
			if(usuarios.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
			}
			
			return new ResponseEntity<>(usuarios, HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") long id){
		Optional<Usuario> usuarioData = usuariosRepository.findById(id); 
		if(usuarioData.isPresent()) { 
			return new ResponseEntity<>(usuarioData.get(),HttpStatus.OK);
			
		} else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
		
	}
	
	@PostMapping("/usuarios")
	
	public ResponseEntity<Usuario> createUsuario (@RequestBody Usuario usuario){
		try {
			Usuario _usuario = usuariosRepository.save(new Usuario(usuario.getId(), usuario.getFirstName(),usuario.getLastName(),usuario.getPhoneNumber(),
					usuario.getEmail())); 
			return new ResponseEntity<>(_usuario, HttpStatus.CREATED); 
		}catch(Exception e ) {
			
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		
		
		
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> updateUsuario (@PathVariable("id") long id, @RequestBody Usuario usuario){
		
		Optional<Usuario> usuarioData = usuariosRepository.findById(id);
		if(usuarioData.isPresent()) { 
			Usuario _usuario = usuarioData.get(); 
			_usuario.setFirstName(usuario.getFirstName()); 
			_usuario.setLastName(usuario.getLastName()); 
			_usuario.setPhoneNumber(usuario.getPhoneNumber()); 
			_usuario.setEmail(usuario.getEmail()); 
			return new ResponseEntity<>(usuarioData.get(),HttpStatus.OK);
			
		} else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
		
		
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable("id") long id){
		try {
			usuariosRepository.deleteById(id);
			return new ResponseEntity<> (HttpStatus.NO_CONTENT); 
		} catch(Exception e) {
			return new ResponseEntity<> (HttpStatus.EXPECTATION_FAILED); 
		}
	}
	
	@DeleteMapping("/usuarios")
	public ResponseEntity<Usuario> deleteAllUsuarios(){
		try {
			usuariosRepository.deleteAll();
			return new ResponseEntity<> (HttpStatus.NO_CONTENT); 
		} catch(Exception e) {
			return new ResponseEntity<> (HttpStatus.EXPECTATION_FAILED); 
		}
	}
	
	
	
	
	
	
	
	
	
	
	

}
