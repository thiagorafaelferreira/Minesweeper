package com.game.mine.application.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ParametersNewGameRequest {
    private Integer numberRows;
    private Integer numberColumns;
    private Integer numberMinesOnField;

    public Integer totalArea() {
        Integer totalArea = 0;
        if (numberRows != null && numberRows > 0 && numberColumns != null && numberColumns > 0 )
            totalArea = numberRows * numberColumns;
        return totalArea;
    }
}
