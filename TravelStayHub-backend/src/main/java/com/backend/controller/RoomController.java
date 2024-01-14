package com.backend.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.model.Room;
import com.backend.response.RoomResponse;
import com.backend.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
	
	@Autowired
	private RoomService roomService;

	@PostMapping("/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,
			@RequestParam("roomType") String roomType, @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, java.io.IOException, java.io.IOException {

		Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
		
		RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(),savedRoom.getRoomPrice());
		
		return ResponseEntity.ok(response);

	}
	
	@GetMapping("/room/types")
	public List<String> getRoomTypes(){
		return roomService.getAllRoomTypes();
	}
}
