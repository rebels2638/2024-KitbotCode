package frc.robot.subsystems.shooter.roller;

import com.revrobotics.CANSparkBase.IdleMode;


import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import frc.robot.lib.util.RebelUtil;

public class RollerIOVictorSP implements RollerIO{
    
    private static final double kMOTOR_TO_OUTPUT_RATIO = 2;

    private VictorSP m_motorT = new VictorSP(88);
    private VictorSP m_motorB = new VictorSP(89);   
        
    private final SimpleMotorFeedforward realFF = new SimpleMotorFeedforward(0, 0, 0);

    public RollerIOVictorSP() {
        m_motorT.setInverted(false);
        m_motorB.setInverted(false);
    }

    @Override
    public void updateInputs(RollerIOInputs inputs) {
    }

    public void setVoltage(double voltage) {
        m_motorT.setVoltage(voltage);
        m_motorB.setVoltage(voltage);
    }

    public double calculatedVoltage(double desiredRPM) {
        double voltage = realFF.calculate(desiredRPM, Math.signum(desiredRPM));
        return RebelUtil.constrain(voltage, -12, 12);
    }
}
