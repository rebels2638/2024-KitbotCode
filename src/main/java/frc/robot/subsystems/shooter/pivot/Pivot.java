package frc.robot.subsystems.shooter.pivot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.util.RebelUtil;

public class Pivot extends SubsystemBase {
    private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
    private PivotIO io;

    private final ProfiledPIDController feedbackController;
    private final ArmFeedforward feedforwardController;
    
    private final double kMAX_RAD_ANGLE = Math.toRadians(70); // 91 deg
    private final double kMIN_RAD_ANGLE = Math.toRadians(-23);
    private final double kG_OFFSET_RAD = Math.toRadians(20);
    private double desiredRadAngle = Math.toRadians(-20);
    private double prevTime = Timer.getFPGATimestamp();
    private double prevVelo = 0;
    public Pivot() {
        switch(Constants.currentMode) {
            case REAL:
                io = new PivotIOSparkMAX();
                feedbackController = 
                    new ProfiledPIDController(12,0, .1, new Constraints(2 * Math.PI * .25, 4.5));
                feedforwardController = new ArmFeedforward(0.115, 0.2, 0, 0);
                feedbackController.setTolerance(Math.toRadians(.5), .03);
                break;
            case SIM:
                // io = new PivotIOSim();

                feedbackController = 
                    new ProfiledPIDController(3, 0, 0, new Constraints(2 * Math.PI * .25, 2));
                feedforwardController = new ArmFeedforward(0, 0, 0);
                break;
            case REPLAY_REAL:
                io = new PivotIO() {};
                feedbackController = 
                    new ProfiledPIDController(3, 0, 0, new Constraints(2 * Math.PI * .25, 2));
                feedforwardController = new ArmFeedforward(0, 0, 0);
                break;
            default: // REPLAY_SIM
                io = new PivotIO() {};
                feedbackController = 
                    new ProfiledPIDController(1, 0, 0, new Constraints(2 * Math.PI * .25, 2));
                feedforwardController = new ArmFeedforward(0, 0, 0);
                break;
        }
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Pivot", inputs);
        
        double voltage = feedbackController.calculate(inputs.angleRad, desiredRadAngle);
        Logger.recordOutput("Pivot/profliedPositionRad", feedbackController.getSetpoint().position);
        Logger.recordOutput("Pivot/profiledVelocityRadSec", feedbackController.getSetpoint().velocity);

        Logger.recordOutput("Pivot/feedbackVoltage", voltage);

        voltage += feedforwardController.calculate(
            feedbackController.getSetpoint().position + kG_OFFSET_RAD, 
            feedbackController.getSetpoint().velocity, 
            (feedbackController.getGoal().velocity - prevVelo)/(Timer.getFPGATimestamp() - prevTime));
        Logger.recordOutput("Pivot/feedbackVelocity", feedbackController.getSetpoint().velocity);
        Logger.recordOutput("Pivot/feedforwardVoltage", voltage);

        voltage = RebelUtil.constrain(voltage, -12, 12);
        // if (Math.max(inputs.rAmps, inputs.lAmps) > 40 || 
        //     Math.max(inputs.rVolts, inputs.rVolts) > 12 || 
        //     Math.max(inputs.rTemp, inputs.rTemp) >= 100 ||
        //     inputs.angleRad > kMAX_RAD_ANGLE && voltage > 0 ||
        //     inputs.angleRad < kMIN_RAD_ANGLE && voltage < 0) {
        //     voltage = 0;
        
        //     System.err.println("Pivot MOTOR NOT WITHIN OPERATION RANGE");
        //     Logger.recordOutput("Pivot/withinOperationRange", false);
        // }
        // else {
        //     Logger.recordOutput("Pivot/withinOperationRange", true);
        // }
        Logger.recordOutput("Pivot/calculatedVoltage", voltage);
        io.setVoltage(voltage);
        prevTime = Timer.getFPGATimestamp();
        prevVelo = feedbackController.getSetpoint().velocity;
        System.out.println(voltage);
    }

    public void setRadAngle(double angleRad) {
        desiredRadAngle = RebelUtil.constrain(angleRad, kMIN_RAD_ANGLE, kMAX_RAD_ANGLE);
        Logger.recordOutput("Pivot/desiredRadAngle", desiredRadAngle);
    }

    public double getRadAngle() {
        return inputs.angleRad;
    }

    public boolean reachedGoal() {
        return feedbackController.atGoal();
    }
}
