package com.game.mine.domain.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mine_field")
@ToString
public class Field {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="user_id")
    private String userId;
    @Column(name="row_position")
    private Integer rowPosition;
    @Column(name="column_position")
    private Integer columnPosition;
    private boolean flaged = false;
    private boolean mine = false;
    private boolean clicked = false;
    @Column(name="mines_arround")
    private Integer minesArround = 0;
}
