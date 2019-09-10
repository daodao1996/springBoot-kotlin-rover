package com.example.convert.contract

import org.jetbrains.annotations.NotNull
import org.springframework.format.annotation.NumberFormat
import javax.validation.constraints.Pattern

data class Rover(
        @field:NotNull
        @field:Pattern(regexp = "^[A-Za-z0-9]+$")
        var id: String,
        @field:NumberFormat
        @field:NotNull
        var x: Int,
        @field:NumberFormat
        @field:NotNull
        var y: Int,
        @field:NotNull
        @field:Pattern(regexp = "^[EWSN]$")
        var direction: String) {

}
