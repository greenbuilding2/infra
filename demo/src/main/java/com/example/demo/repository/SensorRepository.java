package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long> {

    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.node_id = :node_id and sensor.cluster_id = :cluster_id")
    void deleteSensorByNodeandClusterId(@Param("node_id")long node_id, @Param("cluster_id") long cluster_id);

    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.cluster_id = :cluster_id")
    void deleteSensorByClusterId(@Param("cluster_id") long cluster_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.node_id = :node_id and sensor.cluster_id = :cluster_id")
    List<Sensor> findSensorByNodeandClusterId(@Param("node_id") long node_id, @Param("cluster_id") long cluster_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.cluster_id = :cluster_id")
    List<Sensor> findSensorByClusterId(@Param("cluster_id") long cluster_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.building_id = :building_id")
    List<Sensor> findSensorByBuildingId(@Param("building_id") long building_id);

    @Transactional
    @Query("SELECT sensor FROM Sensor sensor WHERE sensor.building_id = :building_id AND sensor.floor_number = :floor_number")
    List<Sensor> findSensorByBuildingandFloor(@Param("building_id") long building_id, @Param("floor_number") int floor_number);

    @Transactional
    @Query("SELECT sensor FROM Sensor sensor WHERE sensor.id = :sensor_id AND sensor.node_id = :node_id AND sensor.cluster_id = :cluster_id")
    Sensor findSensorByAllId(@Param("sensor_id") long sensor_id, @Param("node_id") long node_id, @Param("cluster_id") long cluster_id);

    @Transactional
    @Query("select type from Sensor sensor where sensor.id = :sensor_id")
    String getTypeByID(@Param("sensor_id") long sensor_id);

    @Transactional
    @Query(value = "SELECT count(type) FROM Sensor sensor WHERE sensor.type = :type")
    int getNumOfSensorByType(@Param("type") String type);

    @Transactional
    @Query(value = "SELECT count(status) FROM Sensor sensor WHERE sensor.status = :status AND sensor.building_id = :building_id")
    int getNumOfSensorByStatus(@Param("status") String status, @Param("building_id") long building_id);

    @Transactional
    @Query("SELECT install_time FROM Sensor sensor WHERE sensor.floor_id = :floor_id AND sensor.building_id = :building_id ORDER BY install_time DESC")
    List<Date> getInstallTime(@Param("floor_id") long floor_id, @Param("building_id") long building_id);

    @Transactional
    @Query("SELECT last_maintenance FROM Sensor sensor WHERE sensor.floor_id = :floor_id AND sensor.building_id = :building_id ORDER BY last_maintenance DESC")
    List<Date> getMaintenanceTime(@Param("floor_id") long floor_id, @Param("building_id") long building_id);


}