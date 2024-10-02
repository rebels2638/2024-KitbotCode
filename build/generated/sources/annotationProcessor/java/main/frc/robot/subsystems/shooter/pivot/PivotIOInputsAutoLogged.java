package frc.robot.subsystems.shooter.pivot;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class PivotIOInputsAutoLogged extends PivotIO.PivotIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("AngleRad", angleRad);
    table.put("RTemp", rTemp);
    table.put("LTemp", lTemp);
    table.put("RAmps", rAmps);
    table.put("LAmps", lAmps);
    table.put("RVolts", rVolts);
    table.put("LbVolts", lbVolts);
  }

  @Override
  public void fromLog(LogTable table) {
    angleRad = table.get("AngleRad", angleRad);
    rTemp = table.get("RTemp", rTemp);
    lTemp = table.get("LTemp", lTemp);
    rAmps = table.get("RAmps", rAmps);
    lAmps = table.get("LAmps", lAmps);
    rVolts = table.get("RVolts", rVolts);
    lbVolts = table.get("LbVolts", lbVolts);
  }

  public PivotIOInputsAutoLogged clone() {
    PivotIOInputsAutoLogged copy = new PivotIOInputsAutoLogged();
    copy.angleRad = this.angleRad;
    copy.rTemp = this.rTemp;
    copy.lTemp = this.lTemp;
    copy.rAmps = this.rAmps;
    copy.lAmps = this.lAmps;
    copy.rVolts = this.rVolts;
    copy.lbVolts = this.lbVolts;
    return copy;
  }
}
