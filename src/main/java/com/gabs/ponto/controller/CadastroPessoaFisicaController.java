package com.gabs.ponto.controller;


import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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
import com.gabs.ponto.dtos.CadastroPessoaFisicaDto;
import com.gabs.ponto.entities.Empresa;
import com.gabs.ponto.entities.Funcionario;
import com.gabs.ponto.response.Response;
import com.gabs.ponto.service.impl.EmpresaServiceImpl;
import com.gabs.ponto.service.impl.FuncionarioServiceImpl;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPessoaFisicaController {
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private FuncionarioServiceImpl funcService;
	
	@PostMapping
	public ResponseEntity<Response<CadastroPessoaFisicaDto>> cadastrar(@Valid @RequestBody CadastroPessoaFisicaDto cadastroDto,
			BindingResult result) throws NoSuchAlgorithmException {
		var response = new Response<CadastroPessoaFisicaDto>();
		validarDadosExistentes(cadastroDto, result);
		var funcionario = this.converterDtoParaFuncionario(cadastroDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroDto.getCnpj());
		empresa.ifPresent(i -> funcionario.setEmpresa(i));
		this.funcService.salvarFuncionario(funcionario);
		
		response.setData(converterPessoaParaDto(funcionario));
		return ResponseEntity.ok(response);
	}
	
	private void validarDadosExistentes(CadastroPessoaFisicaDto cadastroDto, BindingResult result) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroDto.getCnpj());
		if(!empresa.isPresent())
			result.addError(new ObjectError("empresa", "Empresa não cadastrada!"));
		
		this.funcService.buscarPorCpf(cadastroDto.getCpf())
			.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));
		this.funcService.buscarPorEmail(cadastroDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
	}
	
	private Funcionario converterDtoParaFuncionario(CadastroPessoaFisicaDto pessoaDto, BindingResult result) throws NoSuchAlgorithmException {
		var funcionario = new Funcionario();
		funcionario.setNome(pessoaDto.getNome());
		funcionario.setEmail(pessoaDto.getEmail());
		funcionario.setSenha(PasswordUtils.encrypt(pessoaDto.getSenha(), 10));
		funcionario.setCpf(pessoaDto.getCpf());
		funcionario.setPerfil(PerfilType.ROLE_USUARIO);
		
		pessoaDto.getQtdHorasAlmoco()
			.ifPresent(i -> funcionario.setQtdHorasAlmoco(Float.valueOf(i)));
		pessoaDto.getQtdHorasTrabalhoDia()
			.ifPresent(i -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(i)));
		pessoaDto.getValorHora()
			.ifPresent(i -> funcionario.setValorHora(new BigDecimal(i)));
		return funcionario;
	}
	
	private CadastroPessoaFisicaDto converterPessoaParaDto(Funcionario funcionario) {
		var pessoaDto = new CadastroPessoaFisicaDto();
		pessoaDto.setId(funcionario.getId());
		pessoaDto.setNome(funcionario.getNome());
		pessoaDto.setEmail(funcionario.getEmail());
		pessoaDto.setCpf(funcionario.getCpf());
		pessoaDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		//Transient
		funcionario.getQtdHorasTrabalhoDiaOpt()
			.ifPresent(i -> pessoaDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(i))));
		funcionario.getQtdHorasAlmocoOpt()
			.ifPresent(i -> pessoaDto.setQtdHorasAlmoco(Optional.of(Float.toString(i))));
		
		funcionario.getValorHoraOpt()
			.ifPresent(i -> pessoaDto.setValorHora(Optional.of(i.toString())));
		
		return pessoaDto;
	}
}
