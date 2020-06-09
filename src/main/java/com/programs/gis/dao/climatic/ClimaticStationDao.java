package com.programs.gis.dao.climatic;

import com.programs.gis.entity.climatic.ClimaticStation;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClimaticStationDao {

    void save(ClimaticStation climaticStation) throws Exception;
    List<ClimaticStation> getAll() throws Exception;
    void deleteById(String dataId) throws Exception;
    ClimaticStation getById(String dataId) throws Exception;
    List<ClimaticStation> getByAttr(String attrName, Object attrValue) throws Exception;
}
