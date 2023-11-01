package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class DrivingMech extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
//    DcMotor winch;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        backLeft = hardwareMap.get(DcMotor.class, "bl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        backRight = hardwareMap.get(DcMotor.class, "br");
//        winch = hardwareMap.get(DcMotor.class, "winch");


        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double rotate = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            frontLeft.setPower(+drive + strafe + rotate);
            backLeft.setPower(+drive - strafe + rotate);
            frontRight.setPower(+drive - strafe - rotate);
            backRight.setPower(+drive + strafe - rotate);

        }


    }
}
