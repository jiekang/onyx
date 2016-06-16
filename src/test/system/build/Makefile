default: install

install:
	mkdir -p ./classes
	find ../java/ -name *.java | xargs javac -d ./classes
	jar cfm ./BaseTest.jar manifest.mf -C classes/ .
	../../../../bin/onyx ../java ./
	echo -e '#!/bin/bash\nFILE=~/blob\nbmjava -l bytemanRules.btm -jar BaseTest.jar 2>&1 | tee $$FILE' > run-test
	chmod +x run-test
	
