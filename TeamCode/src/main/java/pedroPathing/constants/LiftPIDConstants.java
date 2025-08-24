package pedroPathing.constants;
import com.acmerobotics.dashboard.config.Config;

@Config
public class LiftPIDConstants {
    public static double retractedPosition = 20;
    public static double extendedPosition = 800;
    public static double kP = 0.006;
    public static double kD = 0.08;
    public static double maxPower = 0.05;
}
