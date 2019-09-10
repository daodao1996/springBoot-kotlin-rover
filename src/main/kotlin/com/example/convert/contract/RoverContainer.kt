package com.example.convert.contract

import org.springframework.stereotype.Component
import kotlin.coroutines.experimental.suspendCoroutine

@Component
class RoverContainer {
    var rovers = mutableMapOf<String, Rover>()

    fun add(rover: Rover): Boolean{
        if(isIdExist(rover.id)){
            return false
        }
        rovers[rover.id] = rover
        return true
    }

    fun isIdExist(id: String): Boolean {
        return rovers.containsKey(id)
    }

    fun getRover(roverId: String): Rover? {
        return rovers[roverId]
    }

    fun updateRover(roverId: String, rover: Rover){
        rovers[roverId] = rover
    }
}
