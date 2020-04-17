package br.alysonsantos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class KitUser {
    private String name;
    private Map<String, Long> kitsInCooldown;
}
