# GECo4J [![](https://jitpack.io/v/VSETH-GECO/GECo4J.svg)](https://jitpack.io/#VSETH-GECO/GECo4J) [![Jenkins](https://jenkins.stammgruppe.eu/job/GECo4J/job/master/badge/icon)](https://jenkins.stammgruppe.eu/blue/organizations/jenkins/GECo4J/activity?branch=master)
An Android compatible, [reactive](https://projectreactor.io/) Java wrapper for the [GECo API](https://geco.ethz.ch/api/) written in Java 8.

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
    	<version>0.9.5</version> <!-- Or use any other version including commit hashes -->
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
* [Latest Release Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/0.9.5/javadoc/)
* [Latest Master Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/master-SNAPSHOT/javadoc/)
* [Latest Dev Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/dev-SNAPSHOT/javadoc/)

Alternatively you can fetch the Javadocs of a specific version or commit by pasting the wanted version into this URL:
`https://jitpack.io/com/github/VSETH-GECO/GECo4J/<version>/javadoc/`

#### Tutorial
The first thing you have to do, is to construct a `DefaultGECo4JClient` using your API token:
```java
GECoClient gecoClient = new DefaultGECoClient(<API Token>);
```
Then you can look at the [Javadocs](https://jitpack.io/com/github/VSETH-GECO/GECo4J/0.9.5/javadoc/) to see what you can do with a `GECoClient`.
For example print the titles of the first page of events:
```java
class Example {
    public static void main(String[] args){
        GECoClient gecoClient = new DefaultGECoClient("this is your token");
        
        // Print the titles of the first page of news
        gecoClient.getNews(1).subscribe(news -> System.out.println(news.getTitle()));
    }
}
```
Keep in mind that all function calls are non-blocking, if you don't use `.block()`. Thus, the program could terminate before your function completes.

##### Error Handling
Here is a more complicated example with error handling which prints the borrowed items of the lan user with ID = 1:
```java
class Example {
    public static void main(String[] args){
        // Get the lan user with ID = 1
        gecoClient.getLanUserByID(1L).doOnError(e -> {
            // Check if it's simply a NOT_FOUND error
            if (e instanceof APIException) {
                if (((APIException) e).getError() == APIException.Error.NOT_FOUND) {
                    System.out.println("User not found");
                    return;
                }
            }
            // If it's another error
            e.printStackTrace();
        // Get the borrowed items of the lan user and collect them as a list
        }).subscribe(lanUser -> lanUser.getBorrowedItems().collectList().subscribe(l -> {
            if (l.isEmpty()) {
                System.out.printf("%s has no borrowed items.\n", lanUser.getFullName());
            } else {
                System.out.printf("Items borrowed by %s: %s", lanUser.getFullName(), l.get(0).getName());
                for (int i = 1; i < l.size(); i++) {
                    System.out.print(", " + l.get(i).getName());
                }
                System.out.println();
            }
        }));
    }
}
```
You usually want to handle `APIException`'s since they are not fatal and only exist to inform you about the state of the requested resource.
If a function emits an `APIException`, it is written in the documentation which and when they are emitted.

Other exceptions such as `GECo4JException` or `IOException` may occur, if the input was malformed or if there are connection problems. 
Note that `GECo4JException`'s only occur, if the request could be completed but we got an unexpected response from the API.