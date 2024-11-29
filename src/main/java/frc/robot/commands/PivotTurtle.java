package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class PivotTurtle extends Command {
    private final Pivot pivot;

    public PivotTurtle(Pivot pivot) {
        this.pivot = pivot;

        addRequirements(pivot);
    }

    @Override
    public void execute() {
        this.pivot.setRadAngle(Math.toRadians(-22));
    }

    @Override
    public boolean isFinished() {
        return pivot.reachedGoal(); 
    }

}