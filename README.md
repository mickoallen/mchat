# mchat
google hangouts killer bitch

# deploy
build service: ```mvn clean install```

builder UI: ```npm run build```

build container and push to heroku cr: ```heroku container:push web -a mickchat```

release container: ```heroku container:release web - a mickchat```