package com.samorvell.pontointeligente.api.repository;

import com.samorvell.pontointeligente.api.model.Lancamento;
import com.samorvell.pontointeligente.api.model.PointMirror;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "PointMirrorRepository.findByFuncionarioId",
                query = "SELECT point FROM point_mirror point WHERE point.funcionario.id = :funcionarioId") })
public interface PointMirrorRepository extends JpaRepository<PointMirror, Long> {


    List<PointMirror> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

    Page<PointMirror> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);

    PointMirror findByMirrorId(Long mirrorId);

}