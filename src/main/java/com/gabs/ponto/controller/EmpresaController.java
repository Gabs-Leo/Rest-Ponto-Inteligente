package com.gabs.ponto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.ponto.dtos.EmpresaDto;
import com.gabs.ponto.entities.Empresa;
import com.gabs.ponto.response.Response;
import com.gabs.ponto.service.impl.EmpresaServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empresas/")
public class EmpresaController {
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> viewEmpresasPorCnpj(@PathVariable("cnpj") String cnpj){
		var response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		
		if(!empresa.isPresent()) {
			response.getErros().add("Empresa não encontrada no CNPJ: " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}
	
	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		var empresaDto = new EmpresaDto();
		empresaDto.setId(empresa.getId());
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		return empresaDto;
	}
}
