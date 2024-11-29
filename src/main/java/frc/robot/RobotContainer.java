package frc.robot;

import org.ejml.dense.row.decomposition.eig.SymmetricQRAlgorithmDecomposition_DDRM;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.FollowTarget;
import frc.robot.commands.IntakeNote;
import frc.robot.commands.PivotBumper;
import frc.robot.commands.PivotRaw;
import frc.robot.commands.PivotTurtle;
import frc.robot.commands.RollerRun;
import frc.robot.commands.ShootNote;
import frc.robot.commands.ShooterStop;
import frc.robot.commands.ShooterWindup;
// import frc.robot.commands.RollerRun;
// import frc.robot.commands.RollerStop;
// import frc.robot.commands.ShooterStop;
// import frc.robot.commands.ShooterWindup;
import frc.robot.lib.input.XboxController;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.flywheel.Flywheel;
// import frc.robot.subsystems.shooter.flywheel.Flywheel;
import frc.robot.subsystems.shooter.pivot.Pivot;
// import frc.robot.subsystems.shooter.roller.Roller;
import frc.robot.subsystems.shooter.roller.Roller;

public class RobotContainer {
  public static RobotContainer instance = null;

  private final XboxController xboxTester;
  private final XboxController xboxDriver;
  private final XboxController xboxOperator;

  private final Flywheel flywheelSubsystem = new Flywheel();
  private final Pivot pivotSubsystem = new Pivot();
  private final Roller rollerSubsystem = new Roller();
  private final Intake intakeSubsystem = new Intake();

  public RobotContainer() {
    this.xboxTester = new XboxController(1);
    this.xboxOperator = new XboxController(2);
    this.xboxDriver = new XboxController(3);

    // pivotSubsystem.setDefaultCommand(new PivotRaw(pivotSubsystem, xboxDriver));
    // this.xboxDriver.getLeftBumper().onTrue(new ShooterWindup(flywheelSubsystem));
    // this.xboxDriver.getRightBumper().onTrue(new ShooterStop(flywheelSubsystem));
    // this.xboxDriver.getAButton().onTrue(new RollerRun(rollerSubsystem));
    // this.xboxDriver.getBButton().onTrue(new RollerStop(rollerSubsystem));
    this.xboxDriver.getAButton().onTrue(new RollerRun(rollerSubsystem));
    this.xboxDriver.getBButton().onTrue(new IntakeNote(pivotSubsystem, rollerSubsystem, intakeSubsystem));
    
    this.xboxDriver.getXButton().onTrue(new PivotBumper(pivotSubsystem));
    this.xboxDriver.getYButton().onTrue(new PivotTurtle(pivotSubsystem));
    
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
      instance = new RobotContainer();
    }
    return instance;
  }
  
  public Command getAutonomousCommand() {
    return new InstantCommand(() -> System.out.println("balls"));
  }
}

