package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Utils.GamepadEvents;
/*
These lines of import statements bring in necessary libraries that people have written. We are using
these to access some of the framework, such as LinearOpMode, and some of the physical devices, such
as Servos.
 */

@TeleOp
//This is a type "TeleOp", meaning that the driver has control using a controller during this program.

public class ServoTester extends LinearOpMode { //This is a class named "ServoTester"
    Servo climbRelease, drone, lClaw, rClaw, LCTilt, RCTilt; //Defining that the servos are plugged in on the robot
    double pos = 0; // current position of the currently controlled servo
    GamepadEvents controller1; // a custom library made by hazen to use controller inputs more efficiently
    Servo[] servoArray = {climbRelease, drone, lClaw, rClaw, LCTilt, RCTilt}; // putting all the servos in an array
    /*
    We put the servos in an array so that we can iterate through the list - or basically scroll through
    all of them. This is how we can test several different servos in one go.
     */
    String[] servoArrayNames = {"climbRelease", "drone", "lClaw", "rClaw", "LCTilt", "RCTilt"};
    /*
    We define the name of the servos so that we can access them later, in our telemetry. We cannot define
     */
    int servoNum = 0; // shows which servo we are controlling in the above array (starting at 0, ending at 5)
    /*
    For instance, if we were controlling servoNum 0, we would be controlling climbRelease. If we were
    controlling servoNum 4, we would be controlling LCTilt, because we start counting at 0 in an array.
     */

    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Here, we assign values to all of the empty variables we set outside this "runOpMode()"
        We have to tell the control hub that we have things plugged in. To do so, we must
        "get" our servo from the hardware map, defining what we named it, and what kind of
        device it is. We can read each line as a sentence.

        For instance, for servoArray[0] (assigning a value for the first servo in our array,
        which is climbRelease), we can say, "the value of climbRelease can be found when we
        get a Servo named "climbRelease" from the hardwareMap".

        We also reverse the direction of the left tilting servo, because we have to account for
        how it is positioned on the robot.
         */
        controller1 = new GamepadEvents(gamepad1);
        servoArray[0] = hardwareMap.get(Servo.class, "climbRelease");
        servoArray[1] = hardwareMap.get(Servo.class, "drone");
        servoArray[2] = hardwareMap.get(Servo.class, "lClaw");
        servoArray[3] = hardwareMap.get(Servo.class, "rClaw");
        servoArray[4] = hardwareMap.get(Servo.class, "LCTilt");
        servoArray[4].setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
        servoArray[5] = hardwareMap.get(Servo.class, "RCTilt");


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
            If the right bumper on the controller is pressed (we use the method onPress() to check
            to see if it has been pressed once), then we will set the position of the servo
            to increment by 0.05.
             */

            if (controller1.right_bumper.onPress()) {
                pos += 0.05;
            }

            /*
            If the left bumper on the controller is pressed, however, we will decrement the
            position of the servo by 0.05.
             */
            if (controller1.left_bumper.onPress()) {
                pos -= 0.05;
            }

            /*
            If we press the "a" button on the controller, we change which servo we are controlling.
            We also change the position of the servo back to 0.
             */
            if (controller1.a.onPress()) {
                servoNum += 1;
                servoNum %= 6;
                pos = 0;
            }

            servoArray[servoNum].setPosition(pos);
            /*
            Since this code is constantly being run through until the program is ended, we use
            this statement to constatntly update which servo that we are controlling (denoted by
            servoNum), to the position (pos).
             */


            /*
            This is telemetry. Telemetry is used as a "console log" for our code. We can print
            out any information that we want. In this code, we show the position of the servo and
            the name of the servo we currently control.

             */
            telemetry.addData("pos:", pos);
            telemetry.addData("servo", servoArrayNames[servoNum]);

            /*
            Both the telemetry and controller need to be updated constantly to check if there have been
            any changes since the last loop.
             */
            telemetry.update();
            controller1.update();
        }

    }
}