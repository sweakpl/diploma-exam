package com.sweak.diplomaexam.domain.model.common

enum class Grade(val stringRepresentation: String, val floatRepresentation: Float) {
    A("5.0 (A)", 5.0f),
    B("4.5 (B)", 4.5f),
    C("4.0 (C)", 4.0f),
    D("3.5 (D)", 3.5f),
    E("3.0 (E)", 3.0f),
    F("2.0 (F)", 2.0f);

    companion object {
        fun fromFloat(floatRepresentation: Float): Grade =
            values().first { it.floatRepresentation == floatRepresentation }
    }
}