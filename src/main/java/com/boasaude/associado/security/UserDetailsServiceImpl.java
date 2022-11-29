package com.boasaude.associado.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boasaude.associado.model.Associado;
import com.boasaude.associado.repository.AssociadoRepository;

@Service
public class UserDetailsServiceImpl {
	@Autowired
	private AssociadoRepository associadoRepository;
	
	public UserDetailsImpl loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Associado> user = associadoRepository.findByUsuario(userName);
		user.orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));
		
		return user.map(UserDetailsImpl::new).get();
	}

}
