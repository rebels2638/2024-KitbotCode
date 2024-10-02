package frc.robot.subsystems.shooter.roller;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.util.RebelUtil;

public class Roller extends SubsystemBase {
    private final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();
    private RollerIO io;

    private final SimpleMotorFeedforward realFF = new SimpleMotorFeedforward(0, 0, 0);
    private final SimpleMotorFeedforward simFF = new SimpleMotorFeedforward(0, 0.00208, .012);
    private final SimpleMotorFeedforward feedforward;
    
    private double desiredRPM = 0;
    public Roller() {
        switch(Constants.currentMode) {
            case REAL:
                io = new RollerIOVictorSP();
                feedforward = realFF;
                break;
            case SIM:
                io = new RollerIOSim();
                feedforward = simFF;
                break;
            case REPLAY_REAL:
                io = new RollerIO() {};
                feedforward = realFF;
                break;
            default: // REPLAY_SIM
                io = new RollerIO() {};
                feedforward = simFF;
                break;
        }
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Roller", inputs);

        double voltage = feedforward.calculate(desiredRPM, Math.signum(desiredRPM));
        RebelUtil.constrain(voltage, -12, 12);

        if (Math.max(inputs.tAmps, inputs.bAmps) > 40 || 
            Math.max(inputs.tVolts, inputs.tVolts) > 12 || 
            Math.max(inputs.tTemp, inputs.tTemp) >= 94) {
            voltage = 0;
            
            System.err.println("SHOOTER MOTOR NOT WITHIN OPERATION RANGE");
            Logger.recordOutput("Roller/withinOperationRange", false);
        }
        else {
            Logger.recordOutput("Roller/withinOperationRange", true);
        }

        io.setVoltage(voltage);
        Logger.recordOutput("Roller/calculatedVoltage", voltage);

    }

    public void setRPM(double rpm) {
        desiredRPM = rpm;
        Logger.recordOutput("Roller/desiredRMP", desiredRPM);
    }

    public boolean reachedSetpoint() {
        return Math.abs(inputs.RPM - desiredRPM) <= 5;
    }
}
