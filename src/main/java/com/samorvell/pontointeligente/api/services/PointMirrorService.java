package com.samorvell.pontointeligente.api.services;

import com.samorvell.pontointeligente.api.model.Lancamento;
import com.samorvell.pontointeligente.api.model.PointMirror;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface PointMirrorService {

    /**
     * Retorna uma lista paginada de pointMirror de um determinado funcionário.
     *
     * @param funcionarioId
     * @param pageRequest
     * @return Page<PointMirror>
     */
   // Page<PointMirror> findPointMirrorByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Retorna um PointMirror por ID.
     *
     * @param mirrorId
     * @return Optional<PointMirror>
     */
    //Optional<PointMirror> buscarPorMirrorId(Long mirrorId);

    /**
     * Persiste um lançamento na base de dados.
     *
     * @param pointMirror
     * @return pointMirror
     */
   // PointMirror persistir(PointMirror pointMirror);

    /**
     * Remove um lançamento da base de dados.
     *
     * @param mirrorId
     */
    /*    void remover(Long mirrorId);*/

    /* List<PointMirror> findPointMirrorByFuncionarioId(Long funcionarioId);*/


    /**
     * Persiste espeslho de ponto na base de dados.
     *
     * @param launch
     * @return PointMirror
     */

    List<Lancamento> saveMirrorPointById(List<Lancamento> launch, Long funcionarioId);
    Optional<Lancamento> saveBMirrorPointById(Optional<Lancamento> launch, Long funcionarioId);

}