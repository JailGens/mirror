# Release

1. Update the version number in `gradle.properties` based on the commit messages.
2. Update the README installation guide to include the updated version.
3. Commit the changes with the commit message `chore(release): release <version>`.
4. Create a tag for the version `v<version>`.
5. Push the commit and tag to GitHub.
6. Update the [user guide](https://github.com/JailGens/docs/blob/main/mirror/getting-started.md).
7. Wait until CI passes.
8. Unless it is a minor release, create a GitHub release with the patch notes, and the binaries.
9. Update the version to the next developmental version.
10. Commit the changes with the commit message `chore(release): prepare <version>`.
11. Push the changes.
