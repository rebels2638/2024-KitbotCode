package frc.robot.subsystems.intake;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class IntakeIOInputsAutoLogged extends IntakeIO.IntakeIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("VelocityRadSec", velocityRadSec);
    table.put("Volts", volts);
    table.put("ReachedSetpoint", reachedSetpoint);
  }

  @Override
  public void fromLog(LogTable table) {
    velocityRadSec = table.get("VelocityRadSec", velocityRadSec);
    volts = table.get("Volts", volts);
    reachedSetpoint = table.get("ReachedSetpoint", reachedSetpoint);
  }

  public IntakeIOInputsAutoLogged clone() {
    IntakeIOInputsAutoLogged copy = new IntakeIOInputsAutoLogged();
    copy.velocityRadSec = this.velocityRadSec;
    copy.volts = this.volts;
    copy.reachedSetpoint = this.reachedSetpoint;
    return copy;
  }
}
