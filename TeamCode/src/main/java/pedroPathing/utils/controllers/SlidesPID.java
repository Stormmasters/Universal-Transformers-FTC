package pedroPathing.utils.controllers;

import static com.pedropathing.pathgen.MathFunctions.clamp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.utils.functions.Logger;

public class SlidesPID {
    private final DcMotorEx slideMotor, slideEnc;
    private boolean isReversed;
    private double targetPosition;
    private final double kP, kD;
    private double maxPower = 0.8;
    private double error = 0, lastError = 0, derivative = 0;
    private int currentPosition;
    public SlidesPID(DcMotorEx slideMotor, DcMotorEx slideEnc, double initialTarget, double kP, double kD, boolean isReversed, double maxPower) {
        this.slideMotor = slideMotor;
        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        if (isReversed){
            slideMotor.setDirection(DcMotorEx.Direction.REVERSE);
        }
        this.slideEnc = slideEnc;
        this.targetPosition = initialTarget;
        this.kP = kP;
        this.kD = kD;
        this.isReversed = isReversed;
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
        error = targetPosition - slideMotor.getCurrentPosition();

        derivative = error - lastError;
        lastError = error;

        double power = kP * error + kD * derivative;

        slideMotor.setPower(clamp(power, -maxPower, maxPower));
    }
}