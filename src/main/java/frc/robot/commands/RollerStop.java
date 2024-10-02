package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.roller.Roller;

public class RollerStop extends Command {
    private Roller roller;
    public RollerStop(Roller roller) {
        this.roller = roller;

        addRequirements(roller);
    }

    @Override
    public void initialize() {
        roller.setRPM(0);
    }

    @Override
    public boolean isFinished() {
        return roller.reachedSetpoint();
    }

    @Override
    public void end(boolean interrupted) {}
}
