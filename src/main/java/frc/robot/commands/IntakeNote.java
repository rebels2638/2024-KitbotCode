package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.roller.Roller;
import frc.robot.subsystems.shooter.pivot.Pivot;

public class IntakeNote extends SequentialCommandGroup {
    public IntakeNote (Pivot pivot, Roller roller, Intake intake) {
        this.addCommands(
            new ParallelDeadlineGroup(
                new InRoller(roller), 
                new RollerRun(roller), 
                new PivotBumper(pivot),
                new IntakeIn(intake)
            ),
            new WaitCommand(.1),
            new ParallelCommandGroup(
                new RollerStop(roller), 
                new IntakeStop(intake), 
                new PivotTurtle(pivot))
        );
    }
}
