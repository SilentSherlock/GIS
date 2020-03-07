package com.programs.gis.dao;

import com.programs.gis.entity.CornLeaf;

import java.util.List;

public interface CornLeafDao {

    void save(CornLeaf cornLeaf) throws Exception;
    void deleteByPrimaryKey(Integer DOY, Float TRT) throws Exception;
    List<CornLeaf> getAll() throws Exception;
    CornLeaf getByPrimaryKey(Integer DOY, Float TRT) throws Exception;
}
