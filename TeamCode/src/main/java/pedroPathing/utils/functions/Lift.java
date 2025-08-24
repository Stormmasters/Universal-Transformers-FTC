package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.HardwareConstants;
import pedroPathing.constants.LiftPIDConstants;
import pedroPathing.utils.controllers.SlidesPID;

public class Lift {
    private boolean isExtended = false, isInitialized = false;
    private SlidesPID liftPID1, liftPID2;
    private double armRetracted = 0, armExtended = 1;
    Servo ARM1, ARM2;
    public boolean isExtended(){
        return isExtended;
    }
    public void resetSlides(){
        liftPID1.resetSlides();
    }
    public boolean initialize(DcMotorEx liftMotor1, DcMotorEx liftEnc1, DcMotorEx liftMotor2, DcMotorEx liftEnc2, Servo ARM1, Servo ARM2){
        if (!isInitialized){
            Logger.info("Init started");
            isInitialized = true;
            liftPID1 = new SlidesPID(liftMotor1, liftEnc1,0, LiftPIDConstants.kP, LiftPIDConstants.kD, HardwareConstants.liftMotor1Dir, LiftPIDConstants.maxPower);
            liftPID2 = new SlidesPID(liftMotor2, liftEnc2,0, LiftPIDConstants.kP, LiftPIDConstants.kD, HardwareConstants.liftMotor2Dir, LiftPIDConstants.maxPower);
            this.ARM1 = ARM1;
            this.ARM2 = ARM2;
            ARM2.setDirection(Servo.Direction.REVERSE);
            Logger.info("Successfully initialized");
            return true;
        }
        else {
            Logger.error("Failed to init; Already initialized");
            return false;
        }
    }
    public boolean extend() {
        if (isInitialized && !isExtended){
            Logger.info("Extending slides...");
            liftPID1.setTarget(LiftPIDConstants.extendedPosition);
            liftPID2.setTarget(LiftPIDConstants.extendedPosition);
            ARM1.setPosition(HardwareConstants.arm1Extended);
            ARM2.setPosition(HardwareConstants.arm2Extended);
            isExtended = true;
            return true;
        }
        else if (!isInitialized){
            Logger.error("Failed to extend slides; Slides not initialized");
            return false;
        }
        else {
            Logger.error("Failed to extend slides; already extended");
            return false;
        }
    }
    public boolean retract() {
        if (isInitialized && isExtended){
            Logger.info("Retracting slides...");
            liftPID1.setTarget(LiftPIDConstants.retractedPosition);
            liftPID2.setTarget(LiftPIDConstants.retractedPosition);
            ARM1.setPosition(HardwareConstants.arm1Retracted);
            ARM2.setPosition(HardwareConstants.arm2Retracted);
            isExtended = false;
            return true;
        }
        else if (!isInitialized){
            Logger.error("Failed to retract slides; Slides not initialized");
            return false;
        }
        else {
            Logger.error("Failed to retract slides; already retracted");
            return false;
        }
    }
    public int getLiftPosition(){
        return liftPID1.getCurrentPosition();
    }
    public void update(){
        liftPID1.update(); liftPID2.update();
    }
}