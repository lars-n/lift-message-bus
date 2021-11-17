import java.util.Properties

val globalSbtProperties = {
  val prop = new Properties()
  IO.load(prop, Path.userHome / ".sbt" / "config" / "global-sbt-config.properties")
  prop
}

val publishRepoUrl = globalSbtProperties.getProperty("publishRepoUrl")
val publishRepoUser = globalSbtProperties.getProperty("publishRepoUser")
val keyFile = Path.userHome / ".ssh" / "id_rsa"
val publishSshRepo = Resolver.ssh("publish repo", publishRepoUrl,22)(Resolver.ivyStylePatterns) as (publishRepoUser, keyFile) withPublishPermissions("0755")

lazy val liftVersion = settingKey[String]("Version number of the Lift Web Framework")
lazy val liftEdition = settingKey[String]("Lift Edition (short version number to append to artifact name)")

name := "messagebus"

organization := "net.liftmodules"

version := "1.1-SNAPSHOT"

liftVersion := { liftVersion ?? "3.4.3" }.value
liftEdition := { liftVersion apply { _.substring(0,3) } }.value

moduleName := name.value + "_" + liftEdition.value

scalaVersion := "2.13.6"

crossScalaVersions := Seq(scalaVersion.value, "2.12.14")

scalacOptions ++= Seq("-unchecked", "-deprecation")

Test / scalacOptions ++= Seq("-Yrangepos")

resolvers ++= Seq("sonatype-snapshots"      at "https://oss.sonatype.org/content/repositories/snapshots",
                  "sonatype-releases"       at "https://oss.sonatype.org/content/repositories/releases",
                  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
                 )

libraryDependencies ++= Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion.value  % "provided",
    "org.specs2"  %% "specs2-core" % "4.12.12"     % "test"
)

ThisBuild / publishTo := Some(publishSshRepo)

publishMavenStyle := true

Test / publishArtifact := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/pdyraga/lift-message-bus</url>
  <licenses>
    <license>
      <name>Apache 2.0 License</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:pdyraga/lift-message-bus.git</url>
    <connection>scm:git:git@github.com:pdyraga/lift-message-bus.git</connection>
  </scm>
  <developers>
    <developer>
      <id>piotrd</id>
      <name>Piotr Dyraga</name>
      <url>http://www.ontheserverside.com</url>
    </developer>
  </developers>
)