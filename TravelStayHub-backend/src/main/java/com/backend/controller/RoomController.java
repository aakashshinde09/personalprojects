package com.backend.controller;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.model.Room;
import com.backend.response.RoomResponse;
import com.backend.service.RoomService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
	
	private RoomService roomService;

	@PostMapping("/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,
			@RequestParam("roomType") String roomType, @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException, java.io.IOException {

		Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
		
		RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(),savedRoom.getRoomPrice());
		
		return ResponseEntity.ok(response);

	}
}
