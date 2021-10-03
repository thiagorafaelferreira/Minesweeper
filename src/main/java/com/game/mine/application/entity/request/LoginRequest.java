package com.game.mine.application.entity.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequest {
    @NonNull
    private String username;
    private String password;
}
