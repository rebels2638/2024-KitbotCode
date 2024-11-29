package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.util.RebelUtil;

public class Intake extends SubsystemBase {
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private IntakeIO io;

    private final SimpleMotorFeedforward realFF = new SimpleMotorFeedforward(0.4, 5, 0);
    private final SimpleMotorFeedforward simFF = new SimpleMotorFeedforward(0, 0.1, .012);
    private final SimpleMotorFeedforward feedforward;
    
    private double desiredRadPerSec = 0;
    public Intake() {
        switch(Constants.currentMode) {
            case REAL:
                io = new IntakeIOSparkMax();
                feedforward = realFF;
                break;
            // case SIM:
            //     io = new IntakeIOSim();
            //     feedforward = simFF;
            //     break;
            // case REPLAY_REAL:
            //     io = new IntakeIO() {};
            //     feedforward = realFF;
            //     break;
            default: // REPLAY_SIM
                io = new IntakeIO() {};
                feedforward = simFF;
                break;
        }
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);

        double volts = feedforward.calculate(desiredRadPerSec);
        volts = RebelUtil.constrain(volts, -5, 5);

        Logger.recordOutput("Intake/calculatedVoltage", inputs.volts);
        io.setVoltage(volts);
    }

    public void setRadPerSec(double radpersec) {
        desiredRadPerSec = radpersec;
        Logger.recordOutput("Intake/desiredRPS", desiredRadPerSec); // Radians per second
    }

    public boolean reachedSetpoint() {
        return Math.abs(inputs.velocityRadSec - desiredRadPerSec) <= 0.5;
    }
}
