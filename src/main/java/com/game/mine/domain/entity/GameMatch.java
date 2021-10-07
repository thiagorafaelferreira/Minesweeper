package com.game.mine.domain.entity;

import java.util.List;

import javax.persistence.*;

import com.game.mine.domain.entity.enums.Status;
import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="game_match")
public class GameMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_id")
    private String userId;
    @Transient
    @NonNull
    private Field[][] mineField;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @Transient
    @NonNull
    private List<PositionField> positionsMine;
}
