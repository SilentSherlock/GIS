package com.programs.gis.entity;

import lombok.Data;

@Data
public class CornLeaf {

    private Integer DOY;//复合主键
    private Float TRT;//复合主键
    private Double leafArea;
    private Double leafPerimeter;
    private Integer leafNumber;
    private Float recordDay;//属性存疑

    public Integer getDOY() {
        return DOY;
    }

    public void setDOY(Integer DOY) {
        this.DOY = DOY;
    }

    public Float getTRT() {
        return TRT;
    }

    public void setTRT(Float TRT) {
        this.TRT = TRT;
    }

    public Double getLeafArea() {
        return leafArea;
    }

    public void setLeafArea(Double leafArea) {
        this.leafArea = leafArea;
    }

    public Double getLeafPerimeter() {
        return leafPerimeter;
    }

    public void setLeafPerimeter(Double leafPerimeter) {
        this.leafPerimeter = leafPerimeter;
    }

    public Integer getLeafNumber() {
        return leafNumber;
    }

    public void setLeafNumber(Integer leafNumber) {
        this.leafNumber = leafNumber;
    }

    public Float getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(Float recordDay) {
        this.recordDay = recordDay;
    }
}
