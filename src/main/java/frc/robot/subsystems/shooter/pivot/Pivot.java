package frc.robot.subsystems.shooter.pivot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.util.RebelUtil;

public class Pivot extends SubsystemBase {
    private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
    private PivotIO io;

    private final ProfiledPIDController feedbackController;
    private final ArmFeedforward feedforwardController;
    
    private final double kMAX_RAD_ANGLE = Math.PI * .8;
    private final double kMIN_RAD_ANGLE = 0;

    private double desiredRadAngle = 0;
    public Pivot() {
        switch(Constants.currentMode) {
            case REAL:
                io = new PivotIOSparkMAX();
                feedbackController = 
                    new ProfiledPIDController(1, 0, 0, new Constraints(2 * Math.PI * .25, 2));
                feedforwardController = new ArmFeedforward(0, 0, 0);

                feedbackController.setTolerance(0.1, .2);
                break;
            case SIM:
                // io = new PivotIOSim();
                feedbackController = 
                    new ProfiledPIDController(1, 0, 0, new Constraints(2 * Math.PI * .25, 2));
                feedforwardController = new ArmFeedforward(0, 0, 0);
                break;
            case REPLAY_REAL:
                io = new PivotIO() {};
                feedbackController = 
                    new ProfiledPIDController(1, 0, 0, new Constraints(2 * Math.PI * .25, 2));
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
        voltage += feedforwardController.calculate(feedbackController.getGoal().position, feedbackController.getGoal().velocity);

        voltage = RebelUtil.constrain(voltage, -12, 12);

        if (Math.max(inputs.rAmps, inputs.lAmps) > 40 || 
            Math.max(inputs.rVolts, inputs.rVolts) > 12 || 
            Math.max(inputs.rTemp, inputs.rTemp) >= 94 ||
            inputs.angleRad > kMAX_RAD_ANGLE && voltage > 0 ||
            inputs.angleRad < kMIN_RAD_ANGLE && voltage < 0) {
            voltage = 0;
        
            System.err.println("Pivot MOTOR NOT WITHIN OPERATION RANGE");
            Logger.recordOutput("Pivot/withinOperationRange", false);
        }
        else {
            Logger.recordOutput("Pivot/withinOperationRange", true);
        }

        io.setVoltage(voltage);
        Logger.recordOutput("Pivot/calculatedVoltage", voltage);

    }

    public void setRadAngle(double angleRad) {
        desiredRadAngle = RebelUtil.constrain(angleRad, kMAX_RAD_ANGLE, kMAX_RAD_ANGLE);
        Logger.recordOutput("Pivot/desiredRadAngle", desiredRadAngle);
    }

    public boolean reachedGoal() {
        return feedbackController.atGoal();
    }
}
