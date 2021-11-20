package com.gabs.ponto.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabs.ponto.entities.Funcionario;
import com.gabs.ponto.repositories.FuncionarioRepos;
import com.gabs.ponto.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{
	@Autowired
	private FuncionarioRepos funcionarioRepos;
	
	@Override
	public Funcionario salvarFuncionario(Funcionario funcionario) {
		return funcionarioRepos.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		return Optional.ofNullable(funcionarioRepos.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		return Optional.ofNullable(funcionarioRepos.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		return funcionarioRepos.findById(id);
	}
	
}
