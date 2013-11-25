#!/usr/bin/python
import ConfigParser, os, getopt
import pymssql, sys
from prettytable import PrettyTable
from prettytable import from_db_cursor

############################################
# functions 
############################################
def db_connect():
	# read options from configuration file
	config = ConfigParser.ConfigParser()
	config.read(['checkEmployees.cfg', os.path.expanduser('~/.checkEmployees.cfg')])

	dbUsername = config.get("db", "username")
	dbPassword = config.get("db", "password")
	dbHost = config.get("db", "db-host")
	dbName = config.get("db", "db-name")

	conn = pymssql.connect(host=dbHost, user=dbUsername, password=dbPassword, database=dbName,as_dict=True)
	cur = conn.cursor()
	return cur

def db_lookup(cursor):
	cursor.execute('SELECT e.ID, e.LAST_NAME, e.FIRST_NAME, e.FATHER_NAME, s.SPECIALIZATION_ID, s.TITLE, ei.REGISTRY_ID, e.VAT_NUMBER FROM EMPLOYEE e'+
   ' INNER JOIN SPECIALIZATION s ON s.SPECIALIZATION_ID=e.LAST_SPECIALIZATION_ID' +
   ' INNER JOIN REGULAR_EMPLOYEE_INFO ei ON ei.EMPLOYEE_ID=e.ID' +
   ' WHERE e.IS_ACTIVE=1 AND e.EMPLOYEE_TYPE=\'REGULAR\' ORDER BY e.LAST_NAME, e.FIRST_NAME')

def checkEmployeeRegistry(employeeRegistry):
	if employeeRegistry and len(employeeRegistry)==6:
		return True
	else:
		return False

############################################
# main program 
############################################

def main(argv):
	# parse command line
	try:
		opts, args = getopt.getopt(argv,"h", ["html"])
	except getopt.GetoptError:
		print 'test.py -h --html'
		sys.exit(2)

	generateHtml = False

	for opt, arg in opts:
		if opt == '-h':
			print 'Usage : checkRegularEmployees.py --html'
			print ''
			print '--html : generate HTML output'
			print ''
			sys.exit()
		elif opt in ("--html"):
			generateHtml =  True

	# if we are generating an html body, begin the html document		
	if generateHtml:
		print "<html><body>"

	# connect to the database
	cursor = db_connect()

	# do the lookup
	db_lookup(cursor)

	bad_registryID_table = PrettyTable(["#", "ID", "Surname", "Name", "Father Name", "Specialization"])

	bad_registryID_count = 1; # counter 
	for row in cursor:
		# check employee registry id
		if not checkEmployeeRegistry(row['REGISTRY_ID']):
			bad_registryID_table.add_row([bad_registryID_count, row['ID'], row['LAST_NAME'], row['FIRST_NAME'], row['FATHER_NAME'], row['SPECIALIZATION_ID']]);
			bad_registryID_count+=1
	
	if generateHtml:
		print "<h1>Regular Employees with bad or not existent registry ID</h1>"
	else:
		print "********************************************************"
		print "* Regular Employees with bad or not existent registry ID"
		print "********************************************************"

	if generateHtml:
		print "<pre><span style=\"font-family: \"courier new\", courier, monospace;\">"
		print bad_registryID_table
		print "</span></pre>"
	else:	
		print bad_registryID_table

	# if we are generating an html body, begin the html document		
	if generateHtml:
		print "</body></html>"


if __name__ == "__main__":
   main(sys.argv[1:])
