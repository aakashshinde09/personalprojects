package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.model.BookedRoom;


public interface BookingService {

	List<BookedRoom> getAllBookingsByRoomId(Long roomId);

}
