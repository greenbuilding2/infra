
package com.example.demo.controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.BeanUtils;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.repository.*;
import com.example.demo.nested.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.ui.Model;
import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

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

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private LoginService loginService;

    /**
     * session
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public String login(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pwd") String pwd,
            HttpServletRequest session
    ) {
        User user = new User();
        user.setUsername(name);
        user.setPsw(pwd);
        System.out.println(name + pwd);

        boolean verify = loginService.verifyLogin(user);
        if (verify) {
            session.setAttribute("userInfo", name + "-" + pwd);
            return "index";
        } else {
            return "redirect:/login";
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping(value = "/loginout")
    public String loginout(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.removeAttribute("userInfo");

        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            return "redirect:/login";
        } else {
            return "index";
        }
    }
*/
    /**
     * Get all buildings
     * */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings")
    public @ResponseBody List<Building> getAllBuildings() {
        return buildingService.getAllBuilding();
    }


    /**
     * Get all sensors
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/sensors")
    public @ResponseBody List<Sensor> getAllSensors() {
        return sensorService.getAllSensor();
    }


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

//    @CrossOrigin(origins = "*")
//    @PostMapping("/buildings/{building_id}/floors")
//    public @ResponseBody
//    String addFloor(@RequestBody Floor floor) {
//        return floorService.saveFloortoDB(floor);
//    }
//
//    @CrossOrigin(origins = "*")
//    @PostMapping("/rooms")
//    public @ResponseBody
//    String addRoom(@RequestBody Room room) {
//        return roomService.saveRoomtoDB(room);
//    }

    @CrossOrigin(origins = "*")
    @PostMapping("/clusters")
    public @ResponseBody
    String addCluster(@RequestBody Cluster cluster) { return clusterService.addClustertoDB(cluster); }

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
     * delete building/floor/room
     * delete cluster/node/sensor
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping("/buildings/delete/{building_id}")
    public @ResponseBody
    void deleteBuilding(
            @PathVariable("building_id") final long building_id) { buildingService.deleteBuilding(building_id); }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/buildings/{building_id}/clusters/delete/{cluster_id}")
    public @ResponseBody
    void deleteCluster(
            @PathVariable final long building_id,
            @PathVariable("cluster_id") final long cluster_id,
            @RequestParam(value = "from", required = false) final String source) {
        clusterService.deleteCluster(cluster_id, building_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/buildings/{building_id}/clusters/{cluster_id}/nodes/delete/{node_id}")
    public @ResponseBody
    void deleteNode(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @PathVariable("node_id") final long node_id,
            @RequestParam(value = "from", required = false) final String source
    ) {
        nodeService.deleteNode(node_id, cluster_id, building_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/buildings/{building_id}/clusters/{cluster_id}/nodes/{node_id}/sensors/delete/{sensor_id}")
    public @ResponseBody
    void deleteSensor(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @PathVariable final long node_id,
            @PathVariable("sensor_id") final long sensor_id,
            @RequestParam(value = "from", required = false) final String source) {
        sensorService.deleteSensor(sensor_id, node_id, cluster_id);
    }
    /**
     * update
     * */

    @CrossOrigin(origins = "*")
    @PutMapping("/buildings/update/{building_id}")
    public @ResponseBody Building updateBuildingByBuildingId(@PathVariable final long building_id,
                                                         @RequestBody Building building) {
        Building newBuilding = buildingService.updateBuildingByBuildingId(building_id, building);
        return newBuilding;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/buildings/{building_id}/clusters/update/{cluster_id}")
    public @ResponseBody void updateClusterByClusterId(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @RequestBody Cluster cluster) {
        Cluster newCluster = clusterService.updateClusterByClusterID(cluster_id, cluster);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/buildings/{building_id}/clusters/{cluster_id}/nodes/update/{node_id}")
    public @ResponseBody void updateNodeByNodeId(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @PathVariable final long node_id,
            @RequestBody Node node) {
        Node newNode = nodeService.updateNodeByNodeId(node_id, cluster_id, node);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/buildings/{building_id}/clusters/{cluster_id}/nodes/{node_id}/sensors/update/{sensor_id}")
    public @ResponseBody void updateSensorBySensorId(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @PathVariable final long node_id,
            @PathVariable final long sensor_id,
            @RequestBody Sensor sensor) {
        Sensor newSensor = sensorService.updateSensorBySensorId(sensor_id, node_id, cluster_id, sensor);

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
    @GetMapping("buildings/{building_id}/floors/floor_plan")
    public @ResponseBody
    Iterable<Sensor> searchSensorByLocation(
            @PathVariable final long building_id,
            @RequestParam final Integer floor_number,
            @RequestParam final String x_coordinate,
            @RequestParam final String y_coordinate) {
        return sensorService.searchSensorByLocation(building_id, floor_number, x_coordinate, y_coordinate);
    }

    /**
     * building nested
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}")
    public @ResponseBody
    String getBuildingByBuildingId(
            @PathVariable final long building_id,
            Model model,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        buildingNested buildingNest = buildingService.getBuildingNestedByBuildingId(building_id, "floor,cluster");
        Map<Integer, Boolean> matchedRes = buildingService.getFloorClusterMatchResult(buildingNest);
        model.addAttribute("matchedRes", matchedRes);
        Cluster cluster = new Cluster();
        model.addAttribute("cluster", cluster);

        if (nestedContent == null)
            return buildingService.getBuildingByBuildingId(building_id).toString();
        else
            return buildingService.getBuildingNestedByBuildingId(building_id, nestedContent).toString();
    }

    /**
     * floor nested
     */
    @CrossOrigin(origins = "*")
    @GetMapping("buildings/{building_id}/floors/{floor_id}")
    public @ResponseBody
    String getFloorByFloorId(
            @PathVariable final long building_id,
            @PathVariable final long floor_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null) {
            Floor floor = floorService.getFloorByFloorId(floor_id, building_id);
            List<Sensor> lists = sensorService.getSensorByFloorId(floor.getFloor_number(), building_id);
            HashSet<String> types = new HashSet<>();
            for (Sensor sensor: lists) {
                String type = sensor.getType().toUpperCase();
                types.add(type);
            }
            List<String> list = new ArrayList<>();
            for (String type: types) {
                list.add(type);
            }
            String s = ",\"Type\": " + String.join(",", list);
            StringBuilder tmp = new StringBuilder(floor.toString());
            tmp.insert(tmp.length()-1, s);
            return tmp.toString();
        }
        else
            return floorService.getFloorNestedByFloorId(floor_id, building_id, nestedContent).toString();
    }

    /**
     * room nested
     */
    @CrossOrigin(origins = "*")
    @GetMapping("buildings/{building_id}/floors/{floor_id}/rooms/{room_id}")
    public @ResponseBody
    String getRoomByRoomId(
            @PathVariable final long building_id,
            @PathVariable final long floor_id,
            @PathVariable final long room_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return roomService.getRoomByRoomId(room_id, floor_id, building_id).toString();
        else
            return roomService.getRoomNestedByRoomId(room_id, nestedContent).toString();

    }

    /**
     * cluster nested
     */
    @CrossOrigin(origins = "*")
    @GetMapping("buildings/{building_id}/clusters/{cluster_id}")
    public @ResponseBody
    String getClusterByClusterId(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return clusterService.getClusterByClusterId(cluster_id);
        else
            return clusterService.getClusterNestedByClusterId(cluster_id, nestedContent);
    }

    /**
     * node nested
     */
    @CrossOrigin(origins = "*")
    @GetMapping("buildings/{building_id}/clusters/{cluster_id}/nodes/{node_id}")
    public @ResponseBody
    String getNodeByNodeId(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id,
            @PathVariable final long node_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent) {
        if (nestedContent == null)
            return nodeService.getNodeByNodeId(node_id, cluster_id).toString();
        else
            return nodeService.getNodeNestedByNodeId(node_id, nestedContent).toString();
    }

    /**
     * get sensor id by cluster id: List

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}/clusters/{cluster_id}/sensors")
    public @ResponseBody
    List<Sensor> getSensorIDByClusterId(
            @PathVariable final long building_id,
            @PathVariable final long cluster_id) {
        return clusterService.getSensorIDByClusterID(cluster_id);
    }*/

    /**
     * get sensor id by node id: List

    @CrossOrigin(origins = "*")
    @GetMapping("/nodes/{node_id}/sensors")
    public @ResponseBody
    List<Long> getSensorIDByNodeId(
            @PathVariable final String node_id) {
        return nodeService.getSensorIDByNodeID(node_id);
    }
     */


    /**
     * find sensor by sensor id
     */
    @CrossOrigin(origins = "*")
    @GetMapping("buildings/{building_id}/sensors/{sensor_id}")
    public @ResponseBody
    Sensor getSensorBySensorId(@PathVariable final long building_id,
                               @PathVariable final long sensor_id) {
        return sensorService.findSensorBySensorId(sensor_id, building_id);
    }

    /**
     * get num of sensors of different types statistic in one building

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/statistics/{building_id}")
    public HashMap<String, Object> getTypeStats(@PathVariable final long building_id) {
        List<Sensor> sensors = sensorRepository.findSensorByBuildingId(building_id);
        Set<String> types = new HashSet<>();
        for (Sensor sensor: sensors) {
            long sensor_id = sensor.getId();
            types.add(sensorRepository.getTypeByID(sensor_id));
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (String type: types) {
            Object num = sensorService.countSensorByType(type);
            map.put(type, num);
        }
        return map;
    }*/


    /**
     * get num of sensors of different status
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/status/statistics/{building_id}")
    public @ResponseBody
    int getStatusStats(@PathVariable final long building_id, @RequestParam final String status) {
        return sensorService.countSensorByStatus(status, building_id);
    }


    /**
     * get num of sensors by floor id
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}/floors/statistics/{floor_id}")
    public int getFloorStats(@PathVariable final long building_id,
                             @PathVariable final long floor_id)
    {
        return floorService.countSensorByFloorID(floor_id, building_id);
    }

    /**
     * get num of sensors of different type on different floor
     */
    public @ResponseBody
    int getNumOfSensorsByFlooridAndType(@PathVariable final long building_id,
                                        @PathVariable final long floor_id,
                                        @RequestParam final String type) {
        return floorService.countSensorsByFlooridNType(floor_id, building_id, type);
    }

    /**
     * Get num of sensors of different status on different floor
     */
    public @ResponseBody
    int getNumOfSensorsByFlooridAndStatus(@PathVariable final long building_id,
                                          @PathVariable final long floor_id,
                                          @RequestParam final String status) {
        return floorService.countSensorsByFlooridNStatus(floor_id, building_id, status);
    }

    /**
     * Get the latest install time
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}/floors/latestInstall/{floor_id}")
    public Date latestInstallTime(@PathVariable final long building_id,
                                  @PathVariable final long floor_id) {
        return sensorService.latestInstallTime(floor_id, building_id);
    }

    /**
     * Get the latest maintenance time
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}/floors/latestMaintenance/{floor_id}")
    public Date latestMaintenanceTime(@PathVariable final long building_id,
                                      @PathVariable final long floor_id) {
        return sensorService.latestMaintenanceTime(floor_id, building_id);
    }


    /**
     * get all statistic: BUILDING IOT DASHBOARD
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/statistics/{building_id}")
    public @ResponseBody
    Iterable<List<String>> buildingStatistic(@PathVariable final long building_id,
                                         @RequestParam final String sensor_type) {
        List<List<String>> res = new ArrayList<>();
        List<String> list;
        // list includes floor number, number of sensors, sensor type, status, install time and maintenance time
        Building building = buildingService.getBuildingByBuildingId(building_id);
        List<Floor> floorList= floorService.getFloorByBuildingID(building_id);
        if (sensor_type.equals("ALL")) {
            for (int i = 0; i < Integer.valueOf(building.getNum_of_floors()); i++) {
                Floor floor = floorList.get(i);
                list = new ArrayList<>();
                list.add("Floor " + String.valueOf(floor.getFloor_number()));
                int numOfSensor = floorService.countSensorByFloorID(floor.getId(), floor.getBuilding_id());
                list.add(String.valueOf(numOfSensor));
                int open, closed, maintain;
                open = floorService.countSensorsByFlooridNStatus(floor.getId(), building_id, "open");
                closed = floorService.countSensorsByFlooridNStatus(floor.getId(), building_id, "closed");
                maintain = floorService.countSensorsByFlooridNStatus(floor.getId  (), building_id, "maintaining");
                list.add(String.valueOf(open));
                list.add(String.valueOf(closed));
                list.add(String.valueOf(maintain));
                Date install = sensorService.latestInstallTime(floor.getId(), building_id);
                Date maintainTime = sensorService.latestMaintenanceTime(floor.getId(),building_id);
                if (install == null) list.add("0000-00-00 00:00:00");
                else list.add(String.valueOf(install));
                if (maintainTime == null) list.add("0000-00-00 00:00:00");
                else list.add(String.valueOf(maintainTime));
                res.add(list);
            }
        } else {
            List<Sensor> sensors = sensorService.findSensorByBuildingID(building_id);
            HashSet<String> set = new HashSet<>();
            for (Sensor sensor: sensors) {
                set.add(sensor.getType().toUpperCase());
            }
            if (!set.contains(sensor_type)) return new ArrayList<>();
            else {
                for (int i = 0; i < Integer.valueOf(building.getNum_of_floors()); i++) {
                    Floor floor = floorList.get(i);
                    list = new ArrayList<>();
                    list.add("Floor " + String.valueOf(floor.getFloor_number()));
                    int numOfSensor = floorService.countSensorsByFlooridNType(floor.getId(), floor.getBuilding_id(),sensor_type);
                    list.add(String.valueOf(numOfSensor));
                    int open, closed, maintain;
                    open = floorService.countSensorsByTypeNStatus(floor.getId(), building_id, sensor_type, "open");
                    closed = floorService.countSensorsByTypeNStatus(floor.getId(), building_id, sensor_type, "closed");
                    maintain = floorService.countSensorsByTypeNStatus(floor.getId(), building_id, sensor_type, "maintaining");
                    list.add(String.valueOf(open));
                    list.add(String.valueOf(closed));
                    list.add(String.valueOf(maintain));
                    Date install = sensorService.latestInstallTime(floor.getId(), building_id);
                    Date maintainTime = sensorService.latestMaintenanceTime(floor.getId(), building_id);
                    if (install == null) list.add("0000-00-00 00:00:00");
                    else list.add(String.valueOf(install));
                    if (maintainTime == null) list.add("0000-00-00 00:00:00");
                    else list.add(String.valueOf(maintainTime));
                    res.add(list);
                }
            }
        }
        return res;
    }
}
