package com.backend.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.model.Room;
import com.backend.repository.RoomRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
	
	private final RoomRepo roomRepo;

	@Override
	public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
		
		Room room = new Room();
		room.setRoomType(roomType);
		room.setRoomPrice(roomPrice);
		if(!file.isEmpty()) {
			byte[] photoBytes = file.getBytes();
			
			Blob photoBlob = new SerialBlob(photoBytes);
			room.setPhoto(photoBlob);
		}
		return roomRepo.save(room);
	}
	
	

}
