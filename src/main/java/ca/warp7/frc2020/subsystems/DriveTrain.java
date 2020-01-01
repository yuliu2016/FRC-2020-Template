package ca.warp7.frc2020.subsystems;

import ca.warp7.frc2020.Constants;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveTrain implements Subsystem {

    private static DriveTrain instance;

    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    private DriveTrain() {
    }

    /**
     * Create a master TalonSRX
     * @param deviceID the CAN id
     * @return the motor controller object with default settings
     */
    private static WPI_TalonSRX createMasterTalon(int deviceID) {
        WPI_TalonSRX master = new WPI_TalonSRX(deviceID);
        master.setSafetyEnabled(false);
        master.configFactoryDefault();
        master.configVoltageCompSaturation(12.0);
        master.configContinuousCurrentLimit(30);
        master.configPeakCurrentLimit(60);
        master.configPeakCurrentDuration(50);
        master.enableVoltageCompensation(true);
        return master;
    }

    /**
     * Create a follower TalonSRX
     * @param master the master motor controller to follow
     * @param deviceID the CAN id
     */
    private static void createFollowerTalon(BaseMotorController master, int deviceID) {
        TalonSRX follower = new TalonSRX(deviceID);
        follower.configFactoryDefault();
        follower.follow(master);
    }

    private final WPI_TalonSRX leftMaster = createMasterTalon(Constants.kLeftMaster);
    private final WPI_TalonSRX rightMaster = createMasterTalon(Constants.kRightMaster);

    {
        createFollowerTalon(leftMaster, Constants.kLeftFollower);
        createFollowerTalon(rightMaster, Constants.kRightFollower);
    }

    private final DifferentialDrive teleopDrive = new DifferentialDrive(leftMaster, rightMaster);

    {
        teleopDrive.setRightSideInverted(true);
        teleopDrive.setSafetyEnabled(false);
        teleopDrive.setDeadband(0.2);
    }

    /**
     * @return the instance of DifferentialDrive
     */
    public DifferentialDrive getTeleopDrive() {
        return teleopDrive;
    }
}
