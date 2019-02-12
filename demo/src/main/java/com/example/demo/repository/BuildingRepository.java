package com.example.demo.repository;

import com.example.demo.model.Building;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Repository
public interface BuildingRepository extends CrudRepository<Building, Long> {

    @Transactional
    @Query(value="SELECT count(DISTINCT c.id), count(DISTINCT n.id), count(DISTINCT s.id) FROM building b INNER JOIN cluster c ON b.id = c.building_id LEFT JOIN node n ON c.id= n.cluster_id LEFT JOIN sensor s ON n.id=s.node_id WHERE b.id = :building_id", nativeQuery = true)
    List<Object[]> countBuildingClustersAndNodes(@Param("building_id") long building_id);

}
