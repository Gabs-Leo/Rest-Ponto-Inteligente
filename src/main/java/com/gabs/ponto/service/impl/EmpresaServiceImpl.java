package com.gabs.ponto.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabs.ponto.entities.Empresa;
import com.gabs.ponto.repositories.EmpresaRepos;
import com.gabs.ponto.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{
	@Autowired
	private EmpresaRepos empresaRepos;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		return Optional.ofNullable(empresaRepos.findByCnpj(cnpj));
	}

	@Override
	public Empresa saveEmpresa(Empresa empresa) {
		return this.empresaRepos.save(empresa);
	}

}
