package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "SimpleMecanumAuto")
public class RRtest1 extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Start pose (x, y, heading in radians)
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        // Build a simple trajectory: drive forward 24", then strafe left 12"
        Trajectory2Sequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                .forward(24)
                .strafeLeft(12)
                .turn(Math.toRadians(90))
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(trajSeq);
    }
}

