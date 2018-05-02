package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.entity.Waypoint;
import ru.aizen.domain.dao.WaypointDao;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.util.BinaryUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class WaypointsBlock extends DataBlock {
    public static final int OFFSET = 633;
    public static final int SIZE = 81;
    public static final int ORDER = 9;

    private static final byte[] identifier = new byte[]{0x57, 0x53};
    private static final byte[] unknownConst = new byte[]{0x01, 0x00, 0x00, 0x00, 0x50, 0x00};
    private static final byte[] waypointsDataIdentifier = new byte[]{0x02, 0x01};
    private static final int waypointsDataSize = 22;
    private static final int waypointsCount = 39;

    private List<Waypoint> normal;
    private List<Waypoint> nightmare;
    private List<Waypoint> hell;

    private final WaypointDao waypointDao;

    public WaypointsBlock(WaypointDao waypointDao) {
        super(ORDER);
        this.waypointDao = waypointDao;
    }

    @Override
    public WaypointsBlock parse(ByteReader reader) {
        reader.skip(identifier.length + unknownConst.length);
        reader.skip(waypointsDataIdentifier.length);
        normal = getWaypointsForDifficult(Difficult.NORMAL,
                reader.readBytes(waypointsDataSize));
        reader.skip(waypointsDataIdentifier.length);
        nightmare = getWaypointsForDifficult(Difficult.NIGHTMARE,
                reader.readBytes(waypointsDataSize));
        reader.skip(waypointsDataIdentifier.length);
        hell = getWaypointsForDifficult(Difficult.HELL,
                reader.readBytes(waypointsDataSize));
        return this;
    }

    private List<Waypoint> getWaypointsForDifficult(Difficult difficult, byte[] data) {
        List<Waypoint> result = new ArrayList<>();
        String bits = BinaryUtils.getBitString(data, true);
        char[] bitArray = bits.toCharArray();
        for (int i = 0; i < waypointsCount; i++) {
            Waypoint waypoint = waypointDao.getWaypointByPosition(i);
            waypoint.setDifficult(difficult);
            waypoint.setActive(bitArray[i] == '1');
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
                .put(getByteFromWaypoints(normal))
                .put(waypointsDataIdentifier)
                .put(getByteFromWaypoints(nightmare))
                .put(waypointsDataIdentifier)
                .put(getByteFromWaypoints(hell))
                .put((byte) 0x01);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private byte[] getByteFromWaypoints(List<Waypoint> waypoints) {
        char[] array = new char[waypointsCount];
        for (int i = 0; i < waypointsCount; i++) {
            array[i] = waypoints.get(i).isActive() ? '1' : '0';
        }
        StringBuilder bits = new StringBuilder(new String(array));
        int max = waypointsDataSize * Byte.SIZE;
        while (bits.length() < max) {
            bits.append("0");
        }
        return BinaryUtils.fromBinaryString(bits.toString(), true);
    }

    @Override
    public String toString() {
        return "WaypointsBlock{" +
                "normal=" + normal +
                ", nightmare=" + nightmare +
                ", hell=" + hell +
                '}';
    }
}
