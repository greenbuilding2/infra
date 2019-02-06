package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;

@Service
public class sensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public String findSensorBySensorId(String sensorId)
    {
        long sensor_id = Long.valueOf(sensorId).longValue();
        return sensorRepository.findById(sensor_id).get().toString();
    }

    public Sensor updateSensorBySensorId(String sensorID, Sensor sensor)
    {
        long sensor_id = Long.valueOf(sensorID).longValue();
        Sensor sensorFromDB = sensorRepository.findById(sensor_id).get();

        if(sensor.getName() != null) {
            sensorFromDB.setName(sensor.getName());
        }

        if(sensor.getStatus() != null) {
            sensorFromDB.setStatus(sensor.getStatus());
        }

        sensorRepository.save(sensorFromDB);
        return sensorFromDB;
    }

    public String addSensortoDB(Sensor sensor){
        sensorRepository.save(sensor);
        return sensor.toString();
    }

    public void deleteSensor(long sensor_id){
        Long sensorId = Long.valueOf(sensor_id).longValue();
        Iterable<Sensor> sensors = sensorRepository.findAll();
        for(Sensor sensor: sensors){
            if(sensorId == sensor.getId()) {
                sensorRepository.deleteById(sensorId);
            }
        }
    }
}
