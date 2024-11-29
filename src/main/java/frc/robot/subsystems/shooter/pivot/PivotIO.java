package frc.robot.subsystems.shooter.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
    @AutoLog
    public static class PivotIOInputs {
        public double angleRad = 0;
        public double velocityRadSec = 0;

        public double rTemp = 0;
        public double lTemp = 0;

        public double rAmps = 0;
        public double lAmps = 0;

        public double rVolts = 0;
        public double lbVolts = 0;
    }

    public default void updateInputs(PivotIOInputs inputs) {}
    public default void setVoltage(double voltage) {}
}
