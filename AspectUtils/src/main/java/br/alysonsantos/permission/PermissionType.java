package br.alysonsantos.permission;

import lombok.Getter;

@Getter
public enum PermissionType {

    VIP("rankup.vip"),
    MEMBRO("rankup.membro"),
    AJUDANTE("rankup.ajudante"),
    MODERADOR("rankup.ajudante"),
    ADMINISTRADOR("rankup.administrador"),
    GERENTE("rankup.gerente"),

    ADMIN("rankup.admin"),
    STAFF("rankup.staff");

    private String permisison;

    PermissionType(String permission) {
        this.permisison = permission;
    }
}
