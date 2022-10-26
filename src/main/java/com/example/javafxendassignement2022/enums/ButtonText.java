package com.example.javafxendassignement2022.enums;

public enum ButtonText {
    ADD_ITEM,
    ADD_MEMBER,
    EDIT_ITEM,
    EDIT_MEMBER;

    @Override
    public String toString() {
        return switch (this) {
            case ADD_ITEM -> "Add item";
            case ADD_MEMBER -> "Add member";
            case EDIT_ITEM -> "Edit item";
            case EDIT_MEMBER -> "Edit member";
        };
    }
}
