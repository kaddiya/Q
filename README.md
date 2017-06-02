# Q
a JVM based poor man's In Memory Q supporting the following features  
1.One Producer Multiple Consumers.  
2.Bounded queue.  
3.Dependencies between consumers define the message consumption pattern.   


# Requirements:  
1.To run this locally all one needs is a working installation of JDK 8.  

2.Clone this repo.  

3.In the root directory run `./gradlew clean install`.This will build the code and install the client libs into the local `~/.m2` folder.  

4.Set the following env variables in your `~/.bash_profile`  
  - `BROKER_HOST` (Describing the address of the broker)  
  - `BROKER_PORT` (Describing the port to run at)  
  
5.To run the Broker run `./gradlew clean appRun`.  
The broker will run at the specified port and host.

# Specifications:  
1. Platform - Groovy/Java 8  
2. Build tool - [Gradle](https://gradle.org)
3. DI tool - [Google Guice](https://github.com/google/guice)
4. Web F/w - [Gravy](https://github.com/kaddiya/gravy) based on Restling and [Restlet](https://restlet.com).

# Clients  
1.Sample Usage is described in the example project [here](https://github.com/kaddiya/QSample/)  


