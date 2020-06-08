package com.programs.gis.entity.field;

import java.util.Date;

/**
 * Date: 2020/5/13
 * Description: soil water content
 */
public class SWC {//土壤持水量

    private Integer DOY;//primary key
    private Integer TRT;
    private String NUM_1;
    private String NUM_2;//primary key
    private Integer NUM_3;
    private Float boxAndWetSoil;
    private Float boxAndDrySoil;
    private Float box;
    private Float water;
    private Float drySoil;
    private Float SWC;
    private Date recordDate;

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

    public String getNUM_1() {
        return NUM_1;
    }

    public void setNUM_1(String NUM_1) {
        this.NUM_1 = NUM_1;
    }

    public String getNUM_2() {
        return NUM_2;
    }

    public void setNUM_2(String NUM_2) {
        this.NUM_2 = NUM_2;
    }

    public Integer getNUM_3() {
        return NUM_3;
    }

    public void setNUM_3(Integer NUM_3) {
        this.NUM_3 = NUM_3;
    }

    public Float getBoxAndWetSoil() {
        return boxAndWetSoil;
    }

    public void setBoxAndWetSoil(Float boxAndWetSoil) {
        this.boxAndWetSoil = boxAndWetSoil;
    }

    public Float getBoxAndDrySoil() {
        return boxAndDrySoil;
    }

    public void setBoxAndDrySoil(Float boxAndDrySoil) {
        this.boxAndDrySoil = boxAndDrySoil;
    }

    public Float getBox() {
        return box;
    }

    public void setBox(Float box) {
        this.box = box;
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public Float getDrySoil() {
        return drySoil;
    }

    public void setDrySoil(Float drySoil) {
        this.drySoil = drySoil;
    }

    public Float getSWC() {
        return SWC;
    }

    public void setSWC(Float SWC) {
        this.SWC = SWC;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
