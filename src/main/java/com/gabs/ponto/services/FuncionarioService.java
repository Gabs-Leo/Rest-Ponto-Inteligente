package com.gabs.ponto.services;

import java.util.Optional;

import com.gabs.ponto.entities.Funcionario;

public interface FuncionarioService {
	Funcionario salvarFuncionario(Funcionario funcionario);
	Optional<Funcionario> buscarPorCpf(String cpf);
	Optional<Funcionario> buscarPorEmail(String email);
	Optional<Funcionario> buscarPorId(Long id);
}
