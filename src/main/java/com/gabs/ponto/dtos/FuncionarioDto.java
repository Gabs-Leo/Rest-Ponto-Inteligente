package com.gabs.ponto.dtos;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class FuncionarioDto {
	private Long id;
	private String nome, email;
	private Optional<String> senha = Optional.empty(),
			valorHora = Optional.empty(),
			qtdHorasAlmoco = Optional.empty(),
			qtdHorasTrabalhoDia = Optional.empty();
	
	public FuncionarioDto() {}
	public FuncionarioDto(Long id, String nome, String email, Optional<String> senha, Optional<String> valorHora,
			Optional<String> qtdHorasAlmoco, Optional<String> qtdHorasTrabalhoDia) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.valorHora = valorHora;
		this.qtdHorasAlmoco = qtdHorasAlmoco;
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
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
	@Length(min = 3, max = 200, message = "Digite um email que tenha entre 3 e 200 letras.")
	@Email(message = "Email inválido!")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Optional<String> getSenha() {
		return senha;
	}
	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}
	public Optional<String> getValorHora() {
		return valorHora;
	}
	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}
	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}
	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}
	public Optional<String> getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}
	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}
	
}
