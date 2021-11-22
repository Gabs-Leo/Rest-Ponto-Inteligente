package com.gabs.ponto.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.ponto.dtos.LancamentoDto;
import com.gabs.ponto.entities.Funcionario;
import com.gabs.ponto.entities.Lancamento;
import com.gabs.ponto.enums.LancamentoType;
import com.gabs.ponto.response.Response;
import com.gabs.ponto.service.impl.FuncionarioServiceImpl;
import com.gabs.ponto.service.impl.LancamentoServiceImpl;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {
	private final SimpleDateFormat dateUtils = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@Autowired
	private LancamentoServiceImpl lancamentoService;
	@Autowired
	private FuncionarioServiceImpl funcService;
	
	@GetMapping("/funcionario/{id}")
	public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir,
			@RequestParam(value = "size", defaultValue = "20") int qtdPorPagina
		){
		var response = new Response<Page<LancamentoDto>>();
		
		PageRequest pageRequest = PageRequest.of(pag, qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
		Page<LancamentoDto> lancamentosDto = lancamentos.map(i -> converterLancamentoDto(i));
		
		response.setData(lancamentosDto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<LancamentoDto>> getLancamentoById(
			@PathVariable(value = "id") Long id
		){
		var response = new Response<LancamentoDto>();
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		if(!lancamento.isPresent()) {
			response.getErros().add("Lancamento não encontrado no id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(converterLancamentoDto(lancamento.get()));
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<Response<LancamentoDto>> salvarLancamento(
			@Valid @RequestBody LancamentoDto lancamentoDto,
			BindingResult result
		) throws ParseException{
		var response = new Response<LancamentoDto>();
		validarFuncionario(lancamentoDto, result);
		Lancamento lancamento = converterDtoLancamento(lancamentoDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(i -> response.getErros().add(i.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(converterLancamentoDto(lancamentoService.saveLancamento(lancamento)));
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<LancamentoDto>> atualizarLancamento(
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result
		) throws ParseException{
		var response = new Response<LancamentoDto>();
		validarFuncionario(lancamentoDto, result);
		lancamentoDto.setId(Optional.of(id));
		Lancamento lancamento = converterDtoLancamento(lancamentoDto, result);
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(i -> response.getErros().add(i.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		lancamento = lancamentoService.saveLancamento(lancamento);
		response.setData(converterLancamentoDto(lancamento));
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<LancamentoDto>> deleteLancamento(
			@PathVariable(value = "id") Long id
		){
		var response = new Response<LancamentoDto>();
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		
		if(!lancamento.isPresent()) {
			response.getErros().add("Erro ao remover lancamento. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		lancamentoService.removerLancamento(id);
		return ResponseEntity.ok().body(response);
	}
	
	private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
		var lancamentoDto = new LancamentoDto();
		lancamentoDto.setId(Optional.of(lancamento.getId()));
		lancamentoDto.setDescricao(lancamento.getDescricao());
		lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());
		lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
		lancamentoDto.setTipo(lancamento.getTipo().toString());
		lancamentoDto.setData(dateUtils.format(lancamento.getData()));
		return lancamentoDto;
	}
	
	private void validarFuncionario(LancamentoDto lancamentoDto, BindingResult result) {
		if(lancamentoDto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Funcionario não informado."));
			return;
		}
		Optional<Funcionario> funcionario = funcService.buscarPorId(lancamentoDto.getFuncionarioId());
		if(!funcionario.isPresent()) {
			result.addError(new ObjectError("fucnionario", "Funcionário não encontrado, ID inexistente!"));
		}
	}
	
	private Lancamento converterDtoLancamento(LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
		var lancamento = new Lancamento();
		if(lancamentoDto.getId().isPresent()) {
			Optional<Lancamento> lanc = lancamentoService.buscarPorId(lancamentoDto.getId().get());
			if(lanc.isPresent()) {
				lancamento = lanc.get();
			}else {
				result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
			}
		}else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
		}
		lancamento.setDescricao(lancamentoDto.getDescricao());
		lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
		lancamento.setData(dateUtils.parse(lancamentoDto.getData()));
		
		if(EnumUtils.isValidEnum(LancamentoType.class, lancamentoDto.getTipo())) {
			lancamento.setTipo(LancamentoType.valueOf(lancamentoDto.getTipo()));
		}else {
			result.addError(new ObjectError("tipo", "Tipo inválido"));
		}
		return lancamento;
	}
}
