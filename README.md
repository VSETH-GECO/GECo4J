# GECo4J [![](https://jitpack.io/v/VSETH-GECO/GECo4J.svg)]() [![Jenkins](https://jenkins.stammgruppe.eu/job/GECo4J/job/master/badge/icon)](https://jenkins.stammgruppe.eu/blue/organizations/jenkins/GECo4J/activity?branch=master)
Java Interface for the [GECo API](https://geco.ethz.ch/api/) written in Java 8.

You can find the latest builds on [JitPack](https://jitpack.io/#VSETH-GECO/GECo4J) or here on GitHub.

## Adding as dependency

### Maven
Add the following to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
	
<dependencies>
    <dependency>
        <groupId>com.github.VSETH-GECO</groupId>
    	<artifactId>GECo4J</artifactId>
    	<version>0.9.1</version> <!-- Or use any other version including commit hashes -->
    </dependency>
</dependencies>
```
You can also use `master-SNAPSHOT` as version if you want to use the current development build or `dev-SNAPSHOT` if you
want even more cutting edge builds.

### Gradle / SBT / Leiningen
See [JitPack](https://jitpack.io/#VSETH-GECO/GECo4J) for more information on how to add this project as a dependency.

### Manually
You can get a shaded jar on [Jenkins](https://jenkins.stammgruppe.eu/blue/organizations/jenkins/GECo4J/activity) or [JitPack](https://jitpack.io/#VSETH-GECO/GECo4J) to manually add this project as a dependency.

## Usage
#### Resources
* [Latest Release Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/0.9.1/javadoc/)
* [Latest Master Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/master-SNAPSHOT/javadoc/)
* [Latest Dev Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/dev-SNAPSHOT/javadoc/)

Alternatively you can fetch the Javadocs of a specific version or commit by pasting the wanted version into this URL:
`https://jitpack.io/com/github/VSETH-GECO/GECo4J/<version>/javadoc/`

#### Tutorial
The first thing you have to do, is to construct a `GECo4JClient` using your API token:
```java
IGECoClient gecoClient = new GECoClient(<API Token>);
```
Then you can look at the [Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/0.9.1/javadoc/) to see what you can do with a `IGECoClient`.
For example print the titles of the first page of events:
```java
class Example {
    public static void main(String[] args){
        IGECoClient gecoClient = new GECoClient("this is your token");
        
        List<IEvent> events = gecoClient.getEvents(1);
        for (IEvent event : events) {
            System.out.println(event.getTitle());
        }
    }
}
```