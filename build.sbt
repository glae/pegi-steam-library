name                := "pegi-steam-library"
version             := "0.1"
scalaVersion        := "2.12.6"
libraryDependencies :=  Seq(
                            "com.softwaremill.sttp" %% "core" % "1.3.0",
                            "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
                            "ch.qos.logback" % "logback-classic" % "1.2.3",
                            "org.scalatest" %% "scalatest" % "3.0.5" % "test",
                        )
