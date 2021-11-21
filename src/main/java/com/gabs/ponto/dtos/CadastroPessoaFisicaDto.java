package com.gabs.ponto.dtos;


import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPessoaFisicaDto {
	private Long id;
	private String nome, email, senha, cpf, cnpj;
	private Optional<String> valorHora = Optional.empty(),
			qtdHorasTrabalhoDia = Optional.empty(),
			qtdHorasAlmoco = Optional.empty();
	
	public CadastroPessoaFisicaDto() {}

	public CadastroPessoaFisicaDto(Long id, String nome, String email, String senha, String cpf, String cnpj,
			Optional<String> valorHora, Optional<String> qtdHorasTrabalhoDia, Optional<String> qtdHorasAlmoco) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.valorHora = valorHora;
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio!")
	@Length(min = 3, max = 200, message = "Digite um nome que tenha entre 3 e 200 letras.")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotEmpty(message = "Email não pode ser vazio!")
	@Length(min = 3, max = 200, message = "Email inválido!")
	@Email(message = "Email inválido!")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotEmpty(message = "Senha não pode ser vazia!")
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@NotEmpty(message = "Cpf não pode ser vazio!")
	@CPF(message = "CPF inválido!")
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotEmpty(message = "Cnpj não pode ser vazio!")
	@CNPJ(message = "Cnpj inválido!") 										         
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}

	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}

	public Optional<String> getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}

	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}
	
}
