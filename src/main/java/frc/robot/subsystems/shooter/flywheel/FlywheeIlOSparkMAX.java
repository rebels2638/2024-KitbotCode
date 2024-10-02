package frc.robot.subsystems.shooter.flywheel;

import com.revrobotics.CANSparkBase.IdleMode;


import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import frc.robot.lib.util.RebelUtil;

public class FlywheeIlOSparkMAX implements FlywheelIO{
    
    private static final double kMOTOR_TO_OUTPUT_RATIO = 2;

    // private PWMMotorController  
    private CANSparkMax m_motorT = new CANSparkMax(88, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax m_motorB = new CANSparkMax(89, CANSparkMax.MotorType.kBrushless);   
        
    private final SimpleMotorFeedforward realFF = new SimpleMotorFeedforward(0, 0, 0);

    public FlywheeIlOSparkMAX() {
        m_motorT.setIdleMode(IdleMode.kBrake);
        m_motorB.setIdleMode(IdleMode.kBrake);

        m_motorT.setInverted(false);
        m_motorB.setInverted(false);
    }

    @Override
    public void updateInputs(FlywheelIOInputs inputs) {
        inputs.RPM = m_motorT.getEncoder().getVelocity() * kMOTOR_TO_OUTPUT_RATIO;

        inputs.tAmps = m_motorT.getOutputCurrent();
        inputs.bAmps = m_motorB.getOutputCurrent();

        inputs.tTemp = m_motorT.getMotorTemperature();
        inputs.bTemp = m_motorB.getMotorTemperature();

        inputs.tVolts = m_motorT.getAppliedOutput() * 12;
        inputs.bVolts = m_motorB.getAppliedOutput() * 12;
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
