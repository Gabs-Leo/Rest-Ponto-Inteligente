package com.gabs.ponto.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPessoaJuridicaDto {
	private Long id;
	private String nome, email, senha, cpf, razaoSocial, cnpj;
	public CadastroPessoaJuridicaDto() {};
	
	public CadastroPessoaJuridicaDto(Long id, String nome, String email, String senha, String cpf, String razaoSocial,
			String cnpj) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Insira um Nome.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotEmpty(message = "Insira um Endereço de Email.")
	@Length(min = 3, max = 200, message = "Email deve conter entre 3 e 200 caracteres.")
	@Email(message = "Insira um email válido.")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotEmpty(message = "Insira uma Senha.")
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@NotEmpty(message = "Insira um CPF.")
	@CPF(message = "CPF inválido.")
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@NotEmpty(message = "Insira uma Razão Social.")
	@Length(min = 3, max = 200, message = "Razão social deve conter de 3 a 200 caracteres.")
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	@NotEmpty(message = "Insira um CNPJ.")
	@CNPJ(message = "Insira um CNPJ válido.")
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
