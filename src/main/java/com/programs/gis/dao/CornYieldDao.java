package com.programs.gis.dao;

import com.programs.gis.entity.CornYield;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CornYieldDao {
    void save(CornYield cornYield) throws Exception;
    List<CornYield> getAllYield() throws Exception;
    CornYield getYieldById(Float cornFieldId) throws Exception;
    void delete(Float cornFieldId) throws Exception;
}
