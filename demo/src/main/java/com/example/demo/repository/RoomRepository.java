package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Transactional
    @Modifying
    @Query("delete from Room room where room.building_id = :building_id")
    void deleteRoomByBuildingId(@Param("building_id")long building_id);

    @Transactional
    @Modifying
    @Query("delete from Room room where room.floor_id = :floor_id")
    void deleteRoomByFloorId(@Param("floor_id")long floor_id);

    @Transactional
    @Query("select room from Room room where room.floor_id = :floor_id")
    List<Room> findRoomByFloorId(@Param("floor_id") long floor_id);

}
