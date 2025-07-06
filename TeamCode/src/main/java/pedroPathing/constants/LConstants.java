package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelIMUConstants.forwardTicksToInches = 0.001;
        ThreeWheelIMUConstants.strafeTicksToInches = 0.001;
        ThreeWheelIMUConstants.turnTicksToInches = 0.006;
        ThreeWheelIMUConstants.leftY = 1;
        ThreeWheelIMUConstants.rightY = -1;
        ThreeWheelIMUConstants.strafeX = -2.5;
        ThreeWheelIMUConstants.leftEncoder_HardwareMapName = HardwareConstants.backLeftEnc;
        ThreeWheelIMUConstants.rightEncoder_HardwareMapName = HardwareConstants.frontRightEnc;
        ThreeWheelIMUConstants.strafeEncoder_HardwareMapName = HardwareConstants.frontLeftEnc;
        ThreeWheelIMUConstants.leftEncoderDirection = HardwareConstants.backLeftEncDirection;
        ThreeWheelIMUConstants.rightEncoderDirection = HardwareConstants.frontRightEncDirection;
        ThreeWheelIMUConstants.strafeEncoderDirection = HardwareConstants.frontLeftEncDirection;

        DriveEncoderConstants.forwardTicksToInches = 0.005;
        DriveEncoderConstants.strafeTicksToInches = 0.005;
        DriveEncoderConstants.turnTicksToInches = 0.0008;

        DriveEncoderConstants.robot_Width = 18;
        DriveEncoderConstants.robot_Length = 18;
    }
}




