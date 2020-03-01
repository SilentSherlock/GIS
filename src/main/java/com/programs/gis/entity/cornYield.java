package com.programs.gis.entity;

import lombok.Data;

@Data
public class cornYield {

    private Float cornFieldId;//数据中田地的编号形式为*-*的形式，如1-2，代码中以浮点表示
    private Double yield;
    private Float boxWeight;
    private Float beforeDehydration;//脱水前
    private Float afterDehydration;//脱水后
}
