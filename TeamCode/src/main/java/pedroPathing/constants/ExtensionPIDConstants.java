package pedroPathing.constants;
import com.acmerobotics.dashboard.config.Config;

@Config
public class ExtensionPIDConstants {
    public static double retractedPosition = -100;
    public static double extendedPosition = -340;
    public static double kP = 0.02;
    public static double kD = 0.008;
    public static double maxPower = 0.8;
}
