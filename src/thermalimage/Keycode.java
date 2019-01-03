package thermalimage;

public enum Keycode {

    MENU ("1"),
    VOLUMEUP ("24"),
    VOLUMEDOWN ("25"),
    POWER ("26"),
    CAMERA ("27");

    private final String number;

    Keycode(String number){
        this.number = number;
    }

    String getNumber(){ return number;}
}