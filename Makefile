SRC=src/*.java
JC=javac
NAME=PhysicsLab
all: jar
jar: 
	$(JC) $(SRC)
	mkdir -p bin/
	mv src/*.class bin/
	jar cfe $(NAME).jar $(NAME) -C bin/ . -C assets/ .
clean:
	rm -rf bin/
	rm $(NAME).jar

runApplet:
	appletviewer src/PhysicsLab.html

run:
	java -jar $(NAME).jar
doc:
	javadoc -d docs/ src/*.java
