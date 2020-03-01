package com.programs.gis.entity;

import lombok.Data;

import java.util.Date;

@Data
public class climaticStation {

    private Integer dataId;
    private Date recordDate;
    private Float airTemperature;
    private Float airHumidity;
    private Integer radiationAmount;
    private Float windSpeed;
    private Float rainfall;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Float getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Float airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Float getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(Float airHumidity) {
        this.airHumidity = airHumidity;
    }

    public Integer getRadiationAmount() {
        return radiationAmount;
    }

    public void setRadiationAmount(Integer radiationAmount) {
        this.radiationAmount = radiationAmount;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getRainfall() {
        return rainfall;
    }

    public void setRainfall(Float rainfall) {
        this.rainfall = rainfall;
    }
}
