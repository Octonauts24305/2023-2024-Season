package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Utils.GamepadEvents;
/*
These lines of import statements bring in necessary libraries that people have written. We are using
these to access some of the framework, such as LinearOpMode, and some of the physical devices, such
as Servos.
 */


@TeleOp
//This is a type "TeleOp", meaning that the driver has control using a controller during this program.

public class Driving extends LinearOpMode { //This is a class named "Driving"
    DcMotor frontLeft, backLeft, frontRight, backRight, climb, lift; //Defining that the motors are plugged in on the robot
    Servo climbRelease, drone, claw, tilt; //Defining that the servos are plugged in on the robot
    boolean open, isClimbing; // two necessary "true or false" used for your claw and climb, in that order respectively
    GamepadEvents controller1; // a custom library made by hazen to use controller inputs more efficiently

    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Here, we assign values to all of the empty variables we set outside this "runOpMode()"
        We have to tell the control hub that we have things plugged in. To do so, we must
        "get" our motors and servos, in addition to other sensors we have plugged in from the
        hardware map, defining what we named it, and what kind of device it is. We can read
        each line as a sentence.

        For instance, for frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        can be read as "the value of frontLeft can be found when we get a DcMotorEx named
        "climbRelease" from the hardwareMap"

        We also reverse the direction of the left motors, because we have to account for
        how it is positioned on the robot.

        For each motor, we have to "call" STOP_AND_RESET_ENCODER. This is performed on the
        climb motor. The mode is a enum type. This is essentially a more advanced "true/false".
        We will not concern ourselves with this, except for understanding that we are setting
        the motor into a specific "mode", where it is able to perform specific tasks only while
        in that mode.

        Again, line 62 can be read as a sentence.
        "set the mode of the climb to STOP_AND_RESET_ENCODER"
         */
        controller1 = new GamepadEvents(gamepad1);

        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontLeft.setDirection(com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE);
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");

        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backRight = hardwareMap.get(DcMotorEx.class, "br");
        backRight.setDirection(com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE);

        climb = hardwareMap.get(DcMotorEx.class, "climb");
        climb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        climb.setDirection(com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE);
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        climbRelease = hardwareMap.get(Servo.class, "climbRelease");
        drone = hardwareMap.get(Servo.class, "drone");
        claw = hardwareMap.get(Servo.class, "claw");
        tilt = hardwareMap.get(Servo.class, "tilt");

        waitForStart();
        /*
        When we are all done, setting up our motors, servos, or whatever hardware we have plugged
        in, we call waitForStart(), which waits until the start button on the driver hub has been
        pressed.
         */

        /*
        This while loop will run until the program is stopped on the driver hub. We know this
        because it runs "while the opMode is active".
         */

        while (opModeIsActive()) {
            /*
            Here, we have 3 values that are associated with the controller.

            drive: moving forward and backward
            rotate: moving in a circle, with the axis of rotation being the center of the robot
            strafe: moving left and right

            We control these values by setting them to the different sticks on the controller. A
            special note is that we must negate any y stick. This is some problem with the
            controller, and that is how we deal with it.

             */
            double drive = -gamepad1.left_stick_y;
            double rotate = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            /*
            Our motors are controlled by a bit of math, adding a bit of drive, strafe, and rotate
            and moving all at once to achieve some sort of movement.
             */
            frontLeft.setPower(drive + strafe + rotate);
            backLeft.setPower(drive - strafe + rotate);
            frontRight.setPower(drive - strafe - rotate);
            backRight.setPower(drive + strafe - rotate);

            // bumper L/R claw toggle, A CLimb release, B drone, X climb toggle,
            // Dpad Up Claw down claw, trigger lif up/down
            double upPower = gamepad1.left_trigger;
            double downPower = -gamepad1.right_trigger;
            lift.setPower(upPower + downPower);

            if (controller1.right_bumper.onPress()) {
                if (open)
                    claw.setPosition(0);
                else
                    claw.setPosition(1);

                open = !open;
            }

            if (gamepad1.start) {
                climb.setPower(-1);
            }
            if (controller1.dpad_up.onPress()) {
                tilt.setPosition(0);
            }

            if (controller1.dpad_down.onPress()) {
                tilt.setPosition(1);
            }

            if (controller1.b.onPress()) {
                drone.setPosition(0.5); //release
            }

            if (controller1.a.onPress()) {
                climbRelease.setPosition(0.5); //release
            }

            if (controller1.x.onPress()) {
                if (!isClimbing) {
                    isClimbing = true;
                    double distance = 10; //in inches ( 10 or 12)
                    climb.setTargetPosition(7000);
                    climb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    climb.setPower(1);
                } else {
                    isClimbing = false;
                    climb.setPower(0);
                }
            }

            controller1.update();
        }

    }
}