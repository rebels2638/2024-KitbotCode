package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class IntakeIn extends Command {
    private Intake intake;
    public IntakeIn(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setRadPerSec(Math.toRadians(120));
    }
}
