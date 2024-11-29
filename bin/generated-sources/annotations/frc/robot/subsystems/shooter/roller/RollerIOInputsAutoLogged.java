package frc.robot.subsystems.shooter.roller;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class RollerIOInputsAutoLogged extends RollerIO.RollerIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("VelocityRadSec", velocityRadSec);
    table.put("InRoller", inRoller);
    table.put("Voltage", voltage);
  }

  @Override
  public void fromLog(LogTable table) {
    velocityRadSec = table.get("VelocityRadSec", velocityRadSec);
    inRoller = table.get("InRoller", inRoller);
    voltage = table.get("Voltage", voltage);
  }

  public RollerIOInputsAutoLogged clone() {
    RollerIOInputsAutoLogged copy = new RollerIOInputsAutoLogged();
    copy.velocityRadSec = this.velocityRadSec;
    copy.inRoller = this.inRoller;
    copy.voltage = this.voltage;
    return copy;
  }
}
