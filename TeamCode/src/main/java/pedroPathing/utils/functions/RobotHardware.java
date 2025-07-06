package pedroPathing.utils.functions;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

import pedroPathing.constants.HardwareConstants;

public class RobotHardware {
    public final List<LynxModule> hubs;

    public final DcMotorEx intakeMotor;
    public final DcMotorEx hangMotor;
    public final DcMotorEx extensionMotor;
    public final DcMotorEx liftMotor;
    public final DcMotorEx FL, BL, FR, BR;
    public RobotHardware(HardwareMap hardwareMap) {
        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : hubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        // Intake motor
        intakeMotor = hardwareMap.get(DcMotorEx.class, HardwareConstants.intakeMotor);
        intakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Hang motor
        hangMotor = hardwareMap.get(DcMotorEx.class, HardwareConstants.hangMotor);
        hangMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        hangMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        hangMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        // Extension motor
        extensionMotor = hardwareMap.get(DcMotorEx.class, HardwareConstants.extensionMotor);
        extensionMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Lift motor
        liftMotor = hardwareMap.get(DcMotorEx.class, HardwareConstants.liftMotor);
        liftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Drive motors
        FL = hardwareMap.get(DcMotorEx.class, HardwareConstants.frontLeftMotor);
        BL = hardwareMap.get(DcMotorEx.class, HardwareConstants.backLeftMotor);
        FR = hardwareMap.get(DcMotorEx.class, HardwareConstants.frontRightMotor);
        BR = hardwareMap.get(DcMotorEx.class, HardwareConstants.backRightMotor);
    }

    public void clearBulkCache() {
        for (LynxModule module : hubs) {
            module.clearBulkCache();
        }
    }
}