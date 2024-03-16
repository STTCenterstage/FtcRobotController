package org.firstinspires.ftc.teamcode.robotParts;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class DifferentialDrivetrain {
    double x, y, r, gamepadTheta, pidR, pidB, TICKS_PER_ROTATION = 537.7/15*26;
    int dPosR,dPosB, redCurrentPos, blueCurrentPos, FBpos, FRpos,BBpos,BRpos, redHeadingGoal = 0, blueHeadingGoal = 0;
    public DcMotorEx FrontB,FrontR,BackB,BackR;
    public DcMotorEx[] DTMotors;
    String[] DTMotorNames = {"blue_front","red_front","blue_back","red_back"};
    int[] encoderPositions = {FBpos,FRpos,BBpos,BRpos};
    private final LinearOpMode myOpMode;
    HardwareMap map;
    Telemetry telemetry;
    public IMU imu;
    public static double pb = 0.0025, ib = 0.001, db = 0.00004, pr = 0.0025, ir = 0.001, dr = 0.00004;
    PIDController blueHeading = new PIDController(pb, ib, db), redHeading = new PIDController(pr,ir,dr);
    public DifferentialDrivetrain(LinearOpMode opmode) {
        myOpMode = opmode;
        //TODO: find out why this NPE
        map = opmode.hardwareMap;
        telemetry = opmode.telemetry;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    /**
     * This methods initialises the swerve drivetrain and the IMU and sets all the directions and modes to their correct settings.
     */
    public void init() {
        imu = myOpMode.hardwareMap.get(IMU.class, "imu");

        DTMotors = new DcMotorEx[]{FrontB, FrontR, BackB, BackR};
        for (int i = 0; i < DTMotors.length; i++) {
            DTMotors[i] = myOpMode.hardwareMap.get(DcMotorEx.class,DTMotorNames[i]);
            if (i < 2){
                DTMotors[i].setDirection(DcMotorSimple.Direction.FORWARD);
            } else {
                DTMotors[i].setDirection(DcMotorSimple.Direction.REVERSE);
            }
            DTMotors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DTMotors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));
        resetYaw();
    }

    public void swerveSimple(){
        double FWDB = myOpMode.gamepad1.left_stick_y;
        double ROTB = -0.55 * myOpMode.gamepad1.left_stick_x;
        double FWDR = myOpMode.gamepad1.right_stick_y;
        double ROTR = -0.55 * myOpMode.gamepad1.right_stick_y;
        DTMotors[0].setPower(FWDB + ROTB);
        DTMotors[1].setPower(FWDR + ROTR);
        DTMotors[2].setPower(FWDB - ROTB);
        DTMotors[3].setPower(FWDR - ROTR);
    }

    public void swerveLessSimple() {
        x = myOpMode.gamepad1.left_stick_x;
        y = -myOpMode.gamepad1.left_stick_y;
        r = Math.sqrt(x * x + y * y);
        int Bpos = DTMotors[3].getCurrentPosition(),Fpos = -DTMotors[1].getCurrentPosition();
        redCurrentPos = Fpos - Bpos;
        DTMotors[1].setPower(myOpMode.gamepad1.left_stick_y);
        DTMotors[3].setPower(myOpMode.gamepad1.right_stick_y);
    }

    public void swervePID(){
        x = myOpMode.gamepad1.right_stick_x;
        y = -myOpMode.gamepad1.right_stick_y;
        r = Math.sqrt(x * x + y * y);
        for (int i = 0; i < DTMotors.length; i++) {
            encoderPositions[i] = DTMotors[i].getCurrentPosition();
        }

        redCurrentPos = encoderPositions[1] - encoderPositions[3];
        blueCurrentPos = encoderPositions[0] - encoderPositions[2];

        if (myOpMode.gamepad1.x) {
            for (DcMotorEx motor : DTMotors) {
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
        }

        if (x >= 0 && y >= 0) {
            gamepadTheta = Math.atan(y / x);
        } else if (x<0) {
            gamepadTheta = Math.atan(y / x) + Math.PI;
        } else {
            gamepadTheta = Math.atan(y / x) + 2 * Math.PI;
        }

        if(r > 0.5){
            redHeadingGoal = (int) ((gamepadTheta-0.5*Math.PI)/(Math.PI)*TICKS_PER_ROTATION);
            blueHeadingGoal = (int) ((gamepadTheta-0.5*Math.PI)/(Math.PI)*TICKS_PER_ROTATION);
        }

        redHeading.setPID(pr,ir,dr);
        blueHeading.setPID(pb,ib,db);
        pidR = redHeading.calculate(redCurrentPos,redHeadingGoal);
        pidB = blueHeading.calculate(blueCurrentPos,blueHeadingGoal);

        DTMotors[0].setPower(pidB - r);
        DTMotors[1].setPower(pidR + r);
        DTMotors[2].setPower(-pidB - r);
        DTMotors[3].setPower(-pidR + r);
    }
    //TODO:documentation
    public void resetYaw() {
        imu.resetYaw();
    }
}