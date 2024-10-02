package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.roller.Roller;

public class RollerRun extends Command {
    private Roller roller;
    public RollerRun(Roller roller) {
        this.roller = roller;

        addRequirements(roller);
    }

    @Override
    public void initialize() {
        roller.setRPM(50);
    }

    @Override
    public boolean isFinished() {
        return roller.reachedSetpoint();
    }

    @Override
    public void end(boolean interrupted) {}
}
