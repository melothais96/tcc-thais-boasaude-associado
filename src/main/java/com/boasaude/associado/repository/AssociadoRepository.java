package com.boasaude.associado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boasaude.associado.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long>{
	public Optional<Associado> findByUsuario(String usuario);
	public Optional<Associado> findByNome(String nome);
	public Optional<Associado> findByCpf(String cpf);
}
