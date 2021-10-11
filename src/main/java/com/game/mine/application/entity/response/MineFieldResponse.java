package com.game.mine.application.entity.response;

import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MineFieldResponse {
    private Field mineField[][];
    private Status status;
}
