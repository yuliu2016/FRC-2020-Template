/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.warp7.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Drive implements Subsystem {

    private static Drive drive;

    public static Drive getInstance() {
        if (drive == null) {
            drive = new Drive();
        }
        return drive;
    }

    private Drive() {

    }
}
