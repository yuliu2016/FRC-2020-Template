package ca.warp7.frc2020.subsystems;

import ca.warp7.frc2020.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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
     * Create a master TalonFX
     * @param deviceID the CAN id
     * @return the motor controller object with default settings
     */
    private static WPI_TalonFX createMasterTalon(int deviceID) {
        WPI_TalonFX master = new WPI_TalonFX(deviceID);
        master.setSafetyEnabled(false);
        master.configFactoryDefault();
        master.configVoltageCompSaturation(12.0);
        master.enableVoltageCompensation(true);
        master.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(
                true,
                35.0,
                40.0,
                1.0
        ));
        return master;
    }

    /**
     * Create a follower TalonFX
     * @param master the master motor controller to follow
     * @param deviceID the CAN id
     */
    private static void createFollowerTalon(BaseMotorController master, int deviceID) {
        TalonFX follower = new TalonFX(deviceID);
        follower.configFactoryDefault();
        follower.follow(master);
    }

    private final WPI_TalonFX leftMaster = createMasterTalon(Constants.kLeftMaster);
    private final WPI_TalonFX rightMaster = createMasterTalon(Constants.kRightMaster);

    {
        rightMaster.setInverted(true);
        createFollowerTalon(leftMaster, Constants.kLeftFollower);
        createFollowerTalon(rightMaster, Constants.kRightFollower);
    }

    public void setPercentOutput(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }
}
