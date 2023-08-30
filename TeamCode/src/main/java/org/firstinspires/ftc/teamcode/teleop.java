package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import static java.lang.Math.abs;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name= "Field-Centric")
public class teleop extends OpMode {
    DcMotor lf;
    DcMotor lb;
    DcMotor rf;
    DcMotor rb;
    IMU imu;

    @Override
    public void init() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);
        telemetry.addData("initialization", "Ready");
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;
        if(gamepad1.x){
            imu.resetYaw();
        }
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) - y * Math.cos(-botHeading);
        double lfPower = rotY + rotX + rx;
        double lbPower = rotY - rotX + rx;
        double rfPower =-rotY + rotX + rx;
        double rbPower =-rotY - rotX + rx;
        double denominator = Math.max(abs(y) + abs(x) + abs(rx), 1.0);
        lf.setPower(lfPower/denominator);
        lb.setPower(lbPower/denominator);
        rf.setPower(rfPower/denominator);
        rb.setPower(rbPower/denominator);
        if(abs(gamepad1.left_stick_y) < 0.1 && abs(gamepad1.left_stick_x) < 0.1){
            lf.setPower(0);
            lb.setPower(0);
            rf.setPower(0);
            rb.setPower(0);
        }
        telemetry.addData("lfPower", lfPower/denominator);
        telemetry.addData("lfPower", lbPower/denominator);
        telemetry.addData("lfPower", rfPower/denominator);
        telemetry.addData("lfPower", rbPower/denominator);
        telemetry.addData("botHeading", botHeading);
        telemetry.update();

    }
}