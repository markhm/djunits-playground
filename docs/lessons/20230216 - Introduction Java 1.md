# Introduction to Java
_Entry by Mark - 16 Feb 2023._

## Starting point
- Learning is supposed to be challenging, but fun...!
- Communication and sharing feedback is important: We value our time.
- You move at your own pace and let me know what you find interesting.
- Feel free to ask (many) questions, e.g. by creating a new diary entry in this folder. Note the filename convention, starting with the date.
- Ping me on WhatsApp if you have pushed code to GitHub, so I know when to take look.
- Consider keeping a diary of your efforts. What did I do today...? What did I achieve...? What was difficult...?

## Achievements
### Environment installed
We spent quite some installing components to help us get started:
- Installed [OpenJDK 19](https://jdk.java.net/19/) (Java Development Environment) manually in `c:\jdk-19.0.2`. Set the `JAVA_HOME` directory as a system environment variable and added a `PATH` reference to `%JAVA_HOME%\bin`
- Installed [Chocolatey](https://chocolatey.org/), a package manager to help us install other tools more easily.
- Installed [Maven](https://maven.apache.org/) and [Gradle]https://gradle.org/), two Java build management tools. 
- Installed [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/), our integrated development environment (IDE). 
- Installed [GitHub Desktop](https://desktop.github.com/), to make it easy to push code to GitHub.
- Installed the [Exercism command line tool](https://community.chocolatey.org/packages?q=exercism).  
- Created a GitHub account.
- Created a local directory for the projects we download form GitHub: `c:\Users\[Username]\git\[repoName]`.
- Cloned Mark's project [djunit-playground](https://github.com/markhm/djunits-playground/) to `c:\Users\[Username]\git\djunits-playground` and opened it in IntelliJ.
- Created a dedicated GitHub repo `learning-java` to share code for your specific learning.

- Made an [Exercism](https://exercism.org/) account and subscribed to the [Java track](https://exercism.org/tracks/java/).

### Examples discussed
- We looked at the [BasketBallFlightSimulation.java](https://github.com/markhm/djunits-playground/blob/master/src/main/java/dk/mhm/BasketballFlightSimulation.java) example and saw how we can comment a code line by adding `//` in front of the line. We looked at where to change the starting height of the basketball throw. 
- We created a `Chair` class and added properties and some methods. We discussed the difference between `public` and `private` properties and methods: `private` methods can only be accessed within a class. We gave the `Chair` various properties and created two new `Chair`s from the `ChairTest` class. We looked at Getters to access `private` properties. We looked at `if` statements to test conditions and extracted such a test to a separate method. We looked at `System.out.println("");` to print a String to the console. 
- We looked at how to check out an [Exercism](https://exercism.org/) assignment and start working: 
  - Copy-paste the Exercism command from the exercise page
  - Open the command line (Cmd) and paste the command. The exercise is downloaded to: `C:\Users\[Username]\Exercism\java\[exerciseName]`.
  - In IntelliJ, open a Project at `C:\Users\[Username]\Exercism\java\[exerciseName]`.
  - Note that you will be editing the exercise skeleton under `src/main/java`. The tests are ready to verify your correct implementation.
  - On the right side of IntelliJ menu, use Gradle and verification/test to run the tests or `gradle test` from the command line in the project root.
  - Submit your code back to Exercism with `exercism submit` from the command line.
- We practiced pushing code to GitHub. Note that this takes two steps: 
  - Commit the existing code changes to the repository. Note that this is a local action on your laptop. Choose a descriptive name to clarify what you have changed.
  - Push/sync the local repository to the remote repository on GitHub.

## Useful sites / articles
- https://baeldung.com - Short, specific articles on specifics, e.g. how to read a file in Java.
- The [Maven project structure](https://www.baeldung.com/maven) on Baeldung.

## Project structure and Build management tools
Java applications are built from the `src` to the `target` folder. By the Maven convention, source code is stored in the following directories:
- `src/main/java` - production code, the Java classes that we edit
- `src/main/resources` - resources files for production, e.g. a property file, a data file or an image (anything that does not need to be compiled)
- `src/test/java` - unit test code, which is not needed in production, but used to test
- `src/test/resources` - resources files for testing

Within `src/main/java`, source code is stored in a package, a directory structure that 
The two key build management tools for Java are [Maven](https://maven.apache.org/) and [Gradle]https://gradle.org/). Both have similar functions.

### Maven commands
The following commands are run from the command line at the project root:
- `mvn compile` - to compile the production code to `target/classes`
- `mvn test` - to compile the test code `target/test-classes` and run the tests. If necessary, this step will also compile.
- `mvn clean` - remove all `target/`
- `mvn -v` - print the current maven version and some information about the default Java JRE
See also the [Maven documentation online](https://maven.apache.org/guides/getting-started/index.html). Warning: It is quite extensive and probably not easy to digest.

### Gradle commands
Gradle is similar to Maven and used by Exercism. The most important commands are:
- `gradle compile` - ...
- `gradle test` - ...
