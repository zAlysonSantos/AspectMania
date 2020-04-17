package br.alysonsantos.system.teleport.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teleport {
    private String sender;
    private String target;
    private long cooldown;
}
