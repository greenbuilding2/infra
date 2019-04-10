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
    @Query("SELECT room FROM Room room WHERE room.floor_id = :floor_id AND room.building_id = :building_id")
    List<Room> findRoomByFloorId(@Param("floor_id") long floor_id, @Param("building_id") long building_id);

    @Transactional
    @Query(value="SELECT count(DISTINCT n.id),count(DISTINCT s.id) FROM room r INNER JOIN node n ON r.id = n.room_id LEFT JOIN sensor s ON n.id=s.node_id WHERE r.id = :room_id", nativeQuery = true)
    List<Object[]> countNodeSensor(@Param("room_id") long room_id);

}
