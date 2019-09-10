package com.example.convert.contract

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Pattern

data class MoveCommand(
        @field:NotNull
        @field:Pattern(regexp = "^[MRL]+$")
        val command: String) {
}