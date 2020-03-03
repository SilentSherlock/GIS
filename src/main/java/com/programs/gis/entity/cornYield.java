package com.programs.gis.entity;

import lombok.Data;

@Data
public class cornYield {

    private Float cornFieldId;//数据中田地的编号形式为*-*的形式，如1-2，代码中以浮点表示
    private Double moistureYield;//湿重产量
    private Float boxWeight;
    private Float beforeDehydration;//湿重
    private Float afterDehydration;//干重
    private Float moistureContent;//含水率
    private Double dryYield;

    public Float getCornFieldId() {
        return cornFieldId;
    }

    public void setCornFieldId(Float cornFieldId) {
        this.cornFieldId = cornFieldId;
    }

    public Double getMoistureYield() {
        return moistureYield;
    }

    public void setMoistureYield(Double moistureYield) {
        this.moistureYield = moistureYield;
    }

    public Float getBoxWeight() {
        return boxWeight;
    }

    public void setBoxWeight(Float boxWeight) {
        this.boxWeight = boxWeight;
    }

    public Float getBeforeDehydration() {
        return beforeDehydration;
    }

    public void setBeforeDehydration(Float beforeDehydration) {
        this.beforeDehydration = beforeDehydration;
    }

    public Float getAfterDehydration() {
        return afterDehydration;
    }

    public void setAfterDehydration(Float afterDehydration) {
        this.afterDehydration = afterDehydration;
    }

    public Float getMoistureContent() {
        return moistureContent;
    }

    public void setMoistureContent(Float moistureContent) {
        this.moistureContent = moistureContent;
    }

    public Double getDryYield() {
        return dryYield;
    }

    public void setDryYield(Double dryYield) {
        this.dryYield = dryYield;
    }
}
