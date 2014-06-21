SRC=src/*.java
JC=javac
JAR= lib/jcommon-1.0.21.jar:lib/jfreechart-1.0.17.jar

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
