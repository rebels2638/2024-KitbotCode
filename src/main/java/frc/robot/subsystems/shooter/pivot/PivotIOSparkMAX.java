package frc.robot.subsystems.shooter.pivot;

import com.revrobotics.CANSparkBase.IdleMode;

import com.revrobotics.CANSparkMax;

public class PivotIOSparkMAX implements PivotIO {
    
    private static final double kMOTOR_TO_OUTPUT_RATIO = (37/133.0) * (1/3.0) * (1/5.0);
    private static final double KANGLE_OFFSET_RAD = Math.toRadians(-22);
    private CANSparkMax m_motorR = new CANSparkMax(21, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax m_motorL = new CANSparkMax(62, CANSparkMax.MotorType.kBrushless);   
    // left side encoder works right doesent from testing
    
    public PivotIOSparkMAX() {
        m_motorR.setIdleMode(IdleMode.kBrake);
        m_motorL.setIdleMode(IdleMode.kBrake);

        m_motorR.setInverted(true);
        m_motorL.setInverted(false);

        m_motorL.getEncoder().setPosition(0);
        m_motorL.getEncoder().setPosition(0);
    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        inputs.angleRad = m_motorL.getEncoder().getPosition() * kMOTOR_TO_OUTPUT_RATIO * Math.PI * 2 + KANGLE_OFFSET_RAD;
        inputs.velocityRadSec = m_motorL.getEncoder().getVelocity() / 60.0 * kMOTOR_TO_OUTPUT_RATIO * Math.PI * 2;

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
