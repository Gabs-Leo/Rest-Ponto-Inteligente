package com.gabs.ponto.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.gabs.ponto.entities.Lancamento;

public interface LancamentoService {
	Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	Optional<Lancamento> buscarPorId(Long id);
	Lancamento saveLancamento(Lancamento lancamento);
	void removerLancamento(Long id);
}
