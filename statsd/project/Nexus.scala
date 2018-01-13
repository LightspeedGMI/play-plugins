package steam.build

import sbt._
import Keys._
import java.net.URL
import scala.util.Properties

object Nexus {
	lazy val isSnapshot = Properties.envOrNone("NEXUS_SNAPSHOT") match { case Some(s) => true; case _ => false; }
	
	// NB: Port is 8888 since Sophos uses nexus default port of 8081.
	lazy val repoUrl = Properties.envOrElse("NEXUS_URL", "http://wilkins.corp.gmi.lcl:8888/nexus/content/repositories")
	lazy val repoUrlReleases = Properties.envOrElse("NEXUS_RELEASES", repoUrl + "/releases")
	lazy val repoUrlSnapshots = Properties.envOrElse("NEXUS_SNAPSHOTS", repoUrl + "/snapshots")
	
	lazy val repoUrls = Seq("GMI Nexus steam releases" at repoUrlReleases,"GMI Nexus steam snapshots" at repoUrlSnapshots)
	
	lazy val publishUrl = Some("GMI Nexus Repository" at (if (isSnapshot) repoUrlSnapshots else repoUrlReleases))

	lazy val host = Properties.envOrNone("NEXUS_URL").map({url => new URL(url).getHost}).getOrElse("wilkins.corp.gmi.lcl")
    lazy val username = Properties.envOrElse("NEXUS_USERNAME", "deployment")
    lazy val password = Properties.envOrElse("NEXUS_PASSWORD", "Use jenkins to publish" )

	def latest(version: String) = { if (isSnapshot) (version + "-SNAPSHOT") else version }
	def stable(version: String) = { version }
}
