package org.whh.bz.utils;

import java.util.Arrays;
import java.util.UUID;

public class UUIDGenerator {

    /**
     * 定义UU64常量组
     */
    private static final char[] _UU64 = "*0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();
    /**
     * 定义UU16常量组
     */
    private static final char[] _UU16 = "0123456789abcdef".toCharArray();

    /**
     * 默认空构造
     */
    public UUIDGenerator() {
    }

    /**
     * 生成原生的UUID对象
     * @return UUID
     */
    public static UUID createUUID() {
        return UUID.randomUUID();
    }

    /**
     * 获得原生的UUID字符串
     * @return UUID
     */
    public static String getUUID() {
        return createUUID().toString();
    }

    /**
     * 获得原生的UUID字符串
     * @return UUID
     */
    public static String getUUID(UUID uuid) {
        return uuid.toString();
    }

    /**
     * 获得指定数量的原生态的UUID组
     * @return UUID[]
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    /**
     * 获得不带“-”符号的UUID
     * @return UUID
     */
    public static String getUUIDStr() {
        String str = getUUID();
        // 去掉"-"符号
        String str2 = str.substring(0, 8) + str.substring(9, 13)
                + str.substring(14, 18) + str.substring(19, 23)
                + str.substring(24);
        return str2;
    }

    /**
     * 获得不带“-”符号的UUID
     * @return UUID
     */
    public static String getUUIDStr(String uuid) {
        // 去掉"-"符号
        String str = uuid.substring(0, 8) + uuid.substring(9, 13)
                + uuid.substring(14, 18) + uuid.substring(19, 23)
                + uuid.substring(24);
        return str;
    }

    /**
     * 获得指定数量的不带“-”符号的UUID组
     * @return UUID[]
     */
    public static String[] getUUIDStr(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    /**
     * 获得一个 UUID ，并用 64 进制转换成紧凑形的字符串，内容为 [@0-9a-zA-Z_]
     * 比如一个类似下面的 UUID:
     * a6c5c51c-689c-4525-9bcd-c14c1e107c80
     * 一共 128 位，分做L64 和 R64，分为为两个 64位数（两个 long）
     *    > L = uu.getLeastSignificantBits();
     *    > R = uu.getMostSignificantBits();
     * 而一个 64 进制数，是 6 位，因此我们取值的顺序是
     * 1. 从L64位取10次，每次取6位
     * 2. 从L64位取最后的4位 ＋ R64位头2位拼上
     * 3. 从R64位取10次，每次取6位
     * 4. 剩下的两位最后取
     * 这样，就能用一个 22 长度的字符串表示一个 32 长度的UUID，压缩了 1/3
     * @param uuid 对象
     * @return 64进制表示的紧凑格式的 UUID
     */
    public static String getUUID64(UUID uuid) {
        int index = 0;
        char[] cs = new char[22];
        long L = uuid.getMostSignificantBits();
        long R = uuid.getLeastSignificantBits();
        long mask = 63;
        // 从L64位取10次，每次取6位
        for (int off = 58; off >= 4; off -= 6) {
            long hex = (L & (mask << off)) >>> off;
            cs[index++] = _UU64[(int) hex];
        }
        // 从L64位取最后的4位 ＋ R64位头2位拼上
        int l = (int) (((L & 0xF) << 2) | ((R & (3 << 62)) >>> 62));
        cs[index++] = _UU64[l];
        // 从R64位取10次，每次取6位
        for (int off = 56; off >= 2; off -= 6) {
            long hex = (R & (mask << off)) >>> off;
            cs[index++] = _UU64[(int) hex];
        }
        // 剩下的两位最后取
        cs[index++] = _UU64[(int) (R & 3)];
        // 返回字符串
        return new String(cs);

    }

    /**
     * 获得一个uuid64的字符串
     */
    public static String getUUID64(){
        return getUUID64(createUUID());
    }

    /**
     * 从一个 uuid64 恢复回一个 UUID 对象
     * @param uuid64 64进制表示的 UUID, 内容为 [\\-0-9a-zA-Z_]
     * @return UUID 对象
     */
    public static UUID fromUUID64(String uuid64) {
        String uuid16 = UU16FromUU64(uuid64);
        return UUID.fromString(UUID(uuid16));
    }

    /**
     * 将紧凑格式的 UU16 字符串变成标准 UUID 格式的字符串
     * @param uuid16
     * @return 标准 UUID 字符串
     */
    public static String UUID(String uuid16) {
        StringBuilder sb = new StringBuilder();
        sb.append(uuid16.substring(0, 8));
        sb.append('-');
        sb.append(uuid16.substring(8, 12));
        sb.append('-');
        sb.append(uuid16.substring(12, 16));
        sb.append('-');
        sb.append(uuid16.substring(16, 20));
        sb.append('-');
        sb.append(uuid16.substring(20));
        return sb.toString();
    }

    /**
     * 获得一个 UU64 表示的紧凑字符串，变成 UU16 表示的字符串
     * 每次取2个字符，恢复成3个byte，重复10次，
     * 最后一次，是用最后2个字符，恢复回2个byte
     * @param uuid64
     *            uu64 64进制表示的 UUID, 内容为 [\\-0-9a-zA-Z_]
     * @return 16进制表示的紧凑格式的 UUID
     */
    public static String UU16FromUU64(String uuid64) {
        byte[] bytes = new byte[32];
        char[] cs = uuid64.toCharArray();
        int index = 0;
        // 每次取2个字符，恢复成3个byte，重复10次，
        for (int i = 0; i < 10; i++) {
            int off = i * 2;
            char cl = cs[off];
            char cr = cs[off + 1];
            int l = Arrays.binarySearch(_UU64, cl);
            int r = Arrays.binarySearch(_UU64, cr);
            int n = (l << 6) | r;
            bytes[index++] = (byte) ((n & 0xF00) >>> 8);
            bytes[index++] = (byte) ((n & 0xF0) >>> 4);
            bytes[index++] = (byte) (n & 0xF);
        }
        // 最后一次，是用最后2个字符，恢复回2个byte
        char cl = cs[20];
        char cr = cs[21];
        int l = Arrays.binarySearch(_UU64, cl);
        int r = Arrays.binarySearch(_UU64, cr);
        int n = (l << 2) | r;
        bytes[index++] = (byte) ((n & 0xF0) >>> 4);
        bytes[index++] = (byte) (n & 0xF);

        // 返回 UUID 对象
        char[] names = new char[32];
        for (int i = 0; i < bytes.length; i++) {
            names[i] = _UU16[bytes[i]];
        }
        return new String(names);
    }

    /**
     * 获得UUID16
     * @param uuid 对象
     * @return 16进制表示的紧凑格式的 UUID
     */
    public static String getUUID16(UUID uuid) {
        return alignRight(Long.toHexString(uuid.getMostSignificantBits()), 16, '0')
                + alignRight(Long.toHexString(uuid.getLeastSignificantBits()), 16, '0');
    }

    /**
     * 右对齐操作
     * @param o
     * @param width
     * @param c
     * @return
     */
    private static String alignRight(Object o, int width, char c) {
        if (null == o) {
            return null;
        }
        String s = o.toString();
        int len = s.length();
        if (len >= width) {
            return s;
        }
        return new StringBuilder().append(dup(c, width - len)).append(s).toString();
    }

    /**
     * 补全操作
     * @param c
     * @param num
     * @return
     */
    private static String dup(char c, int num) {
        if (c == 0 || num < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder(num);
        for (int i = 0; i < num; i++){
            sb.append(c);
        }
        return sb.toString();
    }

}
