# Makefile 2 
#***************************************************************************
# 
# Java development root directory.
# 
# (C) 1999 Jacob Dreyer - Geotechnical Software Services
# jacob.dreyer@geosoft.no - http://geosoft.no
#
# Modifications (C) 2001, 2004 Robert Grimm 
# rgrimm@alum.mit.edu
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
#
#***************************************************************************




#***************************************************************************
#
# This section describes the current package.
#
# o PACKAGE     - The complete package name. 
# o PACKAGE_LOC - Same as PACKAGE but with "/"s instead of "."s.
# o SOURCE      - List of the source files. Remember extension.
# o JNI_SOURCE  - Files from SOURCE that are to be built with the JAVAH 
#                 compiler.
# o JAR_EXTRAS  - None-class files and directories that are to be bundled
#                 into the jar archive.
#
#***************************************************************************

PACKAGE     = xtc.oop
PACKAGE_LOC = xtc/oop

SOURCE = \
	   OverloadingASTModifier.java \
	   SymTab.java \
	   Dependency.java \
	   NodeHandler.java \
	   VTableCreator.java \
	   DataLayoutCreator.java \
	   Inheritance.java \
	   CCCP.java \
     LoveTest/TestTool.java \
     LoveTest/TestDriver.java \
     ASTModifier.java \
     LoveXTCLogger.java \
     InheritancePrinter.java \
     Overloader.java \
     Translator.java

JNI_SOURCE =

JAR_EXTRAS = 


#***************************************************************************
#
# Include common part of makefile
#
#***************************************************************************

ifdef JAVA_DEV_ROOT
include $(JAVA_DEV_ROOT)/Makerules
endif

.PHONY: junitTests test love run clean
test:
	make && java org.junit.runner.JUnitCore xtc.oop.LoveTest.TestDriver

loveDocs:
	javadoc *.java LoveTest/*.java -d docs/

FILE = examples/Derived.java
love:
	java xtc.oop.Translator -translate ${FILE}

compile:
	g++ output/java_lang.cc output/output.cc -o output/a.out

run:
	output/a.out

cleanup:
	rm -f output/a.out output/output.*