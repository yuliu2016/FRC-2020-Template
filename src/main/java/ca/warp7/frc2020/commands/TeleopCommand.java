package ca.warp7.frc2020.commands;

import ca.warp7.frc2020.lib.XboxControllerHelper;
import ca.warp7.frc2020.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopCommand extends CommandBase {

    XboxControllerHelper driver = new XboxControllerHelper(0);
    XboxControllerHelper operator = new XboxControllerHelper(1);

    private TeleopCommand() {
        addRequirements(Drive.getInstance());
    }

    @Override
    public void execute() {
        driver.collectControllerData();
        operator.collectControllerData();
    }

    @Override
    public void end(boolean interrupted) {
        driver.reset();
        operator.reset();
    }
}
