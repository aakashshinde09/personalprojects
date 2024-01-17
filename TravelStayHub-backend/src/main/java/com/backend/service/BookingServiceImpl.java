package com.backend.service;

import com.backend.exception.InvalidBookingRequestException;
import com.backend.exception.ResourceNotFoundException;
import com.backend.model.BookedRoom;
import com.backend.model.Room;
import com.backend.repository.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	private final BookingRepo bookingRepo;
	private final RoomService roomService;

	@Override
	public List<BookedRoom> getAllBookings() {
		return bookingRepo.findAll();
	}

	@Override
	public List<BookedRoom> getBookingsByUserEmail(String email) {
		return bookingRepo.findByGuestEmail(email);
	}

	@Override
	public void cancelBooking(Long bookingId) {
		bookingRepo.deleteById(bookingId);
	}

	@Override
	public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
		return bookingRepo.findByRoomId(roomId);
	}

	@Override
	public String saveBooking(Long roomId, BookedRoom bookingRequest) {
		if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check-in date must come before check-out date");
		}
		Room room = roomService.getRoomById(roomId).get();
		List<BookedRoom> existingBookings = room.getBookings();
		boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
		if (roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookingRepo.save(bookingRequest);
		} else {
			throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
		}
		return bookingRequest.getBookingConfirmationCode();
	}

	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		return bookingRepo.findByBookingConfirmationCode(confirmationCode).orElseThrow(
				() -> new ResourceNotFoundException("No booking found with booking code :" + confirmationCode));

	}

	private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
		return existingBookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));
	}

}
