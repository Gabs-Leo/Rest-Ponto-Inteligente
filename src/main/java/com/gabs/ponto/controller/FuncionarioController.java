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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.ponto.domain.PasswordUtils;
import com.gabs.ponto.dtos.FuncionarioDto;
import com.gabs.ponto.entities.Funcionario;
import com.gabs.ponto.response.Response;
import com.gabs.ponto.service.impl.FuncionarioServiceImpl;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	@Autowired
	private FuncionarioServiceImpl funcService;
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable(value = "id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException{
		
		var response = new Response<FuncionarioDto>();
		Optional<Funcionario> funcionario = funcService.buscarPorId(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionario não encontrado!"));
		}
		this.atualizarFuncionario(funcionario.get(), funcionarioDto, result);
		if(result.hasErrors()) {
			result.getAllErrors().forEach(i -> response.getErros().add(i.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.atualizarFuncionario(funcionario.get(), funcionarioDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(i -> response.getErros().add(i.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		funcService.salvarFuncionario(funcionario.get());
		response.setData(converterFuncionarioDto(funcionario.get()));
		return ResponseEntity.ok(response);
	}
	
	private void atualizarFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		funcionario.setNome(funcionarioDto.getNome());
		if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			this.funcService.buscarPorEmail(funcionarioDto.getEmail())
				.ifPresent(i -> result.addError(new ObjectError("email", "Email já existente!")));
			funcionario.setEmail(funcionarioDto.getEmail());
		}
		funcionario.setQtdHorasAlmoco(null);
		funcionarioDto.getQtdHorasAlmoco()
			.ifPresent(i -> funcionario.setQtdHorasAlmoco(Float.valueOf(i)));
		
		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getQtdHorasTrabalhoDia()
			.ifPresent(i -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(i)));
		
		funcionario.setValorHora(null);
		funcionarioDto.getValorHora()
			.ifPresent(i -> funcionario.setValorHora(new BigDecimal(i)));
		
		if(funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(PasswordUtils.encrypt(funcionarioDto.getSenha().get(), 10));
		}
	}
	
	public FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		var funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setNome(funcionario.getNome());
		
		funcionario.getQtdHorasAlmocoOpt()
			.ifPresent(i -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(i))));
		funcionario.getQtdHorasTrabalhoDiaOpt()
			.ifPresent(i -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(i))));
		funcionario.getValorHoraOpt()
			.ifPresent(i -> funcionarioDto.setValorHora(Optional.of(i.toString())));
		
		return funcionarioDto;
	}
}
