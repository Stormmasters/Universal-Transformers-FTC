package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.constants.ExtensionPIDConstants;
import pedroPathing.constants.HardwareConstants;
import pedroPathing.constants.LiftPIDConstants;
import pedroPathing.utils.controllers.SlidesPID;

public class Extension {
    private boolean isExtended = false, isInitialized = false;
    private SlidesPID extensionPID;
    private double targetPosition;
    Servo IS1, IS2;
    public boolean initialize(DcMotorEx extensionMotor, DcMotorEx extEnc, Servo IS1, Servo IS2){
        if (!isInitialized){
            Logger.info("Init started");
            isInitialized = true;
            extensionPID = new SlidesPID(extensionMotor, extEnc, 0, ExtensionPIDConstants.kP, ExtensionPIDConstants.kD, true, ExtensionPIDConstants.maxPower);
            Logger.info("Successfully initialized");
            this.IS1 = IS1;
            this.IS2 = IS2;
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
            extensionPID.setTarget(ExtensionPIDConstants.extendedPosition);
            isExtended = true;
            IS1.setPosition(HardwareConstants.iServo1Extended);
            IS2.setPosition(HardwareConstants.iServo2Extended);
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
            extensionPID.setTarget(ExtensionPIDConstants.retractedPosition);
            isExtended = false;
            IS1.setPosition(HardwareConstants.iServo1Retracted);
            IS2.setPosition(HardwareConstants.iServo2Retracted);
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

    public int getExtensionPosition(){
        return extensionPID.getCurrentPosition();
    }
    public boolean isExtended() {
        return isExtended;
    }
    public void update(){
        extensionPID.update();
    }
}