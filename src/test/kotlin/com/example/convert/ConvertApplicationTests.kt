package com.example.convert

import com.example.convert.contract.MoveCommand
import com.example.convert.contract.Rover
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import testBase.BaseTest

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConvertApplicationTests : BaseTest() {
	@Autowired
	lateinit var mockMvc: MockMvc

	@Test
	fun should_status_200_when_add_rover_success() {
		val rover = Rover("rover10", 2, 4, "E")

		val roverString = ObjectMapper().writeValueAsString(rover)
		mockMvc.perform(post("/api/rovers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(roverString))
				.andExpect(status().isCreated)
	}

	@Test
	fun should_status_400_when_rover_info_illegal() {
		val rover = Rover("rover1", 2, 4, "NW")

		val roverString = ObjectMapper().writeValueAsString(rover)
		mockMvc.perform(post("/api/rovers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(roverString))
				.andExpect(status().isBadRequest)
	}

	@Test
	fun should_status_409_when_rover_id_already_exist() {
		val rover = Rover("rover1", 2, 4, "N")
		val rover1 = Rover("rover1", 2, 4, "W")

		val roverString = ObjectMapper().writeValueAsString(rover)
		val rover1String = ObjectMapper().writeValueAsString(rover1)

		mockMvc.perform(post("/api/rovers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(roverString))
		mockMvc.perform(post("/api/rovers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(rover1String))
				.andExpect(status().isConflict)
	}

	@Test
	fun should_status_200_and_return_rover() {
		val rover = Rover("rover15", 2, 4, "N")
		val roverString = ObjectMapper().writeValueAsString(rover)
		mockMvc.perform(post("/api/rovers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(roverString))

		mockMvc.perform(get("/api/rovers/rover15"))
				.andExpect(status().isOk)
				.andExpect(jsonPath("$.id").value("rover15"))
				.andExpect(jsonPath("$.x").value(2))
				.andExpect(jsonPath("$.direction").value("N"))
	}

	@Test
	fun should_status_404_when_rover_not_exist() {
		mockMvc.perform(get("/api/rovers/rover20"))
				.andExpect(status().isNotFound)
	}

	@Test
	fun should_status_200_when_rover_move_successful() {
		val rover = Rover("rover220", 1, 2, "N")
		val moveCommand = MoveCommand("LMLMLMLMMRM")
		val roverString = ObjectMapper().writeValueAsString(rover)
		val moveCommandSring = ObjectMapper().writeValueAsString(moveCommand)
		mockMvc.perform(post("/api/rovers")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(roverString))

		mockMvc.perform(patch("/api/rovers/rover220")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(moveCommandSring))
				.andExpect(status().isOk)
				.andExpect(jsonPath("$.id").value("rover220"))
				.andExpect(jsonPath("$.x").value(2))
				.andExpect(jsonPath("$.y").value(3))
				.andExpect(jsonPath("$.direction").value("E"))
	}

	@Test
	fun should_status_400_when_command_illegal() {
		val moveCommand = MoveCommand("LSO")
		val moveCommandString = ObjectMapper().writeValueAsString(moveCommand)
		mockMvc.perform(patch("/api/rovers/rover220")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(moveCommandString))
				.andExpect(status().isBadRequest)
	}

	@Test
	fun should_status_404_when_rover_not_found() {
		val moveCommand = MoveCommand("LML")
		val moveCommandString = ObjectMapper().writeValueAsString(moveCommand)
		mockMvc.perform(patch("/api/rovers/rover100")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(moveCommandString))
				.andExpect(status().isNotFound)
	}
}
