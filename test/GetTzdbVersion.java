import java.time.zone.ZoneRulesProvider;

public class GetTzdbVersion {

    public static void main(String[] args) {

        System.out.println("TZ DB Version: " + ZoneRulesProvider.getVersions("UTC").lastEntry().getKey());
    }
}