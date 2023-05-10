# Time Zone Database Builder

## What is this?

This repo uses a GitHub Action Workflow to periodically check for new [Time Zone Database](https://www.iana.org/time-zones) releases. When a new release is found it will download the Time Zone Database release files and generate the artifacts necessary for updating a Java installation's internal Time Zone Database.

The first artifact generated is a rearguard format tarball of the Time Zone Database that can be used as input to tools like [ZIUpdater](https://www.azul.com/products/components/ziupdater-time-zone-tool/) which will update an existing Java installation's internal Time Zone Database.

The second artifact generated is the final tzdb.dat Time Zone Database data file for people who want to skip using tools like ZIUpdater and just download the final tzdb.dat file.

## Contents of this repo

- `.github/workflows/build-latest-tzdb.yml` is the GitHub Action Workflow that checks for new releases and generates the artifacts.
- `test` is a directory which contains some test Java code used during the build process to test the Time Zone Database.
- `releases` is a directory which contains the generated artifacts described above after each build of a new Time Zone Database release.
- `ziupdater` is a directory which contains a cached copy of the ZIUpdater tool which the GitHub Action Workflow script uses to create the final tzdb.dat file.
- `last_processed_version` is used to track the last version number of the Time Zone Database that was processed to avoid unecessary builds during periodic checks for new releases.
- `last_run_timestamp` is used to track the last time an update check was performed.
