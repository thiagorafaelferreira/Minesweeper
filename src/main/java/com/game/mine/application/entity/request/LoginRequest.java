package com.game.mine.application.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NonNull
    private String username;
    private String password;
}
