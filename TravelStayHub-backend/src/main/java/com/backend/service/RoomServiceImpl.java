package com.backend.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.exception.ResourceNotFoundException;
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

	@Override
	public List<String> getAllRoomTypes() {
		
		return roomRepo.findDistinctRoomTypes();
	}

	@Override
	public List<Room> getAllRooms() {
		// TODO Auto-generated method stub
		return roomRepo.findAll();
	}

	@Override
	public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
		Optional<Room> theRoom = roomRepo.findById(roomId);
		if(theRoom.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, Room not Found....");
		}
		Blob photoBlob = theRoom.get().getPhoto();
		if(photoBlob != null) {
			return photoBlob.getBytes(1, (int) photoBlob.length());
		}
		return null;
	}
	
	

}
