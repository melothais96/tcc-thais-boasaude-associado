package com.boasaude.associado.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.boasaude.associado.model.Associado;
import com.boasaude.associado.model.AssociadoLogin;
import com.boasaude.associado.repository.AssociadoRepository;
import com.boasaude.associado.service.AssociadoService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/associado")
public class AssociadoController {
	
	@Autowired
	private AssociadoRepository repository;
	
	@Autowired
	private AssociadoService associadoService;
	
	@GetMapping
	public ResponseEntity<List<Associado>> getAllAssociado()
	{
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Associado> findByIdAssociado(@PathVariable long id)
	{
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Optional<Associado>> getByNome(@PathVariable String nome)
	{
		return ResponseEntity.ok(repository.findByNome(nome));
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<Optional<Associado>> getByCpf(@PathVariable String cpf)
	{
		return ResponseEntity.ok(repository.findByCpf(cpf));
	}
	
	@PostMapping
	public ResponseEntity<Associado> post(@RequestBody Associado associado)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(associado));
	}
	
	@PutMapping("/id/{id}")
	public ResponseEntity<Associado> put(@RequestBody Associado associado)
	{
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(associado));
	}
	
	@DeleteMapping("/id/{id}")
	public void delete(@PathVariable long id)
	{
		repository.deleteById(id);
	}
	
	@PostMapping("/logar")
	public ResponseEntity<AssociadoLogin> Autentication(@RequestBody Optional<AssociadoLogin> user) {
		return associadoService.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Associado> Post(@RequestBody Associado usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(associadoService.cadastrarUsuario(usuario));
	}
}
