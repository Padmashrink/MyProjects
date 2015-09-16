'''

References :
1. https://www.dropbox.com/developers/core/sdks/python
2. https://www.dropbox.com/developers/core/start/python
3. https://pythonhosted.org/python-gnupg/
4. http://www.saltycrane.com/blog/2011/10/python-gnupg-gpg-example/


'''


import dropbox
import gnupg
import time
from Tkinter import *
import Tkinter 
import ttk
import tkMessageBox
import tkFileDialog
import os
import sys

# Get your app key and secret from the Dropbox developer website
app_key = '*****'
app_secret = '*****'

# Create an object of DropboxOAuth2FlowNoRedirect i.e obj which is the object of DropboxOAuth2FlowNoRedirect and provide authorise linking to the app with the respective dropbox account
obj = dropbox.client.DropboxOAuth2FlowNoRedirect(app_key, app_secret)

#pass the access token to the Dropbox CLient to check if we have got access to the Core API calls
dclient = dropbox.client.DropboxClient('******')
print 'linked account: ', dclient.account_info()

#Set the path to the home directory for Gnupg and also set the default encoding as utf-8
gpg = gnupg.GPG(gnupghome='C:/Python27/GNU/GnuPG')
gpg.encoding = 'utf-8'


#Generate the keys for sender and receiver using gpg.gen_key()
data = gpg.gen_key_input(key_type="RSA", key_length=1024, name_real='advanceddb', name_email=['******'], passphrase='advanceddb')
keys = gpg.gen_key(data)
advanceddb = keys.fingerprint

def encrypt ():
	
	#using Tkinter to browse files to upload
	root = Tkinter.Tk()
	#use to hide tkinter window
	root.withdraw()

	currdir = os.getcwd()
	filename = tkFileDialog.askopenfilename(parent=root,title='Open file to encrypt')
	if len(filename) > 0:
		print "You chose %s" % filename

	#uploading and encrypting the local file to the path in the app created(Dropbox) using the method - put_file(). If destination file is not present, 
	#it will create a new file and upload in the root of the app folder.
	#file object contains two arguments (Filename, mode)
	locfile = open(filename, 'rb')
	i=filename.find('.')
	encrypted_filename= filename[0:i]+ '_encrypted'+filename[i:len(filename)]
	e_data= gpg.encrypt_file(locfile, recipients=['advanceddb@gmail.com'], passphrase='advanceddb', sign=advanceddb, output=encrypted_filename)
	file = open(encrypted_filename, 'rb')
	fname=os.path.basename(encrypted_filename)
	fname1 = '/utility//'+fname
	t0 = time.clock()
	upload = dclient.put_file(fname1, file)
	t1 = time.clock()
	print "uploaded:", upload
	print "--------------TIME TAKEN----------------------"
	print t1-t0, "seconds"
	
	
def decrypt ():
	folder_metadata = dclient.metadata('/utility')
	FileList = []
    #Get all the files and discard the directories
	for fileop in folder_metadata['contents']:
		if fileop['is_dir'] is False:
			#Create a file list of all the files in the root folder of drop box
			FileList.append(fileop['path'])
	print FileList
	
	#downloading and decrypting(also verifying) the file from the drop box to the local directory
	fileToDecrypt = raw_input("Please enter the file to be decrypted")
	fname=os.path.basename(fileToDecrypt)
	file = dclient.get_file(fileToDecrypt)
	output = open('C:\cloud project\output\\'+fname, 'wb')
	decrypt = gpg.decrypt_file(file, passphrase='advanceddb')
	if decrypt.trust_level is not None and decrypt.trust_level >= decrypt.TRUST_FULLY:
		print('Trust level: %s' % decrypt.trust_text)
	output.write(decrypt.data)
	output.close()


def sign ():
	
	#using Tkinter to browse files to upload
	root = Tkinter.Tk()
	#use to hide tkinter window
	root.withdraw()
	
	currdir = os.getcwd()
	filename = tkFileDialog.askopenfilename(parent=root,title='Open file to encrypt')
	if len(filename) > 0:
		print "You chose %s" % filename

	#uploading and digital sign the local file to the path in the app created(Dropbox) using the method - put_file(). If destination file is not present, 
	#it will create a new file and upload in the root of the app folder.
	#file object contains two arguments (Filename, mode)
	locfile = open(filename, 'rb')
	i=filename.find('.')
	encrypted_filename= filename[0:i]+ '_encrypted'+filename[i:len(filename)]
	signdata = gpg.sign_file(locfile, passphrase='advanceddb', output=encrypted_filename)
	file = open(encrypted_filename, 'rb')
	fname=os.path.basename(encrypted_filename)
	fname1 = '/utility//'+fname
	t0 = time.clock()
	upload = dclient.put_file(fname1, file)
	t1 = time.clock()
	print "uploaded:", upload
	print "--------------TIME TAKEN----------------------"
	print t1-t0, "seconds"
	
def verify ():
	folder_metadata = dclient.metadata('/utility')
	FileList = []
    #Get all the files and discard the directories
	for fileop in folder_metadata['contents']:
		if fileop['is_dir'] is False:
			#Create a file list of all the files in the root folder of drop box
			FileList.append(fileop['path'])
	print FileList
	
	#Taking the file that needs to be verified
	fileToDecrypt = raw_input("Please enter the file to be verified")
	fname=os.path.basename(fileToDecrypt)
	file = dclient.get_file(fileToDecrypt)

	#Verifying the file in the drop box
	file, metadata = dclient.get_file_and_metadata('/utility/'+ fname)
	verified = gpg.verify_file(file)
	print "Verified" if verified else "Not verified"
	
def error ():
    print "Please select the right option"	

option = {
	"encrypt": encrypt,
	"decrypt": decrypt,
	"sign": sign,
	"verify": verify}

q = ""
	
while q != "QUIT":
	selected_option = raw_input("Please enter encrypt, decrypt, sign or verify. If you want to exit, type QUIT")
	if selected_option == 'QUIT':
		sys.exit()
	else:
		option.get(selected_option,error)()
