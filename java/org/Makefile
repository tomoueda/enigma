JFLAGS = -g -cp

JARPATH = ../lib/junit-4.10.jar:.

default:
	javac $(JFLAGS) $(JARPATH) enigma/*.java

test:
	java -cp $(JARPATH) org.junit.runner.JUnitCore enigma.MainTest

debug:
	jdb -classpath $(JARPATH) org.junit.runner.JUnitCore enigma.MainTest
