package com.gabs.ponto.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class LancamentoDto {
	private Optional<Long> id = Optional.empty();
	private String data, tipo, descricao, localizacao;
	private Long funcionarioId;
	
	public LancamentoDto(){}

	public LancamentoDto(Optional<Long> id, String data, String tipo, String descricao, String localizacao,
			Long funcionarioId) {
		super();
		this.id = id;
		this.data = data;
		this.tipo = tipo;
		this.descricao = descricao;
		this.localizacao = localizacao;
		this.funcionarioId = funcionarioId;
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	@NotEmpty(message = "Data não pode ser vazia!")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	
}	
