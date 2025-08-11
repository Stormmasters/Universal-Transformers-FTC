package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import pedroPathing.constants.LiftPIDConstants;
import pedroPathing.utils.controllers.SlidesPID;

public class Lift {
    private boolean isExtended = false, isInitialized = false;
    private SlidesPID liftPID1, liftPID2;
    private double armRetracted = 0, armExtended = 1;
    ServoImplEx arm;
    public boolean isExtended(){
        return isExtended;
    }
    public void resetSlides(){
        liftPID1.resetSlides();
    }
    public boolean initialize(DcMotorEx liftMotor1, DcMotorEx liftEnc1, DcMotorEx liftMotor2, DcMotorEx liftEnc2){
        if (!isInitialized){
            Logger.info("Init started");
            //arm = hardwareMap.get(ServoImplEx.class, "arm");
            isInitialized = true;
            liftPID1 = new SlidesPID(liftMotor1, liftEnc1,0, LiftPIDConstants.kP, LiftPIDConstants.kD, true, LiftPIDConstants.maxPower);
            liftPID2 = new SlidesPID(liftMotor2, liftEnc2,0, LiftPIDConstants.kP, LiftPIDConstants.kD, true, LiftPIDConstants.maxPower);
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
            //arm.setPosition(armExtended);
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
            //arm.setPosition(armRetracted);
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
    public void update(){
        liftPID1.update(); liftPID2.update();
    }
}