package com.gabs.ponto.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gabs.ponto.entities.Empresa;

public interface EmpresaRepos extends JpaRepository<Empresa, Long>{
	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
}
