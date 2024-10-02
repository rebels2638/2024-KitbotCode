package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.flywheel.Flywheel;

public class ShooterStop extends Command {
    private Flywheel flywheel;
    public ShooterStop(Flywheel flywheel) {
        this.flywheel = flywheel;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setRPM(0);
    }

    @Override
    public boolean isFinished() {
        return flywheel.reachedSetpoint();
    }

    @Override
    public void end(boolean interrupted) {}
}
