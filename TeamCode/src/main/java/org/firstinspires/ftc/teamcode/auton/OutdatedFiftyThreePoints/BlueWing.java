//package org.firstinspires.ftc.teamcode.auton.OutdatedFiftyThreePoints;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.robotParts.Outdated.OpenCVTrussIsLeft;
//import org.firstinspires.ftc.teamcode.robotParts.newAutonMethods;
//import org.firstinspires.ftc.teamcode.robotParts.Outdated.PixelManipulation;
//@Disabled
//@Autonomous(name = "BlueWing")
//public class BlueWing extends LinearOpMode {
//    newAutonMethods methods = new newAutonMethods(this);
//    OpenCVTrussIsLeft camera = new OpenCVTrussIsLeft(this);
//    PixelManipulation slides = new PixelManipulation(this);
//
//    public void runOpMode() {
//        methods.init(hardwareMap);
//        slides.init(hardwareMap, telemetry);
//        methods.calibrateEncoders();
//
//        camera.findScoringPosition();
//        // kijk of je assen kloppen de XYZ van imu, anders doet ie niks
//        waitForStart();
//        if (opModeIsActive()) {
//            int finalPos = camera.pos;
//            telemetry.addData("localPos", camera.pos);
//            if (finalPos == 0) {
//                methods.linearDriveY(-90);
//                methods.linearRotateToHeading(90);
//                methods.linearDriveX(-10);
//                methods.linearDriveY(-20);
//                methods.linearDriveY(25);
////                methods.driveY(40);
////                methods.driveX(-60);
//////                methods.driveY(-230);
//////                methods.driveX(100);
//////                methods.driveY(-20);
//////                slides.dropYellowPixel();
////                methods.driveY(20);
////                methods.driveX(50);
////                methods.driveY(-30);
//            } else if (finalPos == 1) {
//                methods.linearDriveY(-110);
//                methods.linearDriveY(20);
////                methods.driveX(-90);
////                methods.driveY(-100);
////                methods.rotateToHeading(90);
////                methods.driveY(-260);
////                methods.driveX(70);
////                methods.driveY(-20);
////                slides.dropYellowPixel();
////                methods.driveY(20);
////                methods.driveX(80);
////                methods.driveY(-30);
//            } else if (finalPos == 2){
//                methods.linearDriveX(-16);
//                methods.linearDriveY(-90);
//                methods.linearDriveY(30);
////                methods.driveX(50);
////                methods.driveY(-115);
////                methods.rotateToHeading(90);
////                methods.driveY(-260);
////                methods.driveX(40);
////                methods.driveY(-20);
////                slides.dropYellowPixel();
////                methods.driveY(-20);
////                methods.driveX(110);
////                methods.driveY(-30);
//            }
//            sleep(30000);
//
//        }
//    }
//}
