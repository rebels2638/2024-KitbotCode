package frc.robot.subsystems.shooter.flywheel;

import org.littletonrobotics.junction.AutoLog;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        public double RPM;
        
        public double tTemp;
        public double bTemp;

        public double tAmps;
        public double bAmps;

        public double tVolts;
        public double bVolts;
    }

    public default void updateInputs(FlywheelIOInputs inputs) {}
    public default void setVoltage(double voltage) {}
}
