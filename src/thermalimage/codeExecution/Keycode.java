package thermalimage.codeExecution;

/**
 * ENUM of all availabe ADB Keycodes for ADB Keyevent
 */

public enum Keycode {

    UNKNOWN("0"),
    MENU("1"),
    SOFT_RIGHT("2"),
    HOME("3"),
    BACK("4"),
    CALL("5"),
    ENDCALL("6"),
    ZERO("7"),
    ONE("8"),
    TWO("9"),
    THREE("10"),
    FOUR("11"),
    FIVE("12"),
    SIX("13"),
    SEVEN("14"),
    EIGHT("15"),
    NINE("16"),
    STAR("17"),
    POUND("18"),
    DPAD_UP("19"),
    DPAD_DOWN("20"),
    DPAD_LEFT("21"),
    DPAD_RIGHT("22"),
    DPAD_CENTER("23"),
    VOLUMEUP("24"),
    VOLUMEDOWN("25"),
    POWER("26"),
    CAMERA("27"),
    CLEAR("28"),
    A("29"),
    B("30"),
    C("31"),
    D("32"),
    E("33"),
    F("34"),
    G("35"),
    H("36"),
    I("37"),
    J("38"),
    K("39"),
    L("40"),
    M("41"),
    N("42"),
    O("43"),
    P("44"),
    Q("45"),
    R("46"),
    S("47"),
    T("48"),
    U("49"),
    V("50"),
    W("51"),
    X("52"),
    Y("53"),
    Z("54"),
    COMMA("55"),
    PERIOD("56");

    private final String number;

    Keycode(String number) {
        this.number = number;
    }

    String getNumber() {
        return number;
    }
}