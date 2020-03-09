package com.programs.gis.dao;

import com.programs.gis.entity.CornHeightAndChlo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CornHeightAndChloDao {

    void save(CornHeightAndChlo cornHeightAndChlo) throws Exception;
    List<CornHeightAndChlo> getAll() throws Exception;
    void deleteByPrimaryKey(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2) throws Exception;
    CornHeightAndChlo getByPrimaryKey(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2) throws Exception;
}
