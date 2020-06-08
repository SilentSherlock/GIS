package com.programs.gis.entity.field;

/**
 * Date: 2020/5/13
 * Description: water hold
 */
public class FieldWaterHold {

    private Integer TRT;
    private String NUM_1;
    private String NUM_2;//primary key
    private Float massWaterContent;//质量含水率
    private Float volumeWaterContent;//体积含水率
    private Float contentWeight;//容重

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

    public Float getMassWaterContent() {
        return massWaterContent;
    }

    public void setMassWaterContent(Float massWaterContent) {
        this.massWaterContent = massWaterContent;
    }

    public Float getVolumeWaterContent() {
        return volumeWaterContent;
    }

    public void setVolumeWaterContent(Float volumeWaterContent) {
        this.volumeWaterContent = volumeWaterContent;
    }

    public Float getContentWeight() {
        return contentWeight;
    }

    public void setContentWeight(Float contentWeight) {
        this.contentWeight = contentWeight;
    }
}
