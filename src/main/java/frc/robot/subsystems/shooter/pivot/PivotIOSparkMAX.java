package frc.robot.subsystems.shooter.pivot;

import com.revrobotics.CANSparkBase.IdleMode;

import com.revrobotics.CANSparkMax;

public class PivotIOSparkMAX implements PivotIO {
    
    private static final double kMOTOR_TO_OUTPUT_RATIO = 2;

    private CANSparkMax m_motorR = new CANSparkMax(88, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax m_motorL = new CANSparkMax(89, CANSparkMax.MotorType.kBrushless);   
    
    
    public PivotIOSparkMAX() {
        m_motorR.setIdleMode(IdleMode.kBrake);
        m_motorL.setIdleMode(IdleMode.kBrake);

        m_motorR.setInverted(false);
        m_motorL.setInverted(false);
    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        inputs.angleRad = m_motorR.getEncoder().getPosition() * kMOTOR_TO_OUTPUT_RATIO * Math.PI * 2;

        inputs.rAmps = m_motorR.getOutputCurrent();
        inputs.lAmps = m_motorL.getOutputCurrent();

        inputs.rTemp = m_motorR.getMotorTemperature();
        inputs.lTemp = m_motorL.getMotorTemperature();

        inputs.rVolts = m_motorR.getAppliedOutput() * 12;
        inputs.lbVolts = m_motorL.getAppliedOutput() * 12;
    }

    public void setVoltage(double voltage) {
        m_motorR.setVoltage(voltage);
        m_motorL.setVoltage(voltage);
    }
}
