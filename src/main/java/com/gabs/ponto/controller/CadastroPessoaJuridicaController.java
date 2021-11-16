package com.gabs.ponto.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.ponto.domain.PasswordUtils;
import com.gabs.ponto.domain.PerfilType;
import com.gabs.ponto.dtos.CadastroPessoaJuridicaDto;
import com.gabs.ponto.entities.Empresa;
import com.gabs.ponto.entities.Funcionario;
import com.gabs.ponto.response.Response;
import com.gabs.ponto.service.impl.EmpresaServiceImpl;
import com.gabs.ponto.service.impl.FuncionarioServiceImpl;

@RestController
@RequestMapping("/api/cadastrar-pj")
//Adiciona URLs para acessar o controller
@CrossOrigin(origins = "*")
public class CadastroPessoaJuridicaController {
	@Autowired
	private FuncionarioServiceImpl funcionarioService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@PostMapping
	public ResponseEntity<Response<CadastroPessoaJuridicaDto>> cadastrarPessoaJuridica(
			@Valid @RequestBody CadastroPessoaJuridicaDto cadastroDto,
			BindingResult result) throws NoSuchAlgorithmException{
		var response = new Response<CadastroPessoaJuridicaDto>();
		validarDados(cadastroDto, result);
		Empresa empresa = converterDtoParaEmpresa(cadastroDto);
		Funcionario funcionario = converterDtoParaFuncionario(cadastroDto);
		if(result.hasErrors()) {
			result.getAllErrors().forEach(i -> response.getErros().add(i.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		empresaService.saveEmpresa(empresa);
		funcionario.setEmpresa(empresa);
		funcionarioService.salvarFuncionario(funcionario);
		
		response.setData(converterCadastroDto(funcionario));
		return ResponseEntity.ok(response);
	}
	
	//Verifica se dados já existem na database
	public void validarDados(CadastroPessoaJuridicaDto cadastroDto, BindingResult result) {
		empresaService.buscarPorCnpj(cadastroDto.getCnpj()).ifPresent(
				i -> result.addError(new ObjectError("empresa", "Empresa já existe!")));
		funcionarioService.buscarPorCpf(cadastroDto.getCpf()).ifPresent(
				i -> result.addError(new ObjectError("funcionario", "CPF já existe!")));
		funcionarioService.buscarPorEmail(cadastroDto.getEmail()).ifPresent(
				i -> result.addError(new ObjectError("funcionario", "Email já existe!")));
	}
	
	public Empresa converterDtoParaEmpresa(CadastroPessoaJuridicaDto pessoaDto) {
		var empresa = new Empresa();
		empresa.setCnpj(pessoaDto.getCnpj());
		empresa.setRazaoSocial(pessoaDto.getRazaoSocial());
		
		return empresa;
	}
	
	public Funcionario converterDtoParaFuncionario(CadastroPessoaJuridicaDto pessoaDto) {
		var funcionario = new Funcionario();
		funcionario.setNome(pessoaDto.getNome());
		funcionario.setPerfil(PerfilType.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.encrypt(pessoaDto.getSenha(), 10));
		funcionario.setCpf(pessoaDto.getCpf());
		funcionario.setEmail(pessoaDto.getEmail());
		
		return funcionario;
	}
	
	public CadastroPessoaJuridicaDto converterCadastroDto(Funcionario funcionario) {
		var dto = new CadastroPessoaJuridicaDto();
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setCpf(funcionario.getCpf());
		dto.setSenha(PasswordUtils.encrypt(funcionario.getSenha(), 10));
		dto.setEmail(funcionario.getEmail());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		
		return dto;
	}
}
