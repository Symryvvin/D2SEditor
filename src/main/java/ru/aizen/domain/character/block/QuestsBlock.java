package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Difficult;
import ru.aizen.domain.character.entity.Act;
import ru.aizen.domain.character.entity.Quest;
import ru.aizen.domain.dao.QuestDao;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;
import ru.aizen.domain.data.binary.Binary;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestsBlock extends DataBlock {
    public static final int OFFSET = 335;
    public static final int SIZE = 298;
    public static final int ORDER = 7;
    private static final int QUEST_DATA_SIZE = 96;
    private static final int ACT_QUEST_DATA_SIZE = 12;
    private static final int QUEST_BY_ACT = 6;

    private final byte[] identifier = new byte[]{0x57, 0x6F, 0x6F, 0x21};
    private final byte[] unknown1 = new byte[]{0x06, 0x00, 0x00, 0x00, 0x2A, 0x01};
    private final byte[] unknown2 = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private Map<Difficult, List<Quest>> quests;

    private Map<Difficult, Map<Act, Short>> introduce;
    private Map<Difficult, Map<Act, Short>> travel;

    private final QuestDao questDao;

    public QuestsBlock(QuestDao questDao) {
        super(ORDER);
        this.questDao = questDao;
        quests = new HashMap<>();
        introduce = new HashMap<>();
        travel = new HashMap<>();
    }

    @Override
    public QuestsBlock parse(ByteReader reader) {
        reader.skip(identifier.length + unknown1.length);
        readQuestsByDifficult(reader.readBytes(QUEST_DATA_SIZE), Difficult.NORMAL);
        readQuestsByDifficult(reader.readBytes(QUEST_DATA_SIZE), Difficult.NIGHTMARE);
        readQuestsByDifficult(reader.readBytes(QUEST_DATA_SIZE), Difficult.HELL);
        return this;
    }

    private void readQuestsByDifficult(byte[] data, Difficult difficult) {
        List<Quest> result = new ArrayList<>();
        Map<Act, Short> difficultIntroduce = new HashMap<>();
        Map<Act, Short> difficultTravel = new HashMap<>();
        ByteReader questReader = new ByteReader(data, ByteOrder.LITTLE_ENDIAN);
        difficultIntroduce.put(Act.ACT1, questReader.readShort());
        result.addAll(readActQuests(questReader, Act.ACT1));
        difficultTravel.put(Act.ACT2, questReader.readShort());
        difficultIntroduce.put(Act.ACT2, questReader.readShort());
        result.addAll(readActQuests(questReader, Act.ACT2));
        difficultTravel.put(Act.ACT3, questReader.readShort());
        difficultIntroduce.put(Act.ACT3, questReader.readShort());
        result.addAll(readActQuests(questReader, Act.ACT3));
        difficultTravel.put(Act.ACT4, questReader.readShort());
        difficultIntroduce.put(Act.ACT4, questReader.readShort());
        result.addAll(readActQuests(questReader, Act.ACT4));
        difficultTravel.put(Act.ACT5, questReader.readShort());
        difficultIntroduce.put(Act.ACT5, questReader.readShort());
        questReader.skip(4);
        result.addAll(readActQuests(questReader, Act.ACT5));
        questReader.skip(unknown2.length);
        quests.put(difficult, result);
        introduce.put(difficult, difficultIntroduce);
        travel.put(difficult, difficultTravel);
    }

    private List<Quest> readActQuests(ByteReader questReader, Act act) {
        List<Quest> result = new ArrayList<>();
        for (int i = 0; i < QUEST_BY_ACT; i++) {
            if (act == Act.ACT4 && i == 3) {
                questReader.skip(6);
                break;
            }
            Quest quest = questDao.getQuestByPosition(i, act);
            quest.setBinary(new Binary(questReader.readBytes(2)));
            result.add(quest);
        }
        return result;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE)
                .put(identifier)
                .put(unknown1)
                .put(getBytesFromQuests(Difficult.NORMAL))
                .put(getBytesFromQuests(Difficult.NIGHTMARE))
                .put(getBytesFromQuests(Difficult.HELL));
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private byte[] getBytesFromQuests(Difficult difficult) {
        ByteBuffer buffer = ByteBuffer.allocate(QUEST_DATA_SIZE).order(ByteOrder.LITTLE_ENDIAN);
        List<Quest> data = quests.get(difficult);
        buffer.putShort(introduce.get(difficult).get(Act.ACT1));
        buffer.put(readActQuestsByte(data, Act.ACT1));
        buffer.putShort(travel.get(difficult).get(Act.ACT2));
        buffer.putShort(introduce.get(difficult).get(Act.ACT2));
        buffer.put(readActQuestsByte(data, Act.ACT2));
        buffer.putShort(travel.get(difficult).get(Act.ACT3));
        buffer.putShort(introduce.get(difficult).get(Act.ACT3));
        buffer.put(readActQuestsByte(data, Act.ACT3));
        buffer.putShort(travel.get(difficult).get(Act.ACT4));
        buffer.putShort(introduce.get(difficult).get(Act.ACT4));
        buffer.put(readActQuestsByte(data, Act.ACT4));
        buffer.putShort(travel.get(difficult).get(Act.ACT5));
        buffer.putShort(introduce.get(difficult).get(Act.ACT5));
        buffer.putInt(0);
        buffer.put(readActQuestsByte(data, Act.ACT5));
        buffer.put(unknown2);
        buffer.flip();
        return buffer.array();
    }

    private byte[] readActQuestsByte(List<Quest> data, Act act) {
        ByteBuffer result = ByteBuffer.allocate(ACT_QUEST_DATA_SIZE).order(ByteOrder.LITTLE_ENDIAN);
        List<Quest> actQuest = data.stream()
                .filter(q -> q.getAct() == act)
                .collect(Collectors.toList());
        for (int i = 0; i < QUEST_BY_ACT; i++) {
            if (act == Act.ACT4 && i == 3) {
                result.putShort((short) 0);
                result.putShort((short) 0);
                result.putShort((short) 0);
                break;
            }
            result.put(actQuest
                    .get(i)
                    .getBinary()
                    .array());
        }
        result.flip();
        return result.array();
    }

    public List<Quest> getQuests(Difficult difficult) {
        return quests.get(difficult);
    }

    public void setQuests(List<Quest> waypoints, Difficult difficult) {
        this.quests.put(difficult, waypoints);
    }
}
