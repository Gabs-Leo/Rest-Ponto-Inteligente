package com.gabs.ponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabs.ponto.entities.Funcionario;

public interface FuncionarioRepos extends JpaRepository<Funcionario, Long>{
	Funcionario findByCpf(String cpf);
	Funcionario findByEmail(String email);
	Funcionario findByCpfOrEmail(String cpf, String email);
}
