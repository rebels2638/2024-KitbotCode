package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import frc.robot.lib.util.RebelUtil;

import com.revrobotics.CANSparkMax;

public class IntakeIOSparkMax implements IntakeIO {
    
    private static final double kMOTOR_TO_OUTPUT_RATIO = (1/5.0);
    private CANSparkMax m_motor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
    
    public IntakeIOSparkMax() {
        m_motor.setIdleMode(IdleMode.kBrake);
        m_motor.getEncoder().setPosition(0);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.velocityRadSec = m_motor.getEncoder().getVelocity() / 60.0 * kMOTOR_TO_OUTPUT_RATIO * Math.PI * 2;
        inputs.volts = m_motor.getAppliedOutput()*12;
    }

    @Override
    public void setVoltage(double voltage) {
        m_motor.setVoltage(voltage);
    }
}
