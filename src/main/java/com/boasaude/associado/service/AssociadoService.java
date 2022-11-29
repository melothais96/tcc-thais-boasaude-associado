package com.boasaude.associado.service;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.boasaude.associado.model.Associado;
import com.boasaude.associado.model.AssociadoLogin;
import com.boasaude.associado.repository.AssociadoRepository;

@Service
public class AssociadoService {
	@Autowired
	private AssociadoRepository repository;

	private static final Logger LOGGER = Logger.getLogger(AssociadoService.class.getName());

	public AssociadoService(AssociadoRepository repository) {
		this.repository = repository;
	}

	public Associado cadastrarUsuario(Associado associado) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(associado.getSenha());
		associado.setSenha(senhaEncoder);

		return repository.save(associado);
	}

	public Optional<AssociadoLogin> logar(Optional<AssociadoLogin> user) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Associado> usuario = repository.findByUsuario(user.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				user.get().setId(usuario.get().getId());
				user.get().setAdmin(usuario.get().isAdmin());

				return user;

			}
		}
		return null;
	}

	public void run(ApplicationArguments args) throws Exception {
		Associado associado = new Associado("Gaspar Barancelli Junior");

		LOGGER.log(Level.INFO, "Persist");
		repository.save(associado);
		LOGGER.log(Level.INFO, associado.toString());

		LOGGER.log(Level.INFO, "Find");
		repository.findById(associado.getId()).ifPresent(it -> {
			LOGGER.log(Level.INFO, associado.toString());
		});

		Associado associado2 = new Associado("Rodrigo Barancelli");

		LOGGER.log(Level.INFO, "Persist");
		repository.save(associado2);
		LOGGER.log(Level.INFO, associado2.toString());

		associado2.setNome("Rodrigo Dalla Valle Barancelli");
		LOGGER.log(Level.INFO, "Update");
		repository.save(associado2);
		LOGGER.log(Level.INFO, associado2.toString());

		LOGGER.log(Level.INFO, "FindAll");
		repository.findAll().forEach(it -> LOGGER.log(Level.INFO, it.toString()));

		LOGGER.log(Level.INFO, "Delete");
		repository.delete(associado2);
		LOGGER.log(Level.INFO, associado2.toString());
	}
}
