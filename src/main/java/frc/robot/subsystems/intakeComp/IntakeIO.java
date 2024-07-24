package frc.robot.subsystems.intakeComp;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface IntakeIO {
    @AutoLog
    public static class IntakeIOInputs {
        public double velocityRadSec;
        public double velocityMps;
        public double distanceMeters;
        public boolean inIntake; 
        public boolean reachedSetpoint;
    }

    public default void updateInputs(IntakeIOInputs inputs) {}

    public default void setVelocityRadSec(double goalVelocityRadPerSec) {}

    public default void configureController(SimpleMotorFeedforward vff, PIDController vfb) {}

    public default void setVoltage(double voltage) {}
    
}
