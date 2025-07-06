package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.constants.ExtensionPIDConstants;
import pedroPathing.constants.LIftPIDConstants;
import pedroPathing.utils.controllers.SlidesPID;

public class Extension {
    private boolean isExtended = false, isInitialized = false;
    private SlidesPID extensionPID;
    private double targetPosition;
    public boolean initialize(DcMotorEx extensionMotor){
        if (!isInitialized){
            Logger.info("Init started");
            isInitialized = true;
            extensionPID = new SlidesPID(extensionMotor, 0, ExtensionPIDConstants.kP, ExtensionPIDConstants.kD, true, ExtensionPIDConstants.maxPower);
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
            extensionPID.setTarget(LIftPIDConstants.extendedPosition);
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
            extensionPID.setTarget(LIftPIDConstants.retractedPosition);
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
    public boolean isExtended() {
        return isExtended;
    }
    public void update(){
        extensionPID.update();
    }
}