package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Math.abs;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name= "Robot-Centric")
public class RobotCentric extends OpMode {

    DcMotor lf;
    DcMotor lb;
    DcMotor rf;
    DcMotor rb;

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
        telemetry.addData("initialization", "Ready");
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;
        double lfPower = y + x + rx;
        double lbPower = y - x + rx;
        double rfPower =-y + x + rx;
        double rbPower =-y - x + rx;
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
        telemetry.update();

    }
}