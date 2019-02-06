package com.example.demo.repository;

import com.example.demo.model.Cluster;
import com.example.demo.model.Floor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClusterRepository extends CrudRepository<Cluster, Long> {

    @Transactional
    @Query("select cluster from Cluster cluster where cluster.floor_id = :floor_id")
    Cluster findClusterByFloorID(@Param("floor_id") long floor_id);

    @Transactional
    @Query("select cluster from Cluster cluster where cluster.building_id = :building_id")
    List<Cluster> findClusterByBuildingId(@Param("building_id") long building_id);



}
