package ru.aizen.domain.character.block;

import ru.aizen.domain.character.entity.Waypoint;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

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

    private List<Waypoint> waypoints;

    private byte[] normal;
    private byte[] nightmare;
    private byte[] hell;

    public WaypointsBlock() {
        super(ORDER);
        this.waypoints = new ArrayList<>();
    }

    @Override
    public WaypointsBlock parse(ByteReader reader) {
        reader.skip(identifier.length + unknownConst.length);
        reader.skip(waypointsDataIdentifier.length);
        normal = reader.readBytes(waypointsDataSize);
        reader.skip(waypointsDataIdentifier.length);
        nightmare = reader.readBytes(waypointsDataSize);
        reader.skip(waypointsDataIdentifier.length);
        hell = reader.readBytes(waypointsDataSize);
        return this;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE)
                .put(identifier)
                .put(unknownConst)
                .put(waypointsDataIdentifier)
                .put(normal)
                .put(waypointsDataIdentifier)
                .put(nightmare)
                .put(waypointsDataIdentifier)
                .put(hell)
                .put((byte) 0x01);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }
}
