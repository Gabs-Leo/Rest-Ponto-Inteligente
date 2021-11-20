package com.gabs.ponto.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.ponto.dtos.CadastroPessoaFisicaDto;
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
		
		return null;
	}
}
