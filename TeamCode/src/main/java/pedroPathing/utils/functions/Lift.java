package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import pedroPathing.constants.LIftPIDConstants;
import pedroPathing.utils.controllers.SlidesPID;

public class Lift {
    private boolean isExtended = false, isInitialized = false;
    private SlidesPID liftPID;
    private double armRetracted = 0, armExtended = 1;
    ServoImplEx arm;
    public boolean isExtended(){
        return isExtended;
    }
    public void resetSlides(){
        liftPID.resetSlides();
    }
    public boolean initialize(DcMotorEx liftMotor){
        if (!isInitialized){
            Logger.info("Init started");
            //arm = hardwareMap.get(ServoImplEx.class, "arm");
            isInitialized = true;
            liftPID = new SlidesPID(liftMotor, 0, LIftPIDConstants.kP, LIftPIDConstants.kD, false, LIftPIDConstants.maxPower);
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
            liftPID.setTarget(LIftPIDConstants.extendedPosition);
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
            liftPID.setTarget(LIftPIDConstants.retractedPosition);
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
        liftPID.update();
    }
}