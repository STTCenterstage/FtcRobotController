package org.firstinspires.ftc.teamcode.drive.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robotParts.DifferentialDrivetrain;
@Config
@TeleOp(group = "test")
public class swerveTest extends LinearOpMode {

    DifferentialDrivetrain drive = new DifferentialDrivetrain(this);
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drive.init();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drive.swervePID();
//            drive.swerveRobotCentric();
//            telemetry.addData("Front",drive.DTMotors[1].getPower());
//            telemetry.addData("back",drive.DTMotors[3].getPower());
//            telemetry.addData("dPos", -drive.DTMotors[1].getCurrentPosition() + drive.DTMotors[3].getCurrentPosition());
//            telemetry.addData("posF",drive.DTMotors[1].getCurrentPosition());
//            telemetry.addData("posB",drive.DTMotors[3].getCurrentPosition());
            telemetry.update();
        }
    }
}