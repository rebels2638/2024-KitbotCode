package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.shooter.flywheel.Flywheel;
import frc.robot.subsystems.shooter.pivot.Pivot;
import frc.robot.subsystems.shooter.roller.Roller;

public class ShootNote extends SequentialCommandGroup {
    public ShootNote (Pivot pivot, Flywheel flywheel, Roller roller) {
        this.addCommands(
        new PivotBumper(pivot), 
        new ParallelDeadlineGroup(new WaitCommand(2), new ShooterWindup(flywheel)),
        new ParallelDeadlineGroup(new WaitCommand(2), new RollerRun(roller)),
        new ShooterStop(flywheel),
        new PivotTurtle(pivot));
    }
}
