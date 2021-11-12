package com.gabs.ponto.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gabs.ponto.entities.Lancamento;

@Transactional(readOnly = true)
@NamedQueries({
						//(Nome dessa Interface).(Nome do Método)
	@NamedQuery(name = "LancamentoRepos.findByFuncionarioId",
		query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId"
	)
})
public interface LancamentoRepos extends JpaRepository<Lancamento, Long>{
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
