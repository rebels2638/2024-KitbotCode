package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.flywheel.Flywheel;

public class ShooterWindup extends Command {
    private Flywheel flywheel;
    public ShooterWindup(Flywheel flywheel) {
        this.flywheel = flywheel;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setRPM(50);
    }

    @Override
    public boolean isFinished() {
        return flywheel.reachedSetpoint();
    }

    @Override
    public void end(boolean interrupted) {}
}
