package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class ShooterTurtule extends Command {
    private final Pivot pivot;
    public ShooterTurtule(Pivot pivot) {
        this.pivot = pivot;

        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        pivot.setRadAngle(0);
    }

    @Override
    public boolean isFinished() {
        return pivot.reachedGoal(); 
    }
}