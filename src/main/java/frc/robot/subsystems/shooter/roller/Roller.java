package frc.robot.subsystems.shooter.roller;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.util.RebelUtil;

public class Roller extends SubsystemBase {
    private final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();
    private RollerIO io;

    private boolean inRoller = true;

    private final SimpleMotorFeedforward realFF = new SimpleMotorFeedforward(1, 10, 0);
    private final SimpleMotorFeedforward simFF = new SimpleMotorFeedforward(0, 0.00208, .012);
    private final SimpleMotorFeedforward feedforward;
    
    private double desiredRadPerSec = 0;
    public Roller() {
        switch(Constants.currentMode) {
            case REAL:
                io = new RollerIOSparkMax();
                feedforward = realFF;
                break;
            // case SIM:
            //     io = new RollerIOSim();
            //     feedforward = simFF;
            //     break;
            // case REPLAY_REAL:
            //     io = new RollerIO() {};
            //     feedforward = realFF;
            //     break;
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

        double volts = feedforward.calculate(desiredRadPerSec);
        volts = RebelUtil.constrain(volts, -12, 12);

        this.inRoller = inputs.inRoller;

        io.setVoltage(volts);
    }

    public void setSpeed(double speed) {
        desiredRadPerSec = speed;
    }

    public boolean reachedSetpoint() {
        return Math.abs(inputs.velocityRadSec - desiredRadPerSec) <= 0.03;
    }

    public boolean getLimSwitchState() {
        return inRoller;
    }
}
