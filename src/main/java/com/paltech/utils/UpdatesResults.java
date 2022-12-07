package com.paltech.utils;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.List;

public class UpdatesResults
{
        private static final InfluxDB INFLXUDB = InfluxDBFactory.connect("http://localhost:8086", "isabella.huynh", "1234qwer");
        private static final String DB_NAME = "Automation";
        static {
            INFLXUDB.setDatabase(DB_NAME);
        }
        public static void post(final Point point) {
            INFLXUDB.write((List<String>) point);
        }
}
