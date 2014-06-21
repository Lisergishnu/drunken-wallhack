SRC=src/*.java
JC=javac
JAR= lib/charting-0.94.jar

NAME=PhysicsLab
all: jar
jar: 
	mkdir -p bin/
	$(JC) -d bin -cp $(JAR) $(SRC)
	jar cfm $(NAME).jar src/Manifiest.txt -C bin/ . -C assets/ .
clean:
	rm -rf bin/
	rm -rf $(NAME).jar

runApplet:
	appletviewer src/PhysicsLab.html

run:
	java -cp $(JAR) -jar $(NAME).jar
doc:
	javadoc -d docs/ src/*.java
