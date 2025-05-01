package pedroPathing.utils.runnables;

import static com.pedropathing.pathgen.MathFunctions.clamp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.utils.functions.Logger;

public class SlidesRunnable implements Runnable {
    private final DcMotorEx slideMotor;
    private boolean isReversed;
    private double targetPosition;
    private final double kP, kI, kD;
    private double maxPower = 0.8;
    private double error = 0, lastError = 0, integral = 0, derivative = 0;

    public SlidesRunnable(DcMotorEx slideMotor, double initialTarget, double kP, double kI, double kD, boolean isReversed) {
        this.slideMotor = slideMotor;
        this.targetPosition = initialTarget;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.isReversed = isReversed;
    }

    public void setMaxPower(double maxPower){
        this.maxPower = maxPower;
    }
    public void setTarget(double newTarget){
        Logger.info("Setting target to " + newTarget);
        this.targetPosition = newTarget;
    }
    public double getTargetPosiion(){
        return targetPosition;
    }
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            error = targetPosition - slideMotor.getCurrentPosition();
            if (Math.abs(error) < 50) {
                integral += error;
                integral *= 0.9;
                integral = clamp(integral, -5000, 5000);
            }
            else {
                integral = 0;
            }

            derivative = error - lastError;
            lastError = error;

            double power = kP * error + kI * integral + kD * derivative;

            if (Math.abs(error) < 50){
                power /= 4;
            }
            if (Math.abs(error) < 20){
                power /= 4;
            }
            // these if statements definitely not optimal, I'll admit
            // better tuned PID constants coming soonish

            slideMotor.setPower(clamp(power, -maxPower, maxPower));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                slideMotor.setPower(0);
                Logger.warn("Interrupted, setting motor to zero and exiting");
                break;
            }
        }
    }
}