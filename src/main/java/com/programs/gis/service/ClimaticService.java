package com.programs.gis.service;

import com.programs.gis.entity.climatic.ClimaticStation;
import com.programs.gis.entity.climatic.FieldPAI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Date: 2020/6/9
 * Description: give a unify operation for Climatic data
 */
@Service
public class ClimaticService {
    @Resource
    private ClimaticStation climaticStation;
    @Resource
    private FieldPAI fieldPAI;

    
}
