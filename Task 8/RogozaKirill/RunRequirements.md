## Requirements

- **Java 17** or higher.
- **Guava 31.1-jre** (only for the "8 Queens" task).

---

## Running the KWIC Solution

### Description

Implementation of the KWIC (Key Word In Context) problem using the "Pipe and Filter" architectural style. The program generates all cyclic permutations of given strings, sorts them, and outputs the result.

**Expected Result:**

The program will output a sorted list of cyclic permutations of the strings (the input list is hardcoded in the `InputFilter` class).

---

## Running the "8 Queens" Solution

### Description

Solution to the "8 Queens" problem using an event-driven architecture and the Guava EventBus library. The program finds and outputs all possible ways to place 8 queens on an 8x8 chessboard without conflicts.

### Preliminary Steps

1. **Install the Guava library:**

   - **Using Maven or Gradle** (if you are using one of these build tools), add the dependency:

     ```xml
     <!-- For Maven -->
     <dependency>
         <groupId>com.google.guava</groupId>
         <artifactId>guava</artifactId>
         <version>31.1-jre</version>
     </dependency>
     ```

     ```gradle
     // For Gradle
     implementation 'com.google.guava:guava:31.1-jre'
     ```

**Expected Result:**

The program will output all found solutions to the "8 Queens" problem.
