package com.example.convert.service

import com.example.convert.contract.Direction
import com.example.convert.contract.MoveCommand
import com.example.convert.contract.Rover
import com.example.convert.contract.RoverContainer
import org.springframework.stereotype.Component

@Component
class RoverService(val roverContainer: RoverContainer) {
    val directionList = listOf("N", "W", "S", "E")

    fun addRover(rover: Rover): Boolean {
        return roverContainer.add(rover)
    }

    fun getRover(roverId: String): Rover? {
        return roverContainer.getRover(roverId)
    }

    fun moveRover(roverId: String, command: MoveCommand): Rover?{
        var rover: Rover? = roverContainer.getRover(roverId) ?: return null

        command.command.forEach{
            rover = if('M'.equals(it)){
                changeIndex(rover!!)
            }else {
                changeDirection(rover!!, it)
            }
        }

        return rover
    }

    fun changeIndex(rover: Rover): Rover?{
        return when(rover.direction){
            "E" -> return Direction.E.move(rover)
            "W" -> return Direction.W.move(rover)
            "N" -> return Direction.N.move(rover)
            "S" -> return Direction.S.move(rover)
            else -> null
        }
    }

    fun changeDirection(rover:Rover, command: Char): Rover {
        val now = directionList.indexOf(rover.direction)
        if("L".equals(command)){
            rover.direction = directionList[(now + 1) % 4]
        }else {
            rover.direction = directionList[(now + 3) % 4]
        }
        return rover
    }
}
