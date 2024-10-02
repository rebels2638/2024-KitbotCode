package frc.robot.subsystems.shooter.roller;

import org.littletonrobotics.junction.AutoLog;

public interface RollerIO {
    @AutoLog
    public static class RollerIOInputs {
        public double RPM = 0;
        
        public double tTemp = 0;
        public double bTemp = 0;

        public double tAmps = 0;
        public double bAmps = 0;

        public double tVolts = 0;
        public double bVolts = 0;
    }

    public default void updateInputs(RollerIOInputs inputs) {}
    public default void setVoltage(double voltage) {}
}
