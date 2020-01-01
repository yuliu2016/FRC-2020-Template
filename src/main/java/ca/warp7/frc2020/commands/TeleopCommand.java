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

        driveTrain.getTeleopDrive()
                .curvatureDrive(-driver.leftY, driver.rightX, driver.leftBumper.isHeldDown());
    }

    @Override
    public void end(boolean interrupted) {
        driver.reset();
        operator.reset();
    }
}
