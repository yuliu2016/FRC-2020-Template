package ca.warp7.frc2020.commands;

import ca.warp7.frc2020.lib.XboxControllerHelper;
import ca.warp7.frc2020.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopCommand extends CommandBase {

    private XboxControllerHelper driver = new XboxControllerHelper(0);
    private XboxControllerHelper operator = new XboxControllerHelper(1);

    private DriveTrain driveTrain = DriveTrain.getInstance();

    public TeleopCommand() {
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        driver.collectControllerData();
        operator.collectControllerData();

        curvatureDrive();
    }

    private void curvatureDrive() {
        double xSpeed = applyDeadband(-driver.leftY, 0.2);
        double zRotation = applyDeadband(driver.rightX, 0.2);

        double angular;

        if (driver.leftBumper.isHeldDown()) {
            angular = zRotation;
        } else {
            angular = Math.abs(xSpeed) * zRotation;
        }

        double left = xSpeed - angular;
        double right = xSpeed + angular;

        double maxMagnitude = Math.max(Math.abs(left), Math.abs(right));

        driveTrain.setPercentOutput(left / maxMagnitude, right / maxMagnitude);
    }

    /**
     * Returns 0.0 if the given value is within the specified range around zero.
     * The remaining range between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     */
    @SuppressWarnings("SameParameterValue")
    private static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }
}
