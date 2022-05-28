import java.time.zone.*;

public class CheckTimeZoneInformation {

    public static void main(String[] args) {

        new CheckTimeZoneInformation().runChecks(
            "America/Los_Angeles"
        );
    }

    public void runChecks(String... ianaZoneNames) {

        for (String ianaZoneName : ianaZoneNames) {
            runCheck(ianaZoneName);
        }
    }

    public void runCheck(String ianaZoneName) {

        ZoneId zoneId = ZoneId.of(ianaZoneName);

        ZonedDateTime nowZdt = ZonedDateTime.now(zoneId);
        Instant nowInstant = nowZdt.toInstant();

        ZoneRules zoneRules = ZoneRulesProvider.getRules(ianaZoneName, false);

        // check the defined transition rules, if there are any
        List<ZoneOffsetTransitionRule> zoneOffsetTransitionRules = zoneRules.getTransitionRules();
        if (zoneOffsetTransitionRules.isEmpty()) {
            System.out.println("List of ZoneOffsetTransitionRule is empty");
        } else {
            System.out.println("List of ZoneOffsetTransitionRule:");
            for (ZoneOffsetTransitionRule zoneOffsetTransitionRule : zoneOffsetTransitionRules) {
                System.out.println(String.format("\t%s", zoneOffsetTransitionRule));
            }
        }
        System.out.println();

        // check the previous and next transition based on now
        System.out.println("Checking zone rules' prev and next transition");
        ZoneOffsetTransition prevZoneOffsetTransition = zoneRules.previousTransition(nowInstant);
        if (prevZoneOffsetTransition != null) {
            System.out.println(
                String.format(
                    "Based on now, the prev transition was %s which was %s and 1 second earlier was %s %s with a transition duration of %s",
                    prevZoneOffsetTransition.toEpochSecond(),
                    ZonedDateTime.ofInstant(prevZoneOffsetTransition.getInstant(), zoneId).toString(),
                    prevZoneOffsetTransition.toEpochSecond() - 1,
                    ZonedDateTime.ofInstant(prevZoneOffsetTransition.getInstant().minusSeconds(1), zoneId).toString(),
                    prevZoneOffsetTransition.getDuration()
                )
            );
        } else {
            System.out.println("Based on now, there was no prev transition");
        }
        ZoneOffsetTransition nextZoneOffsetTransition = zoneRules.nextTransition(nowInstant);
        if (nextZoneOffsetTransition != null) {
            System.out.println(
                String.format(
                    "Based on now, the next transition  is %s which  is %s and 1 second earlier  is %s %s with a transition duration of %s",
                    nextZoneOffsetTransition.toEpochSecond(),
                    ZonedDateTime.ofInstant(nextZoneOffsetTransition.getInstant(), zoneId).toString(),
                    nextZoneOffsetTransition.toEpochSecond() - 1,
                    ZonedDateTime.ofInstant(nextZoneOffsetTransition.getInstant().minusSeconds(1), zoneId).toString(),
                    nextZoneOffsetTransition.getDuration()
                )
            );
        } else {
            System.out.println("Based on now, there  is no next transition");
        }
        System.out.println();
    }
}