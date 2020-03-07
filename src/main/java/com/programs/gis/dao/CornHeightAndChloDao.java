package com.programs.gis.dao;

import com.programs.gis.entity.CornHeightAndChlo;

import java.util.List;

public interface CornHeightAndChloDao {

    void save(CornHeightAndChlo cornHeightAndChlo) throws Exception;
    List<CornHeightAndChlo> getAll() throws Exception;
    void deleteByPrimaryKey(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2) throws Exception;
    CornHeightAndChlo getByPrimaryKey(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2) throws Exception;
}
