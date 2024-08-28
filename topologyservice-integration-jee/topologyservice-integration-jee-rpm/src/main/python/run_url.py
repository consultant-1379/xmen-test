#!/usr/bin/python

########################################################################################################################################################################################
#### This is a script to run the queries to fetch the topology output.                                                                                                                 #
#### The IPs used in this script are the IPs of Jboss instance.                                                                                                                        #
#### To use this script run the script as ./run_url.py -s [Get specified MO] <MO Name> || -u [Get all the MOs underneath specified MO] <MO Name> || -a [Get all the EnodeBFunction]    #
########################################################################################################################################################################################
import subprocess
import sys
import getopt
import json
try:
    myopts, args = getopt.getopt(sys.argv[1:],"s:u:a")
except getopt.GetoptError as e:
    print (str(e))
    print("Usage: %s -s [Get specified MO] <MO Name> || -u [Get all the MOs underneath specified MO] <MO Name> || -a [Get all the EnodeBFunction]." % sys.argv[0])
    sys.exit(2)

for o, a in myopts:
    if o == '-s':
        var = a
        p = subprocess.Popen(['curl', '192.168.0.89:8080/topology/ERBS_NODE_MODEL/ENodeBFunction/' + var  ], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = p.communicate()
    elif o == '-u':
        var1 = a
        p = subprocess.Popen(['curl', '192.168.0.89:8080/topology/containment/' + var1  ], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = p.communicate()
    elif o == '-a':
        p = subprocess.Popen(['curl', '192.168.0.89:8080/topology/ERBS_NODE_MODEL/ENodeBFunction/' ], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = p.communicate()
decoded_response = out.decode("UTF-8")
jdata=json.loads(decoded_response)
for idata in jdata:
    data = idata["items"]
    print "FDN : " + idata["fdn"]
    for index in range(len(data)):
       print data[index]["key"] , ":" , data[index]["value"]
