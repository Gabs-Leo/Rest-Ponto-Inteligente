package com.gabs.ponto.services;

import java.util.Optional;

import com.gabs.ponto.entities.Empresa;

public interface EmpresaService {
	Optional<Empresa> buscarPorCnpj(String cnpj);
	Empresa saveEmpresa(Empresa empresa);
}
