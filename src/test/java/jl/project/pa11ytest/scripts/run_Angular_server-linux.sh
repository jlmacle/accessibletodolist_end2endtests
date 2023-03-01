IsAngularRunning=$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//' | sed 's/jean-louis.*//')

if [ -z $IsAngularRunning ] 
	then 
		echo "No Angular server process currently running on port 4200." 
	else
		kill $IsAngularRunning && echo "The Angular server process that was running on port 4200 has been suppressed." 
fi

echo "Starting a fresh instance of the server."

cd ../simple-accessible-todolist_frontend && ng serve  