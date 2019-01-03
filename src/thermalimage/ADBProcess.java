package thermalimage;

public class ADBProcess {

    /**
     * adb keycode numbers:
     * 1    menu
     * 24   volume up
     * 25   volume down
     * 26   power
     * 27   camera
     *
     * command:
     * adb shell input keyevent keycode number
     *
     */

    //TODO call adb Process to take pictures triggered by Volume Button

    private int ipAddress;

    private int portNumber;

    //TODO implement a Timer that calls the methods in a constant time intervall

    //TODO implement a functionality to get/import pictures in a folder on the computer or the current working directory
    //TODO optional delete the pictures on the phone after the import

}
