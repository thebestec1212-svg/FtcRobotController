package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MainGuts {

    private static DcMotorEx shooter, passer;

    private static double shooterRPM;
    private static double maxPasser;

    public void initGuts(HardwareMap hardwareMap, double ShooterRPM, double maxPasser){

        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        passer = hardwareMap.get(DcMotorEx.class, "passer");

        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        MainGuts.shooterRPM = ShooterRPM;
        MainGuts.maxPasser = maxPasser;
    }

    public void updateShooter(double ShooterRPM){
        MainGuts.shooterRPM = ShooterRPM;
    }

    private enum passerStateM{
        MOTOR_ON(maxPasser),
        MOTOR_OFF(0.0),
        MOTOR_REVERSE(maxPasser*-1);

        public final double power;

        passerStateM(double power) {
            this.power = power;
        }
    }

    private enum shooterStateM{
        MOTOR_ON(shooterRPM),
        MOTOR_OFF(0.0),
        MOTOR_REVERSE(shooterRPM*-1);

        public final double power;

        shooterStateM(double power) {
            this.power = power;
        }
    }

    private  passerStateM passerState = passerStateM.MOTOR_OFF;
    private shooterStateM shooterState = shooterStateM.MOTOR_OFF;

    public void togglePasser(){

        if (passerState == MainGuts.passerStateM.MOTOR_OFF) {
            passerState = MainGuts.passerStateM.MOTOR_ON;

        } else {
            passerState = MainGuts.passerStateM.MOTOR_OFF;
        }

        passer.setPower(passerState.power);
    }

    public void toggleShooter(){
        if (shooterState == shooterStateM.MOTOR_OFF) {
            shooterState = shooterStateM.MOTOR_ON;

        } else {
            shooterState = shooterStateM.MOTOR_OFF;
        }

        shooter.setVelocity(shooterState.power);
    }

}
