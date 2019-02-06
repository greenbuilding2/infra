package com.example.demo.repository;

import com.example.demo.model.Sensor;
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
    @Query("delete from Sensor sensor where sensor.cluster_id = :cluster_id")
    void deleteSensorByClusterId(@Param("cluster_id") long cluster_id);

    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.node_id = :node_id")
    void deleteSensorByNodeId(@Param("node_id")long node_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.node_id = :node_id")
    List<Sensor> findSensorByNodeId(@Param("node_id") long node_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.cluster_id = :cluster_id")
    List<Sensor> findSensorByClusterId(@Param("cluster_id") long cluster_id);
}