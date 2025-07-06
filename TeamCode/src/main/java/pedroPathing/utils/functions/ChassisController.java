package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ChassisController {
    private DcMotorEx slideMotor = null;
    private boolean isInitialized = false;
    ServoImplEx arm;
    private DcMotorEx FL, BL, FR, BR;
    // e.g, LX is left controller x axis
    public boolean update(double LX, double LY, double RX, double sensitivity){
        if (isInitialized){
            FL.setPower((-LY - LX - RX) * sensitivity); //reversed
            BL.setPower((-LY + LX - RX) * sensitivity); //reversed
            FR.setPower((LY + LX - RX) * sensitivity);
            BR.setPower((LY - LX - RX) * sensitivity);
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
    public void initialize(DcMotorEx FL, DcMotorEx BL, DcMotorEx FR, DcMotorEx BR){
        this.FL = FL;
        this.BL = BL;
        this.FR = FR;
        this.BR = BR;
        isInitialized = true;
    }
}
