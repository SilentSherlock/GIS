package com.programs.gis.dao;

import com.programs.gis.entity.CornYield;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CornYieldDao {
    void save(CornYield cornYield) throws Exception;
    List<CornYield> getAll() throws Exception;
    CornYield getById(Float cornFieldId) throws Exception;
    void deleteById(Float cornFieldId) throws Exception;
}
