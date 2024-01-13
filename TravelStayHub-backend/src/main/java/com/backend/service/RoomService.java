package com.backend.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.backend.model.Room;

import io.jsonwebtoken.io.IOException;

public interface RoomService {

	Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException, java.io.IOException ;

}
