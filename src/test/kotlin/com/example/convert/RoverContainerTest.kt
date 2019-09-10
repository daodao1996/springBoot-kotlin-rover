package com.example.convert

import com.example.convert.contract.Rover
import com.example.convert.contract.RoverContainer
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import testBase.BaseTest


@RunWith(SpringRunner::class)
@SpringBootTest
class RoverContainerTest: BaseTest() {

    @Autowired
    lateinit var roverContainer: RoverContainer

    @Test
    fun should_add_rover_to_map() {
        val rover = Rover("rover1", 2, 4, "E")
        val afterAdd = roverContainer.add(rover)
        assertTrue(afterAdd)
    }

    @Test
    fun should_return_rover() {
        val rover = Rover("rover1", 2, 4, "E")
        roverContainer.add(rover)
        val findRover = roverContainer.getRover("rover1")
        assertNotNull(findRover)
        assertEquals(findRover?.id, "rover1")
        assertEquals(findRover?.x, 2)
        assertEquals(findRover?.direction, "E")
    }

    @Test
    fun should_return_null_if_rover_not_exist() {
        val findRover = roverContainer.getRover("rover111")
        assertNull(findRover)
    }
}