package com.example.demo.repository;

import com.example.demo.model.Node;
import com.example.demo.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface NodeRepository extends CrudRepository <Node, Long> {
    @Transactional
    @Modifying
    @Query("delete from Node node where node.cluster_id = :cluster_id")
    void deleteNodeByClusterId(@Param("cluster_id")long cluster_id);

    @Transactional
    @Query("select node from Node node where node.room_id = :room_id")
    Node findNodeByRoomId(@Param("room_id") long room_id);

    @Transactional
    @Query("select node from Node node where node.cluster_id = :cluster_id")
    List<Node> findNodeByClusterId(@Param("cluster_id") long cluster_id);
}
