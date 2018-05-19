package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.entity.Waypoint;
import ru.aizen.domain.dao.WaypointDao;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.data.binary.Binary;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WaypointsBlock extends DataBlock {
    public static final int OFFSET = 633;
    public static final int SIZE = 81;
    public static final int ORDER = 8;

    private static final byte[] identifier = new byte[]{0x57, 0x53};
    private static final byte[] unknownConst = new byte[]{0x01, 0x00, 0x00, 0x00, 0x50, 0x00};
    private static final byte[] waypointsDataIdentifier = new byte[]{0x02, 0x01};
    private static final int waypointsDataSize = 22;
    private static final int waypointsCount = 39;

    private Map<Difficult, List<Waypoint>> waypoints;

    private final WaypointDao waypointDao;

    public WaypointsBlock(WaypointDao waypointDao) {
        super(ORDER);
        this.waypoints = new HashMap<>();
        this.waypointDao = waypointDao;
    }

    @Override
    public WaypointsBlock parse(ByteReader reader) {
        reader.skip(identifier.length + unknownConst.length);
        reader.skip(waypointsDataIdentifier.length);
        waypoints.put(Difficult.NORMAL, getWaypoints(reader.readBytes(waypointsDataSize)));
        reader.skip(waypointsDataIdentifier.length);
        waypoints.put(Difficult.NIGHTMARE, getWaypoints(reader.readBytes(waypointsDataSize)));
        reader.skip(waypointsDataIdentifier.length);
        waypoints.put(Difficult.HELL, getWaypoints(reader.readBytes(waypointsDataSize)));
        return this;
    }

    private List<Waypoint> getWaypoints(byte[] data) {
        List<Waypoint> result = new ArrayList<>();
        Binary binary = new Binary(data);
        for (int i = 0; i < waypointsCount; i++) {
            Waypoint waypoint = waypointDao.getWaypointByPosition(i);
            waypoint.setActive(binary.get(i));
            result.add(waypoint);
        }
        return result;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE)
                .put(identifier)
                .put(unknownConst)
                .put(waypointsDataIdentifier)
                .put(getByteFromWaypoints(Difficult.NORMAL))
                .put(waypointsDataIdentifier)
                .put(getByteFromWaypoints(Difficult.NIGHTMARE))
                .put(waypointsDataIdentifier)
                .put(getByteFromWaypoints(Difficult.HELL))
                .put((byte) 0x01);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private byte[] getByteFromWaypoints(Difficult difficult) {
        Boolean[] array = new Boolean[waypointsCount];
        waypoints.get(difficult).stream()
                .map(Waypoint::isActive)
                .collect(Collectors.toList())
                .toArray(array);
        Binary binary = new Binary(waypointsDataSize * Byte.SIZE);
        for (int i = 0; i < array.length; i++) {
            binary.set(i, array[i]);
        }
        return binary.array();
    }

    public List<Waypoint> getWaypoints(Difficult difficult) {
        return waypoints.get(difficult);
    }

    public void setWaypoints(List<Waypoint> waypoints, Difficult difficult) {
        this.waypoints.put(difficult, waypoints);
    }
}
