# Movie Database Client

An open-source, ad-free Android client for a movie database. Part of the
[Open Substitutes Initiative](https://github.com/Open-Substitutes-Initiative), which builds free,
ad-free apps that replace existing but flawed ones.

**Status:** scaffold. The data source, features and architecture are not decided yet; they are meant to be
worked out with [BMad](#working-with-bmad).

## Stack

| Area | Choice |
|------|--------|
| Language | Kotlin 2.4.21 |
| UI | Jetpack Compose (BOM 2026.09.00) + Material 3 |
| Build | Android Gradle Plugin 9.4.0, Gradle 9.6.0 (wrapper), Gradle Kotlin DSL + version catalog |
| JDK | 17 |
| SDK | `minSdk` 26, `compileSdk` 37.2, `targetSdk` 36 |

AGP 9 has built-in Kotlin, so the `org.jetbrains.kotlin.android` plugin is intentionally not applied. Only the
Compose compiler plugin (`org.jetbrains.kotlin.plugin.compose`) is.

## Requirements

- JDK 17
- Android Studio, recent enough to support AGP 9.4.0
- Android SDK Platform 37.2 and Build Tools 36.0.0 (install via the SDK Manager)

## Build

```sh
./gradlew assembleDebug   # build a debug APK
./gradlew test            # run unit tests
```

Every push and pull request is built on GitHub Actions (`.github/workflows/android.yml`); the debug
APK is attached to each run under **Artifacts**.

## Releases

Pushing a tag `v<versionName>` (for example `v0.1.0`) runs `.github/workflows/release.yml`, which
builds a signed APK (to install directly) and AAB (for Google Play) and publishes them as a GitHub
Release. Before tagging, set `versionName` to match the tag and raise `versionCode`.

```sh
git tag v0.1.0 && git push origin v0.1.0
```

### One-time signing setup

Create the key **on your own machine** and keep the `.jks` file and passwords backed up somewhere
safe: every update of the app must be signed with the same key, and a lost key cannot be replaced.

```sh
keytool -genkeypair -v -keystore release.jks -storetype PKCS12 \
  -alias release -keyalg RSA -keysize 4096 -validity 10000
base64 -w0 release.jks > release.jks.b64   # macOS: base64 -i release.jks -o release.jks.b64
```

Add these under **Settings → Secrets and variables → Actions → New repository secret**:

| Secret | Value |
|---|---|
| `ANDROID_KEYSTORE_BASE64` | contents of `release.jks.b64` |
| `ANDROID_KEYSTORE_PASSWORD` | keystore password |
| `ANDROID_KEY_ALIAS` | `release` (the `-alias` above) |
| `ANDROID_KEY_PASSWORD` | key password (with PKCS12, the same as the keystore password) |

Then delete `release.jks.b64`. Never commit the keystore (`*.jks` is git-ignored).

## Working with BMad

[BMad Method](https://github.com/bmad-code-org/BMAD-METHOD) v6.12.4 is installed for Claude Code:

```sh
npx bmad-method install --modules bmm --tools claude-code
```

| Path | Contents |
|------|----------|
| `_bmad/` | BMad runtime and configuration (committed) |
| `.claude/skills/` | BMad skills for Claude Code |
| `_bmad-output/` | Documents BMad produces (briefs, PRD, architecture, stories) |

Open the repository in Claude Code and invoke the `bmad-help` skill to find out what to do next.

## Before the first release

- [ ] Choose the permanent application ID (currently `org.opensubstitutes.moviedatabaseclient`); it cannot change after publishing
- [ ] Add a launcher icon
- [ ] Choose a license
- [ ] Choose the movie data source and decide how API keys are kept out of version control
