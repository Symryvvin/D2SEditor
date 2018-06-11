package ru.aizen.domain.character.entity;

import java.util.HashMap;
import java.util.Map;

public class Levels {
    private static final Map<Integer, Levels> LEVELS;

    private long experience;
    private int gold;
    private int goldBank;

    public Levels(long experience, int gold, int goldBank) {
        this.experience = experience;
        this.gold = gold;
        this.goldBank = goldBank;
    }

    public static String getExperienceAtLevel(int level) {
        return String.valueOf(LEVELS.get(level).experience);
    }

    public static int getGoldAtLevel(int level) {
        return LEVELS.get(level).gold;
    }

    public static int getGoldBankAtLevel(int level) {
        return LEVELS.get(level).goldBank;
    }

    static {
        LEVELS = new HashMap<>();
        LEVELS.put(0, new Levels(0, 0, 0));
        LEVELS.put(1, new Levels(500, 10000, 50000));
        LEVELS.put(2, new Levels(1500, 20000, 50000));
        LEVELS.put(3, new Levels(3750, 30000, 50000));
        LEVELS.put(4, new Levels(7875, 40000, 50000));
        LEVELS.put(5, new Levels(14175, 50000, 50000));
        LEVELS.put(6, new Levels(22680, 60000, 50000));
        LEVELS.put(7, new Levels(32886, 70000, 50000));
        LEVELS.put(8, new Levels(44396, 80000, 50000));
        LEVELS.put(9, new Levels(57715, 90000, 50000));
        LEVELS.put(10, new Levels(72144, 100000, 100000));
        LEVELS.put(11, new Levels(90180, 110000, 100000));
        LEVELS.put(12, new Levels(112725, 120000, 100000));
        LEVELS.put(13, new Levels(140906, 130000, 100000));
        LEVELS.put(14, new Levels(176132, 140000, 100000));
        LEVELS.put(15, new Levels(220165, 150000, 100000));
        LEVELS.put(16, new Levels(275207, 160000, 100000));
        LEVELS.put(17, new Levels(344008, 170000, 100000));
        LEVELS.put(18, new Levels(430010, 180000, 100000));
        LEVELS.put(19, new Levels(537513, 190000, 100000));
        LEVELS.put(20, new Levels(671891, 200000, 150000));
        LEVELS.put(21, new Levels(839864, 210000, 150000));
        LEVELS.put(22, new Levels(1049830, 220000, 150000));
        LEVELS.put(23, new Levels(1312287, 230000, 150000));
        LEVELS.put(24, new Levels(1640359, 240000, 150000));
        LEVELS.put(25, new Levels(2050449, 250000, 150000));
        LEVELS.put(26, new Levels(2563061, 260000, 150000));
        LEVELS.put(27, new Levels(3203826, 270000, 150000));
        LEVELS.put(28, new Levels(3902260, 280000, 150000));
        LEVELS.put(29, new Levels(4663553, 290000, 150000));
        LEVELS.put(30, new Levels(5493363, 300000, 200000));
        LEVELS.put(31, new Levels(6397855, 310000, 800000));
        LEVELS.put(32, new Levels(7383752, 320000, 850000));
        LEVELS.put(33, new Levels(8458379, 330000, 850000));
        LEVELS.put(34, new Levels(9629723, 340000, 900000));
        LEVELS.put(35, new Levels(10906488, 350000, 900000));
        LEVELS.put(36, new Levels(12298162, 360000, 950000));
        LEVELS.put(37, new Levels(13815086, 370000, 950000));
        LEVELS.put(38, new Levels(15468534, 380000, 1000000));
        LEVELS.put(39, new Levels(17270791, 390000, 1000000));
        LEVELS.put(40, new Levels(19235252, 400000, 1050000));
        LEVELS.put(41, new Levels(21376515, 410000, 1050000));
        LEVELS.put(42, new Levels(23710491, 420000, 1100000));
        LEVELS.put(43, new Levels(26254525, 430000, 1100000));
        LEVELS.put(44, new Levels(29027522, 440000, 1150000));
        LEVELS.put(45, new Levels(32050088, 450000, 1150000));
        LEVELS.put(46, new Levels(35344686, 460000, 1200000));
        LEVELS.put(47, new Levels(38935798, 470000, 1200000));
        LEVELS.put(48, new Levels(42850109, 480000, 1250000));
        LEVELS.put(49, new Levels(47116709, 490000, 1250000));
        LEVELS.put(50, new Levels(51767302, 500000, 1300000));
        LEVELS.put(51, new Levels(56836449, 510000, 1300000));
        LEVELS.put(52, new Levels(62361819, 520000, 1350000));
        LEVELS.put(53, new Levels(68384473, 530000, 1350000));
        LEVELS.put(54, new Levels(74949165, 540000, 1400000));
        LEVELS.put(55, new Levels(82104680, 550000, 1400000));
        LEVELS.put(56, new Levels(89904191, 560000, 1450000));
        LEVELS.put(57, new Levels(98405658, 570000, 1450000));
        LEVELS.put(58, new Levels(107672256, 580000, 1500000));
        LEVELS.put(59, new Levels(117772849, 590000, 1500000));
        LEVELS.put(60, new Levels(128782495, 600000, 1550000));
        LEVELS.put(61, new Levels(140783010, 610000, 1550000));
        LEVELS.put(62, new Levels(153863570, 620000, 1600000));
        LEVELS.put(63, new Levels(168121381, 630000, 1600000));
        LEVELS.put(64, new Levels(183662396, 640000, 1650000));
        LEVELS.put(65, new Levels(200602101, 650000, 1650000));
        LEVELS.put(66, new Levels(219066380, 660000, 1700000));
        LEVELS.put(67, new Levels(239192444, 670000, 1700000));
        LEVELS.put(68, new Levels(261129853, 680000, 1750000));
        LEVELS.put(69, new Levels(285041630, 690000, 1750000));
        LEVELS.put(70, new Levels(311105466, 700000, 1800000));
        LEVELS.put(71, new Levels(339515048, 710000, 1800000));
        LEVELS.put(72, new Levels(370481492, 720000, 1850000));
        LEVELS.put(73, new Levels(404234916, 730000, 1850000));
        LEVELS.put(74, new Levels(441026148, 740000, 1900000));
        LEVELS.put(75, new Levels(481128591, 750000, 1900000));
        LEVELS.put(76, new Levels(524840254, 760000, 1950000));
        LEVELS.put(77, new Levels(572485967, 770000, 1950000));
        LEVELS.put(78, new Levels(624419793, 780000, 2000000));
        LEVELS.put(79, new Levels(681027665, 790000, 2000000));
        LEVELS.put(80, new Levels(742730244, 800000, 2050000));
        LEVELS.put(81, new Levels(809986056, 810000, 2050000));
        LEVELS.put(82, new Levels(883294891, 820000, 2100000));
        LEVELS.put(83, new Levels(963201521, 830000, 2100000));
        LEVELS.put(84, new Levels(1050299747, 840000, 2150000));
        LEVELS.put(85, new Levels(1145236814, 850000, 2150000));
        LEVELS.put(86, new Levels(1248718217, 860000, 2200000));
        LEVELS.put(87, new Levels(1361512946, 870000, 2200000));
        LEVELS.put(88, new Levels(1484459201, 880000, 2250000));
        LEVELS.put(89, new Levels(1618470619, 890000, 2250000));
        LEVELS.put(90, new Levels(1764543065, 900000, 2300000));
        LEVELS.put(91, new Levels(1923762030, 910000, 2300000));
        LEVELS.put(92, new Levels(2097310703, 920000, 2350000));
        LEVELS.put(93, new Levels(2286478756L, 930000, 2350000));
        LEVELS.put(94, new Levels(2492671933L, 940000, 2400000));
        LEVELS.put(95, new Levels(2717422497L, 950000, 2400000));
        LEVELS.put(96, new Levels(2962400612L, 960000, 2450000));
        LEVELS.put(97, new Levels(3229426756L, 970000, 2450000));
        LEVELS.put(98, new Levels(3520485254L, 980000, 2500000));
        LEVELS.put(99, new Levels(3837739017L, 990000, 2500000));
    }
}
