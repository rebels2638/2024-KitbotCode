package frc.robot.subsystems.shooter.roller;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public interface RollerIO {
    @AutoLog
    public static class RollerIOInputs {
        public double velocityRadSec;
        public boolean inRoller;
        public double voltage;
    }

    public default void updateInputs(RollerIOInputs inputs) {}
    public default void setVoltage(double volts) {}
    public default void setVelocityRadSec(double goalVelocity, SimpleMotorFeedforward ff, PIDController fb) {}
}
