package com.game.mine.application.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParametersNewGameRequest {
    private Integer numberRows;
    private Integer numberColumns;
    private Integer numberMinesOnField;
    private boolean reset;

    public Integer totalArea() {
        Integer totalArea = 0;
        if (numberRows != null && numberRows > 0 && numberColumns != null && numberColumns > 0 )
            totalArea = numberRows * numberColumns;
        return totalArea;
    }
}
