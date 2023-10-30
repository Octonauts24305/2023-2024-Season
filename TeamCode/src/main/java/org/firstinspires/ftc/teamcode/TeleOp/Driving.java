package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Utils.GamepadEvents;

@TeleOp
public class Driving extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor winch;
    GamepadEvents controller1;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        backLeft = hardwareMap.get(DcMotor.class, "bl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        backRight = hardwareMap.get(DcMotor.class, "br");
        winch = hardwareMap.get(DcMotor.class, "winch");
        controller1 = new GamepadEvents(gamepad1);

        double drive = -controller1.left_stick_y;
        double strafe = controller1.left_stick_x;
        double rotate = controller1.right_stick_x;

        frontLeft.setPower(drive + strafe + rotate);
        backLeft.setPower(drive - strafe + rotate);
        frontRight.setPower(drive - strafe - rotate);
        backRight.setPower(drive + strafe - rotate);

        

    }
}
