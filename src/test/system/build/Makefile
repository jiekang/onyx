default: install

install:
	mkdir -p ./classes
	find ../java/ -name *.java | xargs javac -d ./classes
	jar cfm ./BaseTest.jar manifest.mf -C classes/ .
	../../../../bin/onyx ../java ./
	echo bmjava -l bytemanRules.btm -jar BaseTest.jar > run-test
	chmod +x run-test
	
