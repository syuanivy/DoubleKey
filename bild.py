#!/usr/bin/env python

# bootstrap by downloading bilder.py if not found
import urllib
import os

if not os.path.exists("bilder.py"):
    print "bootstrapping; downloading bilder.py"
    urllib.urlretrieve(
        "https://raw.githubusercontent.com/parrt/bild/master/src/python/bilder.py",
        "bilder.py")

# assumes bilder.py is in current directory
from bilder import *

def compile():
    javac("src", "out", cp="/tmp/junit-4.10.jar")
    javac("test", "out", cp="out:/tmp/junit-4.10.jar")

def mkjar():
    require(compile)
    jar("dist/doublekey.jar", srcdir="out", manifest="")

def test():
    require(mkjar)
    junit('test', cp="dist/doublekey.jar:/tmp/junit-4.10.jar")

def all():
    #global JARCACHE
    #JARCACHE='lib'
    #mkdir('lib')
    test()

processargs(globals())
