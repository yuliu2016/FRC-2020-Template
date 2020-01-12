package ca.warp7.frc2020.lib;

import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DriverStation;

import static ca.warp7.frc2020.lib.ButtonState.None;

/**
 * Handle input from Xbox 360 or Xbox One controllers connected to the Driver Station.
 *
 * This class handles Xbox input that comes from the Driver Station. There is a single
 * class instance for each controller.
 *
 * The state of the controller is updated through the collectControllerData method
 */
public class XboxControllerHelper {

    public ButtonState aButton          = None;
    public ButtonState bButton          = None;
    public ButtonState xButton          = None;
    public ButtonState yButton          = None;

    public ButtonState leftBumper       = None;
    public ButtonState rightBumper      = None;

    public ButtonState leftStickButton  = None;
    public ButtonState rightStickButton = None;

    public ButtonState backButton       = None;
    public ButtonState startButton      = None;

    public double leftTrigger           = 0.0;
    public double rightTrigger          = 0.0;

    public double leftX                 = 0.0;
    public double leftY                 = 0.0;

    public double rightX                = 0.0;
    public double rightY                = 0.0;

    private int port;
    private DriverStation ds = DriverStation.getInstance();
    private boolean unplugReported = false;

    /**
     * Create a new controller helper
     *
     * @param port the port on the Driver Station
     */
    public XboxControllerHelper(int port) {
        this.port = port;

        HAL.report(FRCNetComm.tResourceType.kResourceType_XboxController, port + 1);
    }

    /**
     * Update the controller data. This should be done once in each periodic function
     * of the robot. If the controller is not found, this method reports a warning
     * to the Driver Station once, then never again until reset is called
     */
    public void collectControllerData() {

        int buttonCount = ds.getStickButtonCount(port);
        int axisCount = ds.getStickAxisCount(port);

        if (buttonCount >= 10 && axisCount > 5) {
            int buttons      = ds               .getStickButtons(port);

            aButton          = aButton          .update((buttons & 1     ) != 0);
            bButton          = bButton          .update((buttons & 1 << 1) != 0);
            xButton          = xButton          .update((buttons & 1 << 2) != 0);
            yButton          = yButton          .update((buttons & 1 << 3) != 0);

            leftBumper       = leftBumper       .update((buttons & 1 << 4) != 0);
            rightBumper      = rightBumper      .update((buttons & 1 << 5) != 0);

            backButton       = backButton       .update((buttons & 1 << 6) != 0);
            startButton      = startButton      .update((buttons & 1 << 7) != 0);

            leftStickButton  = leftStickButton  .update((buttons & 1 << 8) != 0);
            rightStickButton = rightStickButton .update((buttons & 1 << 9) != 0);

            leftX            = ds               .getStickAxis(port, 0);
            rightX           = ds               .getStickAxis(port, 1);

            leftTrigger      = ds               .getStickAxis(port, 2);
            rightTrigger     = ds               .getStickAxis(port, 3);

            leftY            = ds               .getStickAxis(port, 4);
            rightY           = ds               .getStickAxis(port, 5);

        } else {
            if (!unplugReported) {
                unplugReported = true;
                DriverStation.reportWarning("The controller on port " + port +
                        "is not plugged in", false);
            }
        }
    }
}
