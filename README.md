#VIVO project template
This is a git repository template for working with and customizing [VIVO](http://vivoweb.org/).  It uses the [three tiered build approach](https://wiki.duraspace.org/display/VIVO/Building+VIVO+in+3+tiers) documented by the VIVO project.  The project source files (VIVO and Vitro) are tracked using [Git Submodules](http://git-scm.com/book/en/Git-Tools-Submodules).

For a more detailed explanation of setting up the VIVO environment, consult the [VIVO version 1.7 installation instructions](https://wiki.duraspace.org/display/VIVO/Installing+VIVO+release+1.7).

##Checking out the project and building VIVO in three tiers

###VIVO 1.7
~~~
$ git clone https://github.com/NUBIC/vivo-project.git vivo_project
$ cd vivo
$ git submodule init
# Pull in VIVO and Vitro.  This will take a few minutes.
$ git submodule update
# Check out specific versions of VIVO and Vitro
$ cd VIVO
$ git checkout maint-rel-1.7
$ cd ../Vitro
$ git checkout maint-rel-1.7
# Change back to vivo main directory
$ cd ..
# Copy default deploy.properties and edit
$ cp example.build.properties build.properties
$ cp example.runtime.properties runtime.properties
# Adjust build and runtime properties
# Create the data directory specified in build.properties if it doesn't exist.
# e.g. $ mkdir -p /usr/local/vivo_project/home
$ cp runtime.properties /usr/local/vivo_project/home
# Build and deploy VIVO
$ ant all
~~~

###VIVO development
~~~
$ git clone https://github.com/NUBIC/vivo-project.git vivo_project
$ cd vivo
$ git submodule init
# Pull in VIVO and Vitro.  This will take a few minutes.
$ git submodule update
# Check out specific versions of VIVO and Vitro
$ cd VIVO
$ git checkout develop
$ cd ../Vitro
$ git checkout develop
# Change back to vivo main directory
$ cd ..
# Copy default deploy.properties and edit
$ cp example.build.properties build.properties
$ cp example.runtime.properties runtime.properties
$ cp example.applicationSetup.n3 applicationSetup.n3
# Adjust build and runtime properties
# Create the data directory specified in build.properties if it doesn't exist.
# e.g. $ mkdir -p /usr/local/vivo_project/home
$ cp runtime.properties /usr/local/vivo_project/home
# Build and deploy VIVO
$ ant all
$ cp applicationSetup.n3 /usr/local/vivo_project/home/config/applicationSetup.n3
~~~

###Start Tomcat and Verify Build
~~~
# Start Tomcat
$ /usr/local/tomcat8/bin/startup.sh
# Tail logfile
$ tail -F /usr/local/tomcat8/logs/catalina.out
# Open browser and goto http://localhost:8080/vivo_project
# Login as root - using default initial password "rootPassword" 
# (cf. installation instructions pdf for VIVO 1.7)
~~~

##Benefits to this approach
 * local changes are separated from core code making upgrades easier.
 * using Git you can checkout any tagged release, build it with your local changes, and test it out.
 * using the steps above, you can quickly deploy VIVO to another machine.
 * you can use Git features, like [cherry-pick](http://www.vogella.com/articles/Git/article.html#cherrypick), to select bug fixes or enhancements that are not yet in a VIVO release and incorporate them into your implementation.
 * even if you plan on making few modifications, this can be a convenient and efficient way to manage your custom theme.

##Questions or comments
[Open an issue](https://github.com/lawlesst/vivo-project-template/issues) via the issue tracker.

