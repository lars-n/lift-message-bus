val liftVersion = settingKey[String]("Full version number of the Lift Web Framework")
val liftEdition = settingKey[String]("Lift Edition (short version number to append to artifact name)")

name := "messagebus"

organization := "net.liftmodules"

version := "1.1-SNAPSHOT"

liftVersion := (liftVersion ?? "3.4.3").value

liftEdition := liftVersion.value.substring(0,3)

moduleName := name.value + "_" + liftEdition.value

scalaVersion := "2.13.6"

crossScalaVersions := Seq("2.13.6", "2.12.14")

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

//useGpg := true
//usePgpKeyHex("B41A0844")

//publishTo in ThisBuild := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

// For local deployment:
credentials += Credentials( file("sonatype.credentials") )

// For the build server:
credentials += Credentials( file("/private/liftmodules/sonatype.credentials") )

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