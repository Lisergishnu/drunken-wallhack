SRC=src/*.java
JC=javac
NAME=PhysicsLab
all: 
	$(JC) $(SRC)
	mkdir -p bin/
	mv src/*.class bin/
clean:
	rm -rf bin/

run:
	java -classpath bin/ $(NAME)
doc:
	javadoc -d docs/ src/*.java
