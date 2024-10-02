package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.FollowTarget;
import frc.robot.commands.RollerRun;
import frc.robot.commands.RollerStop;
import frc.robot.commands.ShooterStop;
import frc.robot.commands.ShooterWindup;
import frc.robot.lib.input.XboxController;
import frc.robot.subsystems.shooter.flywheel.Flywheel;
import frc.robot.subsystems.shooter.pivot.Pivot;
import frc.robot.subsystems.shooter.roller.Roller;

public class RobotContainer {
  public static RobotContainer instance = null;

  private final XboxController xboxTester;
  private final XboxController xboxDriver;
  private final XboxController xboxOperator;

  private final Flywheel flywheelSubsystem = new Flywheel();
  private final Pivot pivotSubsystem = new Pivot();
  private final Roller rollerSubsystem = new Roller();

  public RobotContainer() {
    this.xboxTester = new XboxController(1);
    this.xboxOperator = new XboxController(2);
    this.xboxDriver = new XboxController(3);

    pivotSubsystem.setDefaultCommand(new FollowTarget(pivotSubsystem));
    this.xboxDriver.getLeftBumper().onTrue(new ShooterWindup(flywheelSubsystem));
    this.xboxDriver.getRightBumper().onTrue(new ShooterStop(flywheelSubsystem));
    this.xboxDriver.getAButton().onTrue(new RollerRun(rollerSubsystem));
    this.xboxDriver.getBButton().onTrue(new RollerStop(rollerSubsystem));
    
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

