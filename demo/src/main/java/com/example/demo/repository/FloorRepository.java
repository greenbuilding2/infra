package com.example.demo.repository;

import com.example.demo.model.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Repository
public interface FloorRepository extends CrudRepository<Floor, Long> {

    @Transactional
    @Query("select floor from Floor floor where floor.id = :floor_id")
    Floor findFloorByFloorID(@Param("floor_id") long floor_id);


    @Transactional
    @Modifying
    @Query("delete from Floor floor where floor.building_id = :building_id")
    void deleteFloorByBuildingId(@Param("building_id")long building_id);

    @Transactional
    @Query("SELECT floor FROM Floor floor WHERE floor.building_id = :building_id")
    List<Floor> findFloorByBuildingId(@Param("building_id") long building_id);

    @Transactional
    @Query(value="SELECT count(DISTINCT r.id), count(DISTINCT n.id),count(DISTINCT s.id) FROM floor f INNER JOIN room r ON f.id = r.floor_id LEFT JOIN node n ON r.id= n.room_id  LEFT JOIN sensor s ON n.id=s.node_id WHERE f.id = :floor_id", nativeQuery = true)
    List<Object[]> countFloorRoomNodeSensor(@Param("floor_id") long floor_id);


    @Transactional
    @Query(value="SELECT count(id) FROM Sensor sensor WHERE sensor.floor_id = :floor_id AND sensor.building_id = :building_id")
    int countSensorByFloorID(@Param("floor_id") long floor_id, @Param("building_id") long building_id);

    @Transactional
    @Query(value = "SELECT count(type) FROM Sensor sensor WHERE sensor.type = :type AND sensor.floor_id = :floor_id AND sensor.building_id = :building_id")
    int countSensorsByFlooridNType(@Param("floor_id") long floor_id, @Param("building_id") long building_id, @Param("type") String type);

    @Transactional
    @Query(value = "SELECT count(status) FROM Sensor sensor WHERE sensor.status = :status AND sensor.floor_id = :floor_id AND sensor.building_id = :building_id")
    int countSensorsByFlooridNStatus(@Param("floor_id") long floor_id, @Param("building_id") long building_id, @Param("status") String status);

    @Transactional
    @Query(value = "SELECT count(floor_id) FROM Sensor sensor WHERE sensor.status = :status AND sensor.floor_id = :floor_id AND sensor.building_id = :building_id AND sensor.type = :type")
    int countSensorByTypeNStatus(@Param("floor_id") long floor_id, @Param("building_id") long building_id, @Param("status") String status, @Param("type") String type);

}
