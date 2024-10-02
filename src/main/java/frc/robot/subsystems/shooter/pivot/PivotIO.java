package frc.robot.subsystems.shooter.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
    @AutoLog
    public static class PivotIOInputs {
        public double angleRad;
        
        public double rTemp;
        public double lTemp;

        public double rAmps;
        public double lAmps;

        public double rVolts;
        public double lbVolts;
    }

    public default void updateInputs(PivotIOInputs inputs) {}
    public default void setVoltage(double voltage) {}
}
