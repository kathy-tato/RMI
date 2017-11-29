all: out compute computations engine client

out:
	mkdir out
	cp -r src/* out/

compute:
	cd out && javac compute/*.java
	cd out && jar cvf compute.jar compute/*.class
	rm -rf out/compute

computations:
	cd out && javac -cp compute.jar computations/*.java
	cd out && jar cvf computations.jar computations/*.class
	rm -rf out/computations

engine:
	cd out && javac -cp compute.jar engine/*.java
	rm -f out/engine/*.java

client:
	cd out && javac -cp compute.jar:computations.jar client/*.java
	rm -f out/client/*.java

run-rmiregistry:
	CLASSPATH=out/compute.jar rmiregistry

run-engine:
	cd out && java -cp .:compute.jar:computations.jar\
	    -Djava.security.policy=security.policy\
	    engine.ComputeEngine

run-client:
	cd out && java -cp .:compute.jar:computations.jar\
	    -Djava.security.policy=security.policy\
	    client.ComputePi

clean:
	rm -rf out
