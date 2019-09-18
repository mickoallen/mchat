# mchat
google hangouts killer - https://mickchat.herokuapp.com/

Features...
- Real-time chat
- Account creation with random avatar generation
- User online status
- Light and dark themes
- Notifications, with unread message count

Cool things I haven't done yet...
- Typing indicator
- Image rendering (both uploaded and from a URL)

<img src="/assets/lightTheme.png" width="300px" /> 
<img src="/assets/accountCreation.png" width="300px" />
<img src="/assets/darkTheme.png" width="300px" />

# local development
### prereqs

- Java 11 (recommend using https://sdkman.io/ to manage java versions)
- Maven 3+
- NPM 6.4.1+, node 10.15.1+ (recommend using https://github.com/nvm-sh/nvm to manage node/npm version)
### server
1. start database using ```docker-compose up``` - this will start postgres, create the db, and apply the schema (http://localhost:5432). 
2. from intellij debug ```com.mick.mchat.Main.java``` - this will start the server, with defaults set for local development (http://localhost:7070).

### ui
1. go to ```./ui``` directory.
2. install npm dependencies ```npm install``` (just need to do this first time or when the dependencies change).
3. run ```npm run serve``` to start hot reload debugging.
4. click link to browser to open ui (defaults to http://localhost:8080) 

### db schema changes
1. update the ```./schema/create_db_postgres.sql``` file with the updated schema
2. recreate the db in docker-compose ```docker-compose rm -fs``` ```docker-compose up```
3. run the schema generation mvn profile ```mvn clean install -pcreate-schema```

# deploy to heroku
build service: ```mvn clean install```

builder UI: ```npm run build```

build container and push to heroku cr: ```heroku container:push web -a mickchat```

release container: ```heroku container:release web - a mickchat```
