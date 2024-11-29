package frc.robot.subsystems.shooter.roller;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.lib.util.RebelUtil;

import com.revrobotics.CANSparkMax;

public class RollerIOSparkMax implements RollerIO{
    
    private static final double kMOTOR_TO_OUTPUT_RATIO = 1/5.0;
    private CANSparkMax m_motor = new CANSparkMax(2, MotorType.kBrushless);
    private DigitalInput limSwitch = new DigitalInput(1);

    public RollerIOSparkMax() {
    }

    @Override
    public void updateInputs(RollerIOInputs inputs) {
        inputs.velocityRadSec = m_motor.getEncoder().getVelocity() / 60.0 * kMOTOR_TO_OUTPUT_RATIO * Math.PI * 2;
        inputs.voltage = m_motor.getAppliedOutput()*12;
        inputs.inRoller = !limSwitch.get();
        // System.out.println("LIMSWITCH: "+!limSwitch.get());
    }

    @Override
    public void setVoltage(double v) {
        m_motor.setVoltage(v);
    }

}
