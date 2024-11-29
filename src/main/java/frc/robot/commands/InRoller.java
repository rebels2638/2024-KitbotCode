package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.lib.input.XboxController;
import frc.robot.subsystems.shooter.pivot.Pivot;
import frc.robot.subsystems.shooter.roller.Roller;

public class InRoller extends Command {
    private Roller roller;
    private boolean inRoller = true;

    public InRoller(Roller roller) {
        this.roller = roller;
    }

    @Override
    public void execute() {
        this.inRoller = roller.getLimSwitchState();
    }

    @Override
    public boolean isFinished() {
        System.out.println("IN COMMAND: "+roller.getLimSwitchState());
        return this.inRoller;
    }
}
