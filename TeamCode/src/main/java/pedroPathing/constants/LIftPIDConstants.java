package pedroPathing.constants;
import com.acmerobotics.dashboard.config.Config;

@Config
public class LIftPIDConstants {
    public static double retractedPosition = 0;
    public static double extendedPosition = 500;
    public static double kP = 0.06;
    public static double kD = 0.8;
    public static double maxPower = 0.8;
}
