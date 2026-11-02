package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;


@TeleOp(name = "Robot: USE THIS MECANUM", group = "Robot")
public class NewRobotTeleopMecanum extends OpMode {

    public double maxIntake, maxPasser, maxPower, maxSpeed = 1.0;
    public double shooterRPM = 3000;

    private DcMotor transfer, shooter;
    MecanumDriveFunctions MDF = new MecanumDriveFunctions();
    IntakeV3 intake = new IntakeV3();
    MainGuts mGuts = new MainGuts();
    @Override
    public void init() {
        MDF.initMecanum(hardwareMap, maxPower,maxSpeed);
        intake.initIntake(hardwareMap, maxIntake);
        mGuts.initGuts(hardwareMap, maxPasser, shooterRPM);

        transfer = hardwareMap.get(DcMotor.class, "transfer");
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        transfer.setDirection(DcMotor.Direction.REVERSE);
        shooter.setDirection(DcMotor.Direction.REVERSE);


    }

    public IMU imu;


    private boolean previousA, previousB, previousX, previousY, previousDU, previousDD = false;

    @Override
    public void loop() {

        //telementry
        String[] mechTele = MDF.getTelemtry();
        telemetry.addLine("Press x to reset Yaw");
        telemetry.addLine("Hold left bumper to drive in robot relative");
        telemetry.addLine("The left joystick sets the robot direction");
        telemetry.addLine("Moving the right joystick left and right turns the robot");
        telemetry.addLine("Aim robot the robot direction as you and press x to reset yaw");
        for (int i = 0; i < mechTele.length; i++){
            telemetry.addLine(mechTele[i]);
        }
        // If you press the A button, then you reset the Yaw to be zero from the way
        // the robot is currently pointing
        if (gamepad1.right_bumper) {
            imu.resetYaw();
        }

        // If you press the left bumper, you get a drive from the point of view of the robot
        // (much like driving an RC vehicle)
        if (gamepad1.left_bumper) {
            MDF.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        } else {
            MDF.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }


        //button Dbounces
        //declare current
        boolean currentA = gamepad2.a;
        boolean currentB = gamepad2.b|| gamepad1.b;
        boolean currentX = gamepad2.x;
        boolean currentY = gamepad2.y;
        boolean currentDU = gamepad2.dpad_up;
        boolean currentDD = gamepad2.dpad_down;

        if(currentDU && !previousDU){
            shooterRPM += 100;
        }
        else if(currentDD && !previousDD){
            shooterRPM -= 100;
        }
        mGuts.updateShooter(shooterRPM);



        //triggers
        if (currentA && !previousA){
            intake.toggleState();
        }
        else if (currentY && !previousY){
            mGuts.togglePasser();
        }
        else if (currentX && ! previousX){
            mGuts.toggleShooter();
        }
        //set up next frame
        previousA = currentA;
        previousB = currentB;
        previousX = currentX;
        previousY = currentY;
        previousDU = currentDU;
        previousDD = currentDD;




    }


}