#!/bin/zsh
DIR="../simple-accessible-todolist_frontend/node_modules/pa11y/bin"
if [ -d "$DIR" ]; then
	cd $DIR && ./pa11y.js http://localhost:4200
else
	pa11y http://localhost:4200 
fi

