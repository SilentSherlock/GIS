package com.programs.gis.entity.corn;

import lombok.Data;

@Data
//玉米株高和叶绿素含量
public class CornHeightAndChlo {

    private Integer DOY;//复合主键
    private Integer TRT;
    private Float NUM_1;
    private Float NUM_2;//*-*-* -> *.** 复合主键
    private Integer NUM_3;
    private Float height;
    private Float chlorophyll;//叶绿素

    public Integer getDOY() {
        return DOY;
    }

    public void setDOY(Integer DOY) {
        this.DOY = DOY;
    }

    public Integer getTRT() {
        return TRT;
    }

    public void setTRT(Integer TRT) {
        this.TRT = TRT;
    }

    public Float getNUM_1() {
        return NUM_1;
    }

    public void setNUM_1(Float NUM_1) {
        this.NUM_1 = NUM_1;
    }

    public Float getNUM_2() {
        return NUM_2;
    }

    public void setNUM_2(Float NUM_2) {
        this.NUM_2 = NUM_2;
    }

    public Integer getNUM_3() {
        return NUM_3;
    }

    public void setNUM_3(Integer NUM_3) {
        this.NUM_3 = NUM_3;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getChlorophyll() {
        return chlorophyll;
    }

    public void setChlorophyll(Float chlorophyll) {
        this.chlorophyll = chlorophyll;
    }
}
