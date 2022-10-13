package com.kualkua.gangcompetition.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StravaAthlete {

    private int id;
    private String username;
}
