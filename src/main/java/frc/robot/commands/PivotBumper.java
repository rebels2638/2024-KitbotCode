package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class PivotBumper extends Command {
    private final Pivot pivot;

    public PivotBumper(Pivot pivot) {
        this.pivot = pivot;

        addRequirements(pivot);
    }

    @Override
    public void execute() {
        this.pivot.setRadAngle(Math.toRadians(61));
    }

    @Override
    public boolean isFinished() {
        return pivot.reachedGoal(); 
    }

}