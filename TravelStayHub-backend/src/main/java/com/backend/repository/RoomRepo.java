package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.Room;

public interface RoomRepo extends JpaRepository<Room, Long> {

}
