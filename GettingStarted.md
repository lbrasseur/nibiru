# Getting Started #
This section es extracted from [reference documentation](http://nibiru.googlecode.com/git/docs/Nibiru_Reference_en.pdf). This way, lazy people can try the framework without reading the documentation :)

# Details #
## Required software ##
  1. Java (http://www.java.com/en/download/).
  1. Eclipse (http://www.eclipse.org/).
  1. Maven (http://maven.apache.org/).
  1. A GIT client (http://git-scm.com/). We use [EGit](http://eclipse.org/egit/).

## Installation ##
  1. Clone the project as explained in http://code.google.com/p/nibiru/source/checkout.
  1. Run mvn eclipse:eclipse from root directory in order to build the Eclipse project from Maven les and downloading target platform JARS.
  1. A target platform, with all the dependencies, will be created in ar.com.oxen.sample/ar.com.oxen.sample.targetplatform/target/platform. If not (or if you change the dependencies) go to that project and run mvn compile in order to rebuild the target platform from Maven dependencies.
  1. Import the projects into Eclipse. You must create a M2\_REPO classpath variable pointing to the m2/repository directory in your home directory.
  1. At preferences menu, activate the Nibiru Sample target platform. Select reload option in order to recognize the downloaded JARs.
  1. Run the OSGi application launch called "Nibiru Sample". By default, Eclipse adds all the plugin (OSGi) projects which are open in the workspace, so even if there is a JAR with the same module, the source project takes precedence.

If you don't want to download the full source code, you can run it downloading a precompiled sample app: http://servidor.oxen.com.ar/artifactory/libs-release-local/ar/com/oxen/nibiru/sample/ar.com.oxen.nibiru.sample.springwebapp/0.2/ar.com.oxen.nibiru.sample.springwebapp-0.2.war. This sample runs into a standard servlet container (non-OSGi).

## Sample project ##
Running the sample application will create an [H2 database](http://www.h2database.com/) in a directory called nibiruDb inside your home directory. Windows users should modify the ar.com.oxen.nibiru.sample/ar.com.oxen.nibiru.sample.datasource.fragment/src/main/resources/database.properties fille in order to specify the database location.
The sample application uses a dummy authentication service. Log-in with user
guest, password guest.