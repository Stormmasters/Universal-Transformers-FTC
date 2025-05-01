package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ChassisController {
    private DcMotorEx slideMotor = null;
    private boolean isInitialized = false;
    ServoImplEx arm;
    private DcMotorEx FL, BL, FR, BR;
    public boolean update(double LX, double LY, double RX, double sensitivity){
        if (LX <= 1 && LY <= 1 && RX <= 1 && sensitivity <= 1 && isInitialized){
            FL.setPower((RX - LX - LY) * sensitivity);
            BL.setPower((RX + LX - LY) * sensitivity);
            FR.setPower((RX - LX + LY) * sensitivity);
            BR.setPower((RX + LX + LY) * sensitivity);
            return true;
        }
        else if (!isInitialized){
            Logger.warn("Chassis not yet initialized");
            return false;
        }
        else {
            Logger.warn("Invalid input value");
            return false;
        }
    }
    public void initialize(HardwareMap hardwareMap){
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        isInitialized = true;
    }
}
