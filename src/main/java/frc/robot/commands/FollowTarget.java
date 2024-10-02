package frc.robot.commands;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class FollowTarget extends Command {
    private NetworkTable llTable;
    private final InterpolatingDoubleTreeMap interpolatingDoubleTreeMap = new InterpolatingDoubleTreeMap();
    private final Pivot pivot;
    public FollowTarget(Pivot pivot) {
        this.pivot = pivot;

        llTable = NetworkTableInstance.getDefault().getTable("limelight");
        // ta, angle
        interpolatingDoubleTreeMap.put(Double.valueOf(0), Double.valueOf(0));

        addRequirements(pivot);
    }

    @Override
    public void execute() {
        boolean hasTargets = llTable.getEntry("tv").getDouble(0) == 1;
        // double txRadians = -Math.toRadians(llTable.getEntry("tx").getDouble(0));
        // double tyRadians = Math.toRadians(llTable.getEntry("ty").getDouble(0));
        double ta = llTable.getEntry("ta").getDouble(0);

        if (hasTargets) {
            pivot.setRadAngle(interpolatingDoubleTreeMap.get(ta));
        }
    }

    @Override
    public boolean isFinished() {
        return false; 
    }

}