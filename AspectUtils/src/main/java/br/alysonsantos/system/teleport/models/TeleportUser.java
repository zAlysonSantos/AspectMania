package br.alysonsantos.system.teleport.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeleportUser {
    private String name;
    private Teleport teleport;
}
