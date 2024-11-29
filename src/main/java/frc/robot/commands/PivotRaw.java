package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import frc.robot.lib.input.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class PivotRaw extends Command {
    private NetworkTable llTable;
    private final Pivot pivot;
    private final XboxController controller;

    public PivotRaw(Pivot pivot, XboxController controller) {
        this.pivot = pivot;
        this.controller = controller;
        addRequirements(pivot);
    }

    @Override
    public void execute() {
        this.pivot.setRadAngle(-controller.getRightY() * Math.toRadians(91));
    }

    @Override
    public boolean isFinished() {
        return false; 
    }

}