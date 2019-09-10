package com.example.convert.controller

import com.example.convert.contract.MoveCommand
import com.example.convert.contract.Rover
import com.example.convert.contract.RoverContainer
import com.example.convert.service.RoverService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class RoverController {
    var roverService = RoverService(RoverContainer())

    @PostMapping("/rovers")
    fun addRover(@RequestBody @Valid rover: Rover): ResponseEntity<Rover> {
        val addRoverResult = roverService.addRover(rover)
        if (addRoverResult) {
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }

    @GetMapping("/rovers/{roverId}")
    fun findRover(@PathVariable roverId: String): ResponseEntity<Rover>{
        val findResult = roverService.getRover(roverId) ?:
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK)
                .body(findResult)
    }

    @PatchMapping("/rovers/{roverId}")
    fun moveRover(@PathVariable roverId: String, @RequestBody @Valid command: MoveCommand): ResponseEntity<Rover>{
        val moveResult = roverService.moveRover(roverId, command)
                ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.status(HttpStatus.OK).body(moveResult)
    }
}