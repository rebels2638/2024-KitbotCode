package frc.robot.commands;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class FollowTarget extends Command {
    private NetworkTable llTable;
    private final InterpolatingDoubleTreeMap interpolatingDoubleTreeMap = new InterpolatingDoubleTreeMap();
    private final Pivot pivot;
    private double prevTa = 0;
    public FollowTarget(Pivot pivot) {
        this.pivot = pivot;

        llTable = NetworkTableInstance.getDefault().getTable("limelight");
        // ta, angle deg
        interpolatingDoubleTreeMap.put(Double.valueOf(0.57), Double.valueOf(47));
        interpolatingDoubleTreeMap.put(Double.valueOf(0.4), Double.valueOf(37));
        interpolatingDoubleTreeMap.put(Double.valueOf(0.31), Double.valueOf(32));
        interpolatingDoubleTreeMap.put(Double.valueOf(0.27), Double.valueOf(32));

        interpolatingDoubleTreeMap.put(Double.valueOf(0.26), Double.valueOf(28));


        addRequirements(pivot);
    }

    @Override
    public void execute() {
        boolean hasTargets = llTable.getEntry("tv").getDouble(0) == 1;
        // double txRadians = -Math.toRadians(llTable.getEntry("tx").getDouble(0));
        // double tyRadians = Math.toRadians(llTable.getEntry("ty").getDouble(0));
        double ta = llTable.getEntry("ta").getDouble(0);
        if (ta == 0) {
            ta = prevTa;
        }
        else {
            prevTa = ta;
        }
        Logger.recordOutput("FollowTarget/ta", ta);
        if (hasTargets) {
            pivot.setRadAngle(Math.toRadians(interpolatingDoubleTreeMap.get(ta)));
        }
    }

    @Override
    public boolean isFinished() {
        return false; 
    }

}