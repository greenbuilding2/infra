
package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.repository.*;
import com.example.demo.nested.*;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    @Autowired
    private roomService roomService;
    @Autowired
    private floorService floorService;
    @Autowired
    private buildingService buildingService;

    @Autowired
    private clusterService clusterService;
    @Autowired
    private nodeService nodeService;
    @Autowired
    private sensorService sensorService;

    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;


    /**
     * Add building, floor, room;
     * Add cluster, node, sensor;
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/buildings")
    public @ResponseBody
    String addBuilding(@RequestBody Building building) {
        return buildingService.saveBuildingToDB(building);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/floors")
    public @ResponseBody
    String addFloor(@RequestBody Floor floor) {
        return floorService.saveFloortoDB(floor);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/rooms")
    public @ResponseBody
    String addRoom(@RequestBody Room room) {
        return roomService.saveRoomtoDB(room);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/clusters")
    public @ResponseBody
    String addCluster(@RequestBody Cluster cluster) {

        return clusterService.addClustertoDB(cluster);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/nodes")
    public @ResponseBody
    String addNode(@RequestBody Node node) {
        return nodeService.addNodetoDB(node);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/sensors")
    public @ResponseBody
    String addSensor(@RequestBody Sensor sensor) {
        return sensorService.addSensortoDB(sensor);
    }

    /**
     * search geocode
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/geocode")
    public @ResponseBody
    Iterable<Building> searchBuildingByLa(
            @RequestParam final String latitude,
            @RequestParam final String longitude,
            @RequestParam(required = false) Integer radius) {
        return buildingService.searchBuildingByLa(latitude, longitude, radius);
    }

    public @ResponseBody
    Iterable<Building> searchBuildingByCity(
            @RequestParam final String city,
            @RequestParam final String state,
            @RequestParam final String zipcode) {
        return buildingService.searchBuildingByCity(city, state, zipcode);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/location")
    public @ResponseBody
    Iterable<Building> searchBuidlingByLocation(
            @RequestParam(required = false) final String city,
            @RequestParam(required = false) final String state,
            @RequestParam(required = false) final String zipcode) {
        return buildingService.searchBuildingByCity(city, state, zipcode);
    }

    /**
     * find sensor on floor plan
     */
    @CrossOrigin(origins = "*")
    @GetMapping("floors/search/floor_plan")
    public @ResponseBody
    Sensor searchSensorByLocation(
            @RequestParam final Double x_coordinate,
            @RequestParam final Double y_coordinate) {
        return sensorService.searchSensorByLocation(x_coordinate, y_coordinate);
    }

    /**
     * delete building/floor/room
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "building_id")
    public @ResponseBody
    void deleteBuilding(
            @PathVariable("building_id") final long building_id) {
        buildingService.deleteBuilding(building_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "floor_id")
    public @ResponseBody
    void deleteFloor(
            @PathVariable("floor_id") final long floor_id) {
        floorService.deleteFloor(floor_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "room_id")
    public @ResponseBody
    void deleteRoom(
            @PathVariable("room_id") final long room_id) {
        roomService.deleteRoom(room_id);
    }

    /**
     * delete cluster/node/sensor
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/clusters/{cluster_id}")
    public @ResponseBody
    void deleteCluster(
            @PathVariable("cluster_id") final long cluster_id,
            @RequestParam(value = "from", required = false) final String source) {
        clusterService.deleteCluster(cluster_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/nodes/{node_id}")
    public @ResponseBody
    void deleteNode(
            @PathVariable("node_id") final long node_id,
            @RequestParam(value = "from", required = false) final String source
    ) {
        nodeService.deleteNode(node_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/sensors/{sensor_id}")
    public @ResponseBody
    void deleteSensor(
            @PathVariable("sensor_id") final long sensor_id,
            @RequestParam(value = "from", required = false) final String source) {
        sensorService.deleteSensor(sensor_id);
    }
    /**
     * get last maintenance time
     */
    
    /**
     * fetch_nested
     */


    /**
     * building
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}")
    public @ResponseBody
    String getBuildingByBuildingId(
            @PathVariable final long building_id,
            Model model,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        buildingNested buildingNest = buildingService.getBuildingNestedByBuildingId(building_id, "floor,cluster");
        Map<Integer, Boolean> matchedRes = buildingService.getFloorCluterMatchResult(buildingNest);
        model.addAttribute("matchedRes", matchedRes);
        Cluster cluster = new Cluster();
        model.addAttribute("cluster", cluster);

        if (nestedContent == null)
            return buildingService.getBuildingByBuildingId(building_id).toString();
        else
            return buildingService.getBuildingNestedByBuildingId(building_id, nestedContent).toString();
    }

    /**
     * floor
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/floors/{floor_id}")
    public @ResponseBody
    String getFloorByFloorId(
            @PathVariable final long floor_id,
            Model model,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return floorService.getFloorByFloorId(floor_id).toString();
        else
            return floorService.getFloorNestedByFloorId(floor_id, nestedContent).toString();
    }

    /**
     * room
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/rooms/{room_id}")
    public @ResponseBody
    String getRoomByRoomId(
            @PathVariable final long room_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return roomService.getRoomByRoomId(room_id).toString();
        else
            return roomService.getRoomNestedByRoomId(room_id, nestedContent).toString();

    }

    /**
     * cluster
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/clusters/{cluster_id}")
    public @ResponseBody
    String getClusterByClusterId(
            @PathVariable final long cluster_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return clusterService.getClusterByClusterId(cluster_id);
        else
            return clusterService.getClusterNestedByClusterId(cluster_id, nestedContent);
    }

    /**
     * node
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/nodes/{node_id}")
    public @ResponseBody
    String getNodeByNodeId(
            @PathVariable final long node_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return nodeService.getNodeByNodeId(node_id).toString();
        else
            return nodeService.getNodeNestedByNodeId(node_id, nestedContent).toString();
    }


    /**
     * get sensor id by cluster id: List
     *
     * @param cluster_id
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/clusters/{cluster_id}/sensors")
    public @ResponseBody
    List<Long> getSensorIDByClusterId(
            @PathVariable final String cluster_id) {
        return clusterService.getSensorIDByClusterID(cluster_id);
    }

    /**
     * get sensor id by node id: List
     *
     * @param node_id
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/nodes/{node_id}/sensors")
    public @ResponseBody
    List<Long> getSensorIDByNodeId(
            @PathVariable final String node_id) {
        return nodeService.getSensorIDByNodeID(node_id);
    }


    /**
     * find sensor by sensor id
     *
     * @param sensor_id
     * @return
     */

    @CrossOrigin(origins = "*")
    @GetMapping("sensors/{sensor_id}")
    public @ResponseBody
    String getSensorBySensorId(@PathVariable final String sensor_id) {
        return sensorService.findSensorBySensorId(sensor_id);
    }

    /**
     * statistic
     */

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/statistics/{building_id}")
    public HashMap<String, Object> getBuildingStats(@PathVariable final long building_id)
    {
        List<Object[]> a = buildingService.countBuildingClustersAndNodes(building_id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        Object[] count = a.get(0);
        map.put("cluster_count", count[0]);
        map.put("node_count", count[1]);
        map.put("sensor_count", count[2]);
        return map;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/floors/statistics/{floor_id}")
    public HashMap<String, Object> getFloorStats(@PathVariable final long floor_id)
    {
        List<Object[]> a = floorService.countFloorRoomNodeSensor(floor_id);
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        Object[] count = a.get(0);
        hmap.put("room_count", count[0]);
        hmap.put("node_count", count[1]);
        hmap.put("sensor_count", count[2]);
        return hmap;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/rooms/statistics/{room_id}")
    public HashMap<String, Object> getRoomStats(@PathVariable final long room_id)
    {
        List<Object[]> a = roomService.countNodeSensor(room_id);
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        Object[] count = a.get(0);
        hmap.put("node_count", count[0]);
        hmap.put("sensor_count", count[1]);
        return hmap;
    }



}
