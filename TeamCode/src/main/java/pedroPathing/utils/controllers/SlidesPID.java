package pedroPathing.utils.controllers;

import static com.pedropathing.pathgen.MathFunctions.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import pedroPathing.utils.functions.Logger;
import java.lang.Math;
public class SlidesPID {
    private final DcMotorEx slideMotor, slideEnc;
    private double targetPosition;
    private final double kP, kD;
    private double maxPower = 0.8, liftPower;
    private double error = 0, lastError = 0, derivative = 0;
    private int currentPosition;
    public SlidesPID(DcMotorEx slideMotor, DcMotorEx slideEnc, double initialTarget, double kP, double kD, DcMotorSimple.Direction direction, double maxPower) {
        this.slideMotor = slideMotor;
        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        slideMotor.setDirection(direction);
        this.slideEnc = slideEnc;
        this.targetPosition = initialTarget;
        this.kP = kP;
        this.kD = kD;
        this.maxPower = maxPower;
    }
    public void setMaxPower(double maxPower){
        this.maxPower = maxPower;
    }
    public void setTarget(double newTarget){
        Logger.info("Setting target to " + newTarget);
        this.targetPosition = newTarget;
    }
    public void resetSlides(){
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int getCurrentPosition(){
        return slideMotor.getCurrentPosition();
    }

    public void update() {
        error = targetPosition - slideEnc.getCurrentPosition();

        derivative = error - lastError;
        lastError = error;

        double power = kP * error + kD * derivative;

        liftPower = clamp(power, -maxPower, maxPower);
        slideMotor.setPower(liftPower);

        /*if (Math.abs(slideMotor.getPower()) < 0.05 && Math.abs(error) < 40){
            slideMotor.setPower(0);
        }
        else { slideMotor.setPower(liftPower); }*/
    }
}