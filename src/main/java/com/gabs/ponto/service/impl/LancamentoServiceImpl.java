package com.gabs.ponto.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gabs.ponto.entities.Lancamento;
import com.gabs.ponto.repositories.LancamentoRepos;
import com.gabs.ponto.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{

	@Autowired
	private LancamentoRepos lancamentoRepos;
	
	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		return lancamentoRepos.findAll(pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		return lancamentoRepos.findById(id);
	}

	@Override
	public Lancamento saveLancamento(Lancamento lancamento) {
		return lancamentoRepos.save(lancamento);
	}

	@Override
	public void removerLancamento(Long id) {
		lancamentoRepos.deleteById(id);
	}

}
