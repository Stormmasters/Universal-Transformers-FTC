package pedroPathing.constants;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Encoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
public class HardwareConstants {
    public static String intakeMotor = "IM";
    public static String extensionMotor = "EM";
    public static String liftMotor1 = "LM1";
    public static String liftMotor2 = "LM2";
    public static String frontLeftMotor = "FL";
    public static String backLeftMotor = "BL";
    public static String frontRightMotor = "FR";
    public static String backRightMotor = "BR";
    public static String backLeftEnc = "BL";
    public static double backLeftEncDirection = Encoder.REVERSE; // double type because Encoder.REVERSE is a double under the hood for some reason
    public static String frontRightEnc = "FR";
    public static double frontRightEncDirection = Encoder.FORWARD;
    public static String frontLeftEnc = "FL";
    public static double frontLeftEncDirection = Encoder.FORWARD;
    public static String iServo1 = "IS1";
    public static String iServo2 = "IS2";
}
