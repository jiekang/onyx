PROJECT_NAME=Onyx
VERSION=0.1-ALPHA
JAR=$(PROJECT_NAME)-$(VERSION)-jar-with-dependencies.jar

BUILD_DIR=$(CURDIR)/build
BIN_DIR=$(CURDIR)/bin

BIN_FILE=$(BIN_DIR)/onyx

default: install

package:
	mvn clean package

build: package
	cp ./target/$(JAR) $(BUILD_DIR)

install: build  
	echo java -jar $(BUILD_DIR)/$(JAR) "$$"* > $(BIN_FILE) ; \
	chmod +x $(BIN_FILE) 
