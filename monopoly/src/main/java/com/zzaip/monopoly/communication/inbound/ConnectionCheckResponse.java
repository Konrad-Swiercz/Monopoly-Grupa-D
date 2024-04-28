package com.zzaip.monopoly.communication.inbound;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionCheckResponse {
    private boolean isConnectedToActiveGame;
}
