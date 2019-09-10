package com.example.convert

import com.example.convert.contract.MoveCommand
import com.example.convert.contract.Rover
import com.example.convert.contract.RoverContainer
import com.example.convert.service.RoverService
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import testBase.BaseTest


@RunWith(SpringRunner::class)
@SpringBootTest
class RoverServiceTest: BaseTest(){
    @Test
    fun should_add_rover() {
        val rover = Rover("rover1", 2, 4, "E")
        val roverContainer = mock(RoverContainer::class.java)
        Mockito.`when`(roverContainer.add(any(Rover::class.java)))
                .thenReturn(true)
        val roverService = RoverService(roverContainer)

        val result = roverService.addRover(rover)

        assertTrue(result)
    }

    @Test
    fun should_return_false_when_add_rover_failed() {
        val rover = Rover("rover2", 2, 4, "E")
        val roverContainer = mock(RoverContainer::class.java)
        Mockito.`when`(roverContainer.add(any(Rover::class.java)))
                .thenReturn(false)
        val roverService = RoverService(roverContainer)

        val result = roverService.addRover(rover)

        assertEquals(false, result)
    }

    @Test
    fun should_return_rover_when_rover_exist() {
        val rover = Rover("rover2", 2, 4, "E")
        val roverContainer = mock(RoverContainer::class.java)
        Mockito.`when`(roverContainer.getRover(ArgumentMatchers.anyString()))
                .thenReturn(rover)
        val roverService = RoverService(roverContainer)

        val result = roverService.getRover("rover2")
        assertNotNull(result)
    }

    @Test
    fun should_return_null_when_rover_not_exist() {
        val roverContainer = mock(RoverContainer::class.java)
        Mockito.`when`(roverContainer.getRover(ArgumentMatchers.anyString()))
                .thenReturn(null)
        val roverService = RoverService(roverContainer)

        val result = roverService.getRover("rover")
        assertNull(result)
    }

    @Test
    fun should_move_rover() {
        val rover = Rover("rover222", 1, 2, "N")
        val roverService = RoverService(RoverContainer())
        roverService.addRover(rover)
        val moved = roverService.moveRover("rover222", MoveCommand("LMLMLMLMMRM"))
        assertEquals("rover222", moved?.id)
        assertEquals(2, moved?.x)
        assertEquals(3, moved?.y)
        assertEquals("E", moved?.direction)
    }
}