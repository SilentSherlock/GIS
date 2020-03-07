package com.programs.gis.dao;

import com.programs.gis.entity.ClimaticStation;

import java.util.Date;
import java.util.List;

public interface ClimaticStationDao {

    void save(ClimaticStation climaticStation) throws Exception;
    List<ClimaticStation> getAll() throws Exception;
    void deleteById(Integer dataId) throws Exception;
    ClimaticStation getById(Integer dataId) throws Exception;
    ClimaticStation getByDate(Date recordDate) throws Exception;
}
