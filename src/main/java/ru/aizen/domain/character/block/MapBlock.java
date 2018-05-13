package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.dao.WaypointDao;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.data.binary.Binary;
import ru.aizen.domain.data.binary.BinaryReader;
import ru.aizen.domain.data.binary.BinaryWriter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class MapBlock extends DataBlock {
    public static final int OFFSET = 168;
    public static final int SIZE = 9;
    public static final int ORDER = 4;

    private WaypointDao waypointDao;

    private List<String> towns;
    private Difficult activeDifficult;
    private String activeTown;
    private int mapId;

    public MapBlock(WaypointDao waypointDao) {
        super(ORDER);
        this.waypointDao = waypointDao;
    }

    @Override
    public MapBlock parse(ByteReader reader) {
        towns = waypointDao.getTowns();
        setTownAndDifficult(reader.readBytes(3));
        mapId = reader.readInt();
        return this;
    }

    private void setTownAndDifficult(byte[] bytes) {
        byte[] result = new byte[1];
        if (bytes[0] != 0) {
            activeDifficult = Difficult.NORMAL;
            result[0] = bytes[0];
        }
        if (bytes[1] != 0) {
            activeDifficult = Difficult.NIGHTMARE;
            result[0] = bytes[1];
        }
        if (bytes[2] != 0) {
            activeDifficult = Difficult.HELL;
            result[0] = bytes[2];
        }
        BinaryReader reader = new BinaryReader(result);
        int act = reader.readInt(3);
        activeTown = towns.get(act);
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put(getActiveTownAndDifficult())
                .putInt(mapId)
                .putShort((short) 0);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private byte[] getActiveTownAndDifficult() {
        BinaryWriter writer = new BinaryWriter();
        writer.writeInt(towns.indexOf(activeTown), 3);
        Binary active = writer.getBinary();
        active.fillToFullBytes();
        active.setValueAt(7, true);
        byte[] result = new byte[]{0, 0, 0};
        switch (activeDifficult) {
            case NORMAL:
                result[0] = active.toByteArray()[0];
                break;
            case NIGHTMARE:
                result[1] = active.toByteArray()[0];
                break;
            case HELL:
                result[2] = active.toByteArray()[0];
                break;
        }
        return result;
    }

    public Difficult getActiveDifficult() {
        return activeDifficult;
    }

    public void setActiveDifficult(Difficult activeDifficult) {
        this.activeDifficult = activeDifficult;
    }

    public String getActiveTown() {
        return activeTown;
    }

    public void setActiveTown(String activeTown) {
        this.activeTown = activeTown;
    }
}
